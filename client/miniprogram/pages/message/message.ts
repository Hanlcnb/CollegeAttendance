const messageApp = getApp();
interface Message {
  id?: string;
  senderId: string;
  receiverId: string;
  courseId: string,
  content: string;
  type: string;
  isRead: boolean;
  createdAt: Date;
}

import { request } from '../../utils/request'

Page({
  properties: {
    courseName: {
      type: String,
      value: ''
    }
  },

  data: {
    userInfo: {
      userId: "",
      username: "",
      avatar: "",
      userRole: "",
    },
    hasNotice: false,
    noticeContent: '',
    courseName: '',
    courseID: '',
    avatar: '',
    messages: [] as Message[],
    inputMessage: '',
    showExtendPanel: false,
    scrollToView: '',
  },

  onLoad(options: any) {
    const avatarURL = messageApp.globalData.userInfo.avatar;
    const userId = messageApp.globalData.userInfo.id;
    const userName = messageApp.globalData.userInfo.username;
    const userRole = messageApp.globalData.userInfo.userRole;
    this.setData({
      avatar: "/images/avatar/mine.jpg",
      userInfo: {
        userId: userId,
        username: userName,
        avatar: avatarURL,
        userRole: userRole
      },
      courseID: options.courseId,
      courseName: options.coursename
    });

    // 获取消息列表
    this.getMessageList();
  },

  /**
   * 获取消息列表
   */
  getMessageList() {
    const courseID = this.data.courseID;
    const serverURL = `${messageApp.config.apiBaseURL}/messages/getMessageByCourseId?courseId=${courseID}`
    request({
      url: serverURL,
      method: 'GET'
    }).then((res: any) => {
      if (res.data.code === 200) {
        this.setData({
          messages: res.data.data
        });
      }
    }).catch((err) => {
      console.error('获取消息列表失败:', err);
    });
  },

  // 发送文本消息
  sendTextMessage() {
    if (!this.data.inputMessage.trim()) {
      return;
    }
    const newMessage = {
      senderId: this.data.userInfo.userId,
      receiverId: "",
      courseId: this.data.courseID,
      content: this.data.inputMessage,
      type: "text",
      isRead: false,
      createdAt: new Date(),
    };
    console.log(newMessage);
    //调用APP.ts的sendMessage方法发送消息
    messageApp.sendMessage(newMessage);
    this.setData({
      messages: [...this.data.messages, newMessage],
      inputMessage: '',
      scrollToView: `msg-${this.data.messages.length}`
    });
  },

  receiveLocationMessage(message : Message) {
    if (message.type === 'location') {
      //message.content是155.115154:115.014131这样的类型
      const lot = message.content.split(":")[0];
      const lat = message.content.split(":")[1];
      console.log('接收到位置信息：', lot," 和 ", lot);
      this.setData({
        longitude: lot,
        latitude: lat,
        markers: [{
          id: 0,
          longitude: lot,
          latitude: lat,
          title: '当前位置',
          width: 45,
          height: 45
        }]
      });
    }
  },

  // 格式化时间
  formatTime(): Date {
    return new Date(Math.floor(new Date().getTime()/1000)*1000);
  },

  // 判断是否显示时间戳
  shouldShowTime(): boolean {
    const messages = this.data.messages;
    if (messages.length === 0) return true;
    
    const lastMessage = messages[messages.length - 1];
    const now = new Date();
    const lastTime = new Date(lastMessage.createdAt);
    return now.getTime() - lastTime.getTime() > 5 * 60 * 1000; // 5分钟显示一次时间
  },

  // 选择图片
  chooseImage() {
    wx.chooseMedia({
      count: 1,
      sourceType: ['album', 'camera'],
      sizeType: ['compressed'],
      success: (res) => {
        const serverURL = `${messageApp.config.apiBaseURL}/upload/image`;
        console.log(res.tempFiles[0].tempFilePath);
        wx.uploadFile({
          url: serverURL,
          filePath: res.tempFiles[0].tempFilePath,
          name: 'file',
          header: {
            "Authorization": "Bearer " + messageApp.globalData.token
          },
          success: (res) =>{
            if(res.statusCode === 200){
              console.log("成功发送图片并上传至服务器,图片URL：" , res.data);
              const newMessage = {
                senderId: this.data.userInfo.userId,
                receiverId: "",
                courseId: this.data.courseID,
                content: res.data,
                type: "image",
                isRead: false,
                createdAt: this.formatTime(),
              };
              messageApp.sendMessage(newMessage);
              this.setData({
                messages: [...this.data.messages, newMessage],
                scrollToView: `msg-${this.data.messages.length}`
              });
            }else{
              wx.showToast({
                title: res.data||"无法上传文件"
              })
            }
          },
          fail: (err) => {
            wx.showToast({
              title: "上传文件失败"+err.errMsg
            })
          }
        })
      }
    })
  },

  // 选择位置
  chooseLocation() {
    wx.chooseLocation({
      success: (res) => {
        const newMessage = {
          senderId: this.data.userInfo.userId,
          receiverId: "",
          courseId: this.data.courseID,
          content: res.address,
          type: "location",
          isRead: false,
          createdAt: this.formatTime(),
        };
        messageApp.sendMessage(newMessage);
        this.setData({
          messages: [...this.data.messages, newMessage],
          scrollToView: `msg-${this.data.messages.length}`
        });
      }
    });
  },

  // 预览图片
  previewImage(e: any) {
    const url = e.currentTarget.dataset.url;
    wx.previewImage({
      current: url,
      urls: [url]
    });
  },

  // 打开位置
  openLocation(e: any) {
    const location = e.currentTarget.dataset.location;
    wx.openLocation({
      latitude: Number(location.latitude),
      longitude: Number(location.longitude),
      name: location.name,
      address: location.address
    });
  },

  // 显示/隐藏扩展面板
  showExtendPanel() {
    this.setData({
      showExtendPanel: !this.data.showExtendPanel
    });
  },

  // 监听输入变化
  onInputChange(e: any) {
    this.setData({
      inputMessage: e.detail.value
    });
  },

  // 发布签到
  publishAttend() {
    wx.navigateTo({
      url: '/pages/publishattend/publishattend',
    });
  },

  /**
   * 跳转到签到页面
   */
  goToAttend(e: any) {
    const taskID = e.currentTarget.dataset.attendid;
    // 跳转到签到页面
    wx.navigateTo({
      url: `/pages/attend/attend?taskId=${taskID}`,
      success: (res) => {
        //this.updateMessageRead(e.data);
        console.log('跳转成功', res);
      },
      fail: (err) => {
        console.error('跳转失败', err);
      }
    });
  },

  /**
   * 更新消息已读状态
   
  updateMessageRead(messageId: string) {
    //TODO 等待后端完善更新消息的接口
    const serverURL = `${messageApp.config.apiBaseURL}/messages`
    wx.request({
      url: serverURL,
      method: 'PUT',
      success: (res: any) => {
        if (res.data.code === 200) {
          // 更新本地消息状态
          const messages = this.data.messages.map((msg: Message) => {
            if (msg.id === messageId) {
              return { ...msg, isRead: 1 };
            }
            return msg;
          });
          this.setData({ messages });
        }
      }
    });
  }
  */
});