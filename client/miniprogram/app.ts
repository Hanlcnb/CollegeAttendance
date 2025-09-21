// app.ts
App({
  globalData: {
    //用户身份
    userInfo: {
      id: "0",
      username: "",
      realName: "",
      avatar: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0',
      phone: "",
      email: "",
      role: "student",
    },
    token: "",
    //设备配置
    systemSetting: {
      screenHeight: 0,
      screenWidth: 0,
    },
    //课程内容
    courses: [
      {name: "", weekDay: 1, startWeek: 1 , endWeek: 1 , section: 0 , sectionCount: 0 , location: ""}
    ],
    //消息内容
    message: {
      senderId: "",
      receiverId: "",
      courseId: "",
      content: "",
      type: "",
      time: "2025-5-9 08:00:00"
    },
    newMessage: "", // 用于存储最新的消息
    messageQueue: [] as any[], // 用于存储消息队列
    listeners: [] as ((message: any) => void)[], // 监听器数组
    socketConnected: false,
    //webSocket
    socketTask: null as WechatMiniprogram.SocketTask | null,
    // WebSocket连接管理
    wsConfig: {
      url: 'ws://localhost:8888/ws',
      reconnectInterval: 3000,  // 重连间隔3秒
      maxReconnectAttempts: 5,  // 最大重连次数
      reconnectAttempts: 0,     // 当前重连次数
      reconnectTimer: null as any, // 重连定时器
      isReconnecting: false,    // 是否正在重连
      heartbeatInterval: 30000, // 心跳间隔30秒
      heartbeatTimer: null as any, // 心跳定时器
      lastHeartbeatTime: 0,     // 最后一次心跳时间
      connectionCheckTimer: null as any, // 连接状态检查定时器
    }
  }, //全局变量

  config: {
    apiBaseURL: 'http://localhost:8080/api/wechat',
    webBaseURL: 'ws://localhost:8888/ws'
  },

  //当小程序初始化完成时，会触发 onLaunch（全局只触发一次）
  onLaunch() {
    const setting = wx.getWindowInfo()
    this.globalData.systemSetting.screenHeight = setting.screenHeight
    this.globalData.systemSetting.screenWidth = setting.screenWidth
  },
  //当小程序从前台进入后台，会触发 onHide
  onHide(){
    // 小程序进入后台时暂停心跳
    this.pauseHeartbeat();
  }, 
  //当小程序启动，或从后台进入前台显示，会触发 onShow
  onShow(){
    // 小程序回到前台时恢复心跳
    if (this.globalData.socketConnected) {
      this.startHeartbeat();
    }
  },
  //当小程序发生脚本错误，或者 API 调用失败时，会触发 onError 并带上错误信息
  onError(){},

  // 连接WebSocket
  connectWebSocket() {
    if (this.globalData.wsConfig.isReconnecting) {
      console.log('正在重连中，跳过本次连接');
      return;
    }

    console.log("开始连接WebSocket");
    const userID = this.globalData.userInfo.id;
    
    // 如果已有连接，先关闭
    if (this.globalData.socketTask) {
      this.globalData.socketTask.close({});
      this.globalData.socketTask = null;
    }

    // 连接 WebSocket 服务端
    const socket = wx.connectSocket({
      url: this.globalData.wsConfig.url,
      success: (res) => {
        console.log('WebSocket 连接创建成功', res);
      },
      fail: (err) => {
        console.error('WebSocket 连接创建失败', err);
        this.handleConnectionError();
      }
    });

    this.globalData.socketTask = socket;

    // 监听连接打开事件
    this.globalData.socketTask.onOpen((res) => {
      console.log('WebSocket 连接打开成功', res);
      this.globalData.socketConnected = true;
      this.globalData.wsConfig.reconnectAttempts = 0; // 重置重连次数
      this.globalData.wsConfig.isReconnecting = false;
      
      // 清除重连定时器
      if (this.globalData.wsConfig.reconnectTimer) {
        clearTimeout(this.globalData.wsConfig.reconnectTimer);
        this.globalData.wsConfig.reconnectTimer = null;
      }
      
      console.log('连接成功，重置重连状态');
      
      // 连接成功后发送初始化消息
      this.sendMessage({ type: 'connect', userId: userID });
      
          // 开始心跳
    this.startHeartbeat();
    
    // 开始连接状态检查
    this.startConnectionCheck();
    });

    // 监听接收到服务器消息事件
    this.globalData.socketTask.onMessage((res) => {
      console.log('接收到服务器消息:', res.data);
      this.handleServerMessage(res.data);
    });

    // 监听连接关闭事件
    this.globalData.socketTask.onClose((res) => {
      console.log('WebSocket 连接关闭', res);
      this.handleConnectionClose();
    });

    // 监听连接错误事件
    this.globalData.socketTask.onError((err) => {
      console.error('WebSocket 连接错误', err);
      this.handleConnectionError();
    });
  },

  // 处理连接关闭
  handleConnectionClose() {
    console.log('处理连接关闭事件');
    this.globalData.socketConnected = false;
    this.globalData.socketTask = null;
    
    // 停止心跳和连接检查
    this.stopHeartbeat();
    this.stopConnectionCheck();
    
    // 检查是否是主动关闭
    if (!this.globalData.wsConfig.isReconnecting) {
      console.log('连接非主动关闭，开始重连流程');
      this.scheduleReconnect();
    } else {
      console.log('连接为主动关闭，不进行重连');
    }
  },

  // 处理连接错误
  handleConnectionError() {
    console.log('处理连接错误事件');
    this.globalData.socketConnected = false;
    this.globalData.socketTask = null;
    
    // 停止心跳和连接检查
    this.stopHeartbeat();
    this.stopConnectionCheck();
    
    // 显示错误提示
    wx.showToast({
      title: "WebSocket连接错误",
      icon: "none"
    });
    
    // 尝试重连
    console.log('连接错误，开始重连流程');
    this.scheduleReconnect();
  },

  // 安排重连
  scheduleReconnect() {
    console.log('安排重连，当前重连次数:', this.globalData.wsConfig.reconnectAttempts);
    
    if (this.globalData.wsConfig.reconnectAttempts >= this.globalData.wsConfig.maxReconnectAttempts) {
      console.log('达到最大重连次数，停止重连');
      wx.showToast({
        title: "连接失败，请检查网络",
        icon: "none"
      });
      return;
    }

    this.globalData.wsConfig.isReconnecting = true;
    this.globalData.wsConfig.reconnectAttempts++;
    
    console.log(`计划在${this.globalData.wsConfig.reconnectInterval}ms后进行第${this.globalData.wsConfig.reconnectAttempts}次重连`);
    
    // 清除之前的重连定时器
    if (this.globalData.wsConfig.reconnectTimer) {
      clearTimeout(this.globalData.wsConfig.reconnectTimer);
    }
    
    this.globalData.wsConfig.reconnectTimer = setTimeout(() => {
      console.log('开始重连WebSocket');
      this.globalData.wsConfig.isReconnecting = false; // 重置重连状态
      this.connectWebSocket();
    }, this.globalData.wsConfig.reconnectInterval);
  },

  // 开始心跳
  startHeartbeat() {
    this.stopHeartbeat(); // 先停止之前的心跳
    
    this.globalData.wsConfig.heartbeatTimer = setInterval(() => {
      if (this.globalData.socketConnected && this.globalData.socketTask) {
        console.log('发送心跳包');
        this.sendHeartbeat();
        this.globalData.wsConfig.lastHeartbeatTime = Date.now();
      }
    }, this.globalData.wsConfig.heartbeatInterval);
  },

  // 停止心跳
  stopHeartbeat() {
    if (this.globalData.wsConfig.heartbeatTimer) {
      clearInterval(this.globalData.wsConfig.heartbeatTimer);
      this.globalData.wsConfig.heartbeatTimer = null;
    }
  },

  // 暂停心跳（小程序进入后台时）
  pauseHeartbeat() {
    this.stopHeartbeat();
  },

  // 开始连接状态检查
  startConnectionCheck() {
    this.stopConnectionCheck(); // 先停止之前的检查
    
    this.globalData.wsConfig.connectionCheckTimer = setInterval(() => {
      // 检查连接状态，如果连接断开且不在重连中，则尝试重连
      if (!this.globalData.socketConnected && !this.globalData.wsConfig.isReconnecting) {
        console.log('检测到连接断开，开始重连');
        this.scheduleReconnect();
      }
    }, 10000); // 每10秒检查一次连接状态
  },

  // 停止连接状态检查
  stopConnectionCheck() {
    if (this.globalData.wsConfig.connectionCheckTimer) {
      clearInterval(this.globalData.wsConfig.connectionCheckTimer);
      this.globalData.wsConfig.connectionCheckTimer = null;
    }
  },

  // 发送心跳包
  sendHeartbeat() {
    const heartbeatMessage = {
      type: 'heartbeat',
      userId: this.globalData.userInfo.id,
      timestamp: Date.now()
    };
    
    if (this.globalData.socketTask && this.globalData.socketConnected) {
      this.globalData.socketTask.send({
        data: JSON.stringify(heartbeatMessage),
        success: (res) => {
          console.log('心跳包发送成功', res);
        },
        fail: (err) => {
          console.error('心跳包发送失败', err);
          // 心跳发送失败，可能是连接断开
          this.handleConnectionError();
        }
      });
    }
  },

  // 发送消息
  sendMessage(message: any) {
    if (this.globalData.socketTask && this.globalData.socketConnected) {
      this.globalData.socketTask.send({
        data: JSON.stringify(message),
        success: (res) => {
          console.log('消息发送成功', res);
        },
        fail: (err) => {
          console.error('消息发送失败', err);
          // 发送失败时尝试重连
          this.handleConnectionError();
        }
      });
    } else {
      console.log('WebSocket 连接未建立或已关闭，尝试重新连接');
      // 如果连接断开，先尝试重连
      if (!this.globalData.wsConfig.isReconnecting) {
        this.connectWebSocket();
      }
      
      // 将消息加入队列，等待连接成功后发送
      setTimeout(() => {
        if (this.globalData.socketConnected) {
          this.sendMessage(message);
        } else {
          console.log('连接仍未建立，消息发送失败');
        }
      }, 1000);
    }
  },

  // 处理服务器消息
  handleServerMessage(data: any) {
    try {
      const message = typeof data === 'string' ? JSON.parse(data) : data;
      
      // 处理心跳响应
      if (message.type === 'heartbeat') {
        console.log('收到心跳响应');
        return;
      }
      
      // 处理其他消息
      this.globalData.newMessage = message;
      this.globalData.messageQueue.push(message);
      this.globalData.listeners.forEach(listener => listener(message));
      
    } catch (error) {
      console.error('解析服务器消息失败:', error);
    }
  },

  // 注册消息处理器
  registerMessageHandler(listener: (message: any) => void) {
    this.globalData.listeners.push(listener);
  },

  // 注销消息处理器
  unregisterMessageHandler(listener: (message: any) => void) {
    this.globalData.listeners = this.globalData.listeners.filter(l => l !== listener);
  },

  // 获取连接状态
  isConnected(): boolean {
    return this.globalData.socketConnected;
  },

  // 获取心跳状态信息
  getHeartbeatInfo() {
    return {
      isConnected: this.globalData.socketConnected,
      lastHeartbeatTime: this.globalData.wsConfig.lastHeartbeatTime 
        ? new Date(this.globalData.wsConfig.lastHeartbeatTime).toLocaleString()
        : '无',
      heartbeatInterval: this.globalData.wsConfig.heartbeatInterval
    };
  }
})