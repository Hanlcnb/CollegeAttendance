package com.hanlc.graduationproject.netty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanlc.graduationproject.entity.domain.Messages;
import com.hanlc.graduationproject.service.MessagesService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Map<String, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, ChannelHandlerContext> USER_CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MessagesService messagesService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = remoteAddress.getAddress().getHostAddress();
        String channelId = ctx.channel().id().asLongText();
        CHANNEL_MAP.put(channelId, ctx);
        log.info("新的WebSocket连接建立，来自 IP: {}, channelId: {}", clientIp, channelId);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String channelId = ctx.channel().id().asLongText();
        USER_CHANNEL_MAP.entrySet().removeIf(entry -> entry.getValue().equals(ctx));
        CHANNEL_MAP.remove(channelId);
        log.info("WebSocket连接关闭，channelId: {}", channelId);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            String message = frame.text();
            log.info("收到消息: {}", message);

            JsonNode root = OBJECT_MAPPER.readTree(message);
            String type = root.get("type").asText();

            if ("connect".equals(type)) {
                String userId = root.get("userId").asText();
                USER_CHANNEL_MAP.put(userId, ctx);
                log.info("用户 {} 连接 WebSocket，channelId: {}", userId, ctx.channel().id().asLongText());
                return; // 连接消息处理完毕，不再进行后续的 processMessage
            }

            processMessage(ctx, message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理WebSocket消息时发生错误", e);
            ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"error\",\"message\":\"处理消息失败\"}"));
        }
    }

    private void processMessage(ChannelHandlerContext ctx, String message) throws Exception {
        try {
            JsonNode root = OBJECT_MAPPER.readTree(message);
            String msgtype = root.get("msgtype").asText();

            switch (msgtype) {
                case "connect": // 小程序发送的连接消息，包含 userId
                    String userId = root.get("userId").asText();
                    USER_CHANNEL_MAP.put(userId, ctx);
                    log.info("用户 {} 关联到 channelId: {}", userId, ctx.channel().id().asLongText());
                    break;

                case "chat":
                    String senderId = root.get("senderId").asText();
                    String receiverId = root.get("receiverId").asText();
                    String courseId = root.get("courseId").asText();
                    String content = root.get("content").asText();
                    String type = root.get("type").asText();
                    Byte isRead = 0;
                    String time = root.get("time").asText();

                    // 构建Message 实体类对象
                    Messages messages = new Messages();
                    messages.setSenderId(senderId);
                    messages.setReceiverId(receiverId);
                    messages.setCourseId(courseId);
                    messages.setContent(content);
                    messages.setType(type);
                    messages.setIsRead(isRead);
                    messages.setCreatedAt(time);

                    log.info(message);
                    messagesService.saveMessage(messages);
                    log.info("聊天消息已保存到数据库 - sender: {}, receiver: {}", senderId, receiverId);

                    ChannelHandlerContext receiverCtx = USER_CHANNEL_MAP.get(receiverId);
                    if (receiverCtx != null) {
                        receiverCtx.channel().writeAndFlush(new TextWebSocketFrame(message));
                        ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"response\",\"status\":\"success\",\"message\":\"消息已发送\"}"));
                    } else {
                        ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"response\",\"status\":\"error\",\"message\":\"接收者不在线或不存在\"}"));
                    }
                    break;

                default:
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"error\",\"message\":\"未知消息类型\"}"));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("处理消息 JSON 失败: {}", message);
            ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"error\",\"message\":\"消息格式错误\"}"));
        }
    }

    public static void broadcast(String message) {
        CHANNEL_MAP.values().forEach(ctx ->
                ctx.channel().writeAndFlush(new TextWebSocketFrame(message))
        );
    }

    /**
     * 发送消息给指定用户
     * @param userId 用户ID
     * @param message 消息内容
     * @return 是否发送成功
     */
    public static boolean sendToUser(String userId, String message) {
        ChannelHandlerContext ctx = USER_CHANNEL_MAP.get(userId);
        if (ctx != null && ctx.channel().isActive()) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
            log.info("消息已发送给用户 {}: {}", userId, message);
            return true;
        } else {
            log.warn("用户 {} 不在线或连接已关闭", userId);
            return false;
        }
    }

    /**
     * 获取当前在线用户数
     * @return 在线用户数
     */
    public static int getOnlineUserCount() {
        return USER_CHANNEL_MAP.size();
    }

    /**
     * 获取所有在线用户的ID
     * @return 在线用户ID列表
     */
    public static List<String> getOnlineUsers() {
        return new ArrayList<>(USER_CHANNEL_MAP.keySet());
    }
}