package com.hanlc.graduationproject.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class WebSocketServer {
    
    private final int port;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    private final ApplicationContext applicationContext;
    
    public WebSocketServer(int port , ApplicationContext applicationContext) {
        this.port = port;
        this.applicationContext = applicationContext;
    }
    
    @PostConstruct
    public void start() {
        // 在单独的线程中启动Netty服务器
        new Thread(() -> {
            try {
                bossGroup = new NioEventLoopGroup(1);
                workerGroup = new NioEventLoopGroup();
                
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) {
                                ch.pipeline().addLast(
                                        new HttpServerCodec(),
                                        new ChunkedWriteHandler(),
                                        new HttpObjectAggregator(65536),
                                        new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS),
                                        applicationContext.getBean(HeartbeatHandler.class), // 从 Spring 上下文获取 HeartbeatHandler
                                        new WebSocketServerProtocolHandler("/ws"),
                                        applicationContext.getBean(WebSocketFrameHandler.class) // 从 Spring 上下文获取 WebSocketFrameHandler
                                );
                            }
                        });
                
                channelFuture = bootstrap.bind(port).sync();
                log.info("WebSocket服务器启动成功，监听端口：{}", port);
                
                // 等待服务器关闭
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                log.error("WebSocket服务器启动失败", e);
            }
        }, "netty-websocket-server").start();
    }
    
    @PreDestroy
    public void stop() {
        try {
            if (channelFuture != null) {
                channelFuture.channel().close();
            }
            if (bossGroup != null) {
                bossGroup.shutdownGracefully();
            }
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
            log.info("WebSocket服务器已关闭");
        } catch (Exception e) {
            log.error("关闭WebSocket服务器时发生错误", e);
        }
    }
} 