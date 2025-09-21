class WebSocketClient {
    constructor(url) {
        this.url = url;
        this.ws = null;
        this.heartbeatTimer = null;
        this.reconnectTimer = null;
        this.reconnectCount = 0;
        this.maxReconnectCount = 5;
        this.heartbeatInterval = 20000; // 20秒发送一次心跳
    }

    connect() {
        this.ws = wx.connectSocket({
            url: this.url,
            success: () => {
                console.log('WebSocket连接成功');
                this.reconnectCount = 0;
                this.startHeartbeat();
            },
            fail: (error) => {
                console.error('WebSocket连接失败:', error);
                this.reconnect();
            }
        });

        this.ws.onOpen(() => {
            console.log('WebSocket连接已打开');
            this.startHeartbeat();
        });

        this.ws.onClose(() => {
            console.log('WebSocket连接已关闭');
            this.stopHeartbeat();
            this.reconnect();
        });

        this.ws.onError((error) => {
            console.error('WebSocket错误:', error);
            this.stopHeartbeat();
            this.reconnect();
        });

        this.ws.onMessage((res) => {
            const message = JSON.parse(res.data);
            if (message.type === 'heartbeat') {
                console.log('收到服务器心跳响应');
            } else {
                // 处理其他类型的消息
                this.handleMessage(message);
            }
        });
    }

    startHeartbeat() {
        this.stopHeartbeat();
        this.heartbeatTimer = setInterval(() => {
            this.sendHeartbeat();
        }, this.heartbeatInterval);
    }

    stopHeartbeat() {
        if (this.heartbeatTimer) {
            clearInterval(this.heartbeatTimer);
            this.heartbeatTimer = null;
        }
    }

    sendHeartbeat() {
        if (this.ws && this.ws.readyState === 1) {
            this.ws.send({
                data: JSON.stringify({
                    type: 'heartbeat',
                    timestamp: Date.now()
                })
            });
        }
    }

    reconnect() {
        if (this.reconnectCount >= this.maxReconnectCount) {
            console.error('达到最大重连次数，停止重连');
            return;
        }

        this.stopHeartbeat();
        if (this.reconnectTimer) {
            clearTimeout(this.reconnectTimer);
        }

        this.reconnectTimer = setTimeout(() => {
            this.reconnectCount++;
            console.log(`尝试第 ${this.reconnectCount} 次重连`);
            this.connect();
        }, 3000); // 3秒后重连
    }

    send(message) {
        if (this.ws && this.ws.readyState === 1) {
            this.ws.send({
                data: JSON.stringify(message)
            });
        } else {
            console.error('WebSocket未连接，无法发送消息');
        }
    }

    handleMessage(message) {
        // 处理接收到的消息
        console.log('收到消息:', message);
        // TODO: 根据消息类型进行相应处理
    }

    close() {
        this.stopHeartbeat();
        if (this.reconnectTimer) {
            clearTimeout(this.reconnectTimer);
        }
        if (this.ws) {
            this.ws.close();
        }
    }
}

export default WebSocketClient; 