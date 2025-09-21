package com.hanlc.attendence.netty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanlc.attendence.entity.domain.Messages;
import com.hanlc.attendence.service.MessagesService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Scope("prototype")
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Map<String, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, ChannelHandlerContext> USER_CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, String> CHANNEL_USER_MAP = new ConcurrentHashMap<>(); // channelId -> userId 映射
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
        String userId = CHANNEL_USER_MAP.remove(channelId);
        
        // 清理用户连接映射
        if (userId != null) {
            USER_CHANNEL_MAP.remove(userId);
            log.info("用户 {} 断开连接，channelId: {}", userId, channelId);
        } else {
            log.info("WebSocket连接关闭，channelId: {}", channelId);
        }
        
        CHANNEL_MAP.remove(channelId);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        try {
            String message = frame.text();
            
            // 验证消息格式
            if (!isValidMessage(message)) {
                log.warn("收到无效消息格式: {}", message);
                sendErrorResponse(ctx, "消息格式无效");
                return;
            }

            JsonNode root = OBJECT_MAPPER.readTree(message);
            String type = root.get("type").asText();
            
            // 如果是心跳包，直接跳过（由HeartbeatHandler处理）
            if ("heartbeat".equals(type)) {
                log.debug("心跳包已由HeartbeatHandler处理，跳过");
                return;
            }
            
            //如果是首次连接发送的连接提示则不需要过多的处理
            if ("connect".equals(type)) {
                if (!validateConnectMessage(root)) {
                    sendErrorResponse(ctx, "连接消息缺少必要字段");
                    return;
                }
                String userId = root.get("userId").asText();
                String channelId = ctx.channel().id().asLongText();
                
                // 如果用户已经连接，先断开旧连接
                ChannelHandlerContext oldCtx = USER_CHANNEL_MAP.get(userId);
                if (oldCtx != null && !oldCtx.equals(ctx)) {
                    log.warn("用户 {} 重复连接，关闭旧连接", userId);
                    oldCtx.close();
                }
                
                USER_CHANNEL_MAP.put(userId, ctx);
                CHANNEL_USER_MAP.put(channelId, userId);
                log.info("用户 {} 连接 WebSocket，channelId: {}", userId, channelId);
                return; // 连接消息处理完毕
            }

            processMessage(ctx, root);
        } catch (Exception e) {
            log.error("处理WebSocket消息时发生错误: {}", e.getMessage(), e);
            sendErrorResponse(ctx, "处理消息失败");
        }
    }
    
    /**
     * 验证消息格式
     */
    private boolean isValidMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            return false;
        }
        try {
            OBJECT_MAPPER.readTree(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 验证连接消息
     */
    private boolean validateConnectMessage(JsonNode root) {
        return root.has("userId") && !root.get("userId").asText().trim().isEmpty();
    }

    /**
     * 验证文本消息
     */
    private boolean validateTextMessage(JsonNode root) {
        return root.has("senderId") && !root.get("senderId").asText().trim().isEmpty() &&
               root.has("content") && !root.get("content").asText().trim().isEmpty() &&
               root.has("createdAt") && !root.get("createdAt").asText().trim().isEmpty();
    }
    
    /**
     * 发送错误响应
     */
    private void sendErrorResponse(ChannelHandlerContext ctx, String errorMessage) {
        try {
            String errorResponse = String.format("{\"type\":\"error\",\"message\":\"%s\"}", errorMessage);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(errorResponse));
        } catch (Exception e) {
            log.error("发送错误响应失败", e);
        }
    }
    
    /**
     * 发送消息给接收者
     */
    private void sendMessageToReceiver(String senderId, String receiverId, Messages messages, ChannelHandlerContext senderCtx) {
        try {
            ChannelHandlerContext receiverCtx = USER_CHANNEL_MAP.get(receiverId);
            
            if (receiverCtx != null && receiverCtx.channel().isActive()) {
                // 构建完整的消息JSON，包含所有必要字段
                String messageJson = buildMessageJson(messages);
                
                // 发送消息给接收者
                receiverCtx.channel().writeAndFlush(new TextWebSocketFrame(messageJson));
                log.info("消息已发送给接收者 - sender: {}, receiver: {}, messageId: {}", 
                        senderId, receiverId, messages.getId());
                
                // 发送成功响应给发送者
                String successResponse = "{\"type\":\"response\",\"status\":\"success\",\"message\":\"消息已发送\",\"messageId\":" + messages.getId() + "}";
                senderCtx.channel().writeAndFlush(new TextWebSocketFrame(successResponse));
                
            } else {
                // 接收者不在线
                log.warn("接收者不在线或连接已断开 - receiverId: {}", receiverId);
                String offlineResponse = "{\"type\":\"response\",\"status\":\"offline\",\"message\":\"接收者不在线，消息已保存\"}";
                senderCtx.channel().writeAndFlush(new TextWebSocketFrame(offlineResponse));
            }
            
        } catch (Exception e) {
            log.error("发送消息给接收者时发生错误: {}", e.getMessage(), e);
            String errorResponse = "{\"type\":\"response\",\"status\":\"error\",\"message\":\"发送消息失败: " + e.getMessage() + "\"}";
            senderCtx.channel().writeAndFlush(new TextWebSocketFrame(errorResponse));
        }
    }
    
    /**
     * 构建消息JSON字符串
     */
    private String buildMessageJson(Messages messages) throws Exception {
        // 构建一个包含所有必要字段的JSON对象
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"id\":").append(messages.getId()).append(",");
        jsonBuilder.append("\"senderId\":\"").append(messages.getSenderId()).append("\",");
        jsonBuilder.append("\"receiverId\":\"").append(messages.getReceiverId()).append("\",");
        jsonBuilder.append("\"courseId\":\"").append(messages.getCourseId()).append("\",");
        jsonBuilder.append("\"content\":\"").append(escapeJsonString(messages.getContent())).append("\",");
        jsonBuilder.append("\"type\":\"").append(messages.getType()).append("\",");
        jsonBuilder.append("\"isRead\":").append(messages.getIsRead()).append(",");
        jsonBuilder.append("\"createdAt\":\"").append(messages.getCreatedAt()).append("\"");
        jsonBuilder.append("}");
        
        return jsonBuilder.toString();
    }
    
    /**
     * 转义JSON字符串中的特殊字符
     */
    private String escapeJsonString(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
    
    /**
     * 公共方法：发送消息给指定用户（供外部调用）
     */
    public static boolean sendMessageToUser(String userId, String message) {
        try {
            ChannelHandlerContext userCtx = USER_CHANNEL_MAP.get(userId);
            if (userCtx != null && userCtx.channel().isActive()) {
                userCtx.channel().writeAndFlush(new TextWebSocketFrame(message));
                log.info("消息已发送给用户: {}", userId);
                return true;
            } else {
                log.warn("用户 {} 不在线或连接已断开", userId);
                return false;
            }
        } catch (Exception e) {
            log.error("发送消息给用户 {} 时发生错误: {}", userId, e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * 公共方法：检查用户是否在线
     */
    public static boolean isUserOnline(String userId) {
        ChannelHandlerContext userCtx = USER_CHANNEL_MAP.get(userId);
        return userCtx != null && userCtx.channel().isActive();
    }
    
    /**
     * 公共方法：获取在线用户数量
     */
    public static int getOnlineUserCount() {
        return USER_CHANNEL_MAP.size();
    }

    private void processMessage(ChannelHandlerContext ctx, JsonNode root) throws Exception {
        String msgtype = root.get("type").asText();
        log.info("我来看看服务器接收到的是什么消息： {}" , root);

            switch (msgtype) {
                // connect 消息已经在 channelRead0 方法中处理，这里不需要重复处理

                case "text": //某个用户发送聊天消息
                    // 验证必要字段
                    if (!validateTextMessage(root)) {
                        sendErrorResponse(ctx, "消息缺少必要字段");
                        return;
                    }

                    //初始化messages实例
                    String senderId = root.get("senderId").asText();
                    String receiverId = root.get("receiverId").asText();
                    String courseId = root.get("courseId").asText();
                    String content = root.get("content").asText();
                    String type = root.get("type").asText();
                    Byte isRead = 0;

                    Instant instant = Instant.parse(root.get("createdAt").asText());
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDateTime time = instant.atZone(zoneId).toLocalDateTime();

                    // 构建Message 实体类对象
                    Messages messages = new Messages();
                    messages.setSenderId(senderId);
                    messages.setReceiverId(receiverId);
                    messages.setCourseId(courseId);
                    messages.setContent(content);
                    messages.setType(type);
                    messages.setIsRead(isRead);
                    messages.setCreatedAt(time);

                    try {
                        // 保存消息到数据库
                        log.debug("保存消息到数据库: {}", messages);
                        messagesService.saveMessage(messages);
                        log.info("聊天消息已保存到数据库 - sender: {}, receiver: {}", senderId, receiverId);

                        // 发送消息给接收者
                        sendMessageToReceiver(senderId, receiverId, messages, ctx);
                        
                    } catch (Exception e) {
                        log.error("处理文本消息时发生错误: {}", e.getMessage(), e);
                        sendErrorResponse(ctx, "处理消息失败: " + e.getMessage());
                    }
                    break;

                case "attend":
                    break;

                case "image":
                    break;

                case "file":
                    break;

                case "location":
                    break;

                default:
                    ctx.channel().writeAndFlush(new TextWebSocketFrame("{\"type\":\"error\",\"message\":\"未知消息类型\"}"));
                    break;
            }
    }

}