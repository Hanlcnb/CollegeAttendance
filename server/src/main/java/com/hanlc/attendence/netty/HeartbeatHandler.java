package com.hanlc.attendence.netty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
    
    private static final int MAX_MISSED_HEARTBEATS = 3;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private int missedHeartbeats = 0;
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {
            switch (event.state()) {
                case READER_IDLE:
                    // 读空闲，说明客户端没有发送心跳
                    missedHeartbeats++;
                    log.warn("客户端 {} 已经 {} 秒没有发送心跳", ctx.channel().remoteAddress(), missedHeartbeats * 30);
                    
                    if (missedHeartbeats >= MAX_MISSED_HEARTBEATS) {
                        log.error("客户端 {} 心跳超时，关闭连接", ctx.channel().remoteAddress());
                        ctx.close();
                    }
                    break;
                    
                case WRITER_IDLE:
                    // 写空闲，发送心跳包
                    ctx.writeAndFlush(new TextWebSocketFrame("{\"type\":\"heartbeat\"}"));
                    break;
                    
                case ALL_IDLE:
                    // 读写都空闲
                    break;
            }
        }
        super.userEventTriggered(ctx, evt);
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof TextWebSocketFrame frame) {
            try {
                String text = frame.text();
                JsonNode root = OBJECT_MAPPER.readTree(text);
                String type = root.get("type").asText();
                
                if ("heartbeat".equals(type)) {
                    // 收到心跳包，重置计数器
                    missedHeartbeats = 0;
                    log.debug("收到客户端 {} 的心跳包", ctx.channel().remoteAddress());
                    // 心跳包处理完毕，不继续传递给下一个Handler
                    return;
                }
            } catch (Exception e) {
                // JSON解析失败，可能是非JSON格式的消息，继续传递给下一个Handler
                log.debug("心跳检测：非JSON格式消息，传递给下一个Handler");
            }
        }
        // 非心跳包消息继续传递给下一个Handler
        ctx.fireChannelRead(msg);
    }
}