// index.ts
// 用户通过微信登录，获取微信服务器返回的openID，从后端查询个人信息
const indexApp = getApp()
import { request } from '../../utils/request';

Page({
  data: {
    //用户登录的方式： 按钮 || 用户名
    loginMethod: "button",
    //默认头像框
    defaultAvatar: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0',
    // 登录相关
    inputUsername: "",
    inputPassword: "",
    // 找回密码相关
    inputEmail: "",
    // 注册相关
    registerUsername: "",
    registerEmail: "",
    registerPassword: "",
    confirmPassword: "",
    // 界面状态
    showForgotPassword: false,
    showRegister: false,
    // 微信授权相关
    userInfo: null as any,
    hasUserInfo: false,
    isAuthorizing: false, // 是否正在授权中
  },

  onLoad: function() {
    // 检查是否已经登录
    this.checkLoginStatus();
  },

  // 检查登录状态
  checkLoginStatus() {
    wx.getStorage({
      key: "user",
      success: (res) => {
        // 用户已经登录过并将userID存储在本地中，那么不需要再次登录直接进入home页面
        console.log("成功从缓存中获取user:", res.data);
        indexApp.globalData.userInfo = res.data;
        // 检查用户信息是否完整
        if (indexApp.globalData.userInfo) {
          console.log("用户信息完整，跳转到home页面")
          wx.getStorage({
            "key": "token" , 
            success: (res) => {
              indexApp.globalData.token = res.data;
            }
          })
          wx.switchTab({
            url: "../home/home",
          });
        } else {
          console.log("用户信息无效，需要重新登录");
          this.clearLoginData();
        }
      },
      fail: () => {
        console.log("未找到用户登录信息，需要登录");
        this.clearLoginData();
      }
    });
  },

  // 清除登录数据
  clearLoginData() {
    wx.removeStorageSync('userId');
    indexApp.globalData.userInfo.id = "0";
    indexApp.globalData.userInfo.username = "";
    indexApp.globalData.userInfo.realName = "";
    indexApp.globalData.userInfo.avatar = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0';
  },

  // 用户名输入事件
  onUsernameInput(e: any) {
    this.setData({
      inputUsername: e.detail.value
    });
  },

  // 密码输入事件
  onPasswordInput(e: any) {
    this.setData({
      inputPassword: e.detail.value
    });
  },

  // 邮箱输入事件（找回密码）
  onEmailInput(e: any) {
    this.setData({
      inputEmail: e.detail.value
    });
  },

  // 注册用户名输入事件
  onRegisterUsernameInput(e: any) {
    this.setData({
      registerUsername: e.detail.value
    });
  },

  // 注册邮箱输入事件
  onRegisterEmailInput(e: any) {
    this.setData({
      registerEmail: e.detail.value
    });
  },

  // 注册密码输入事件
  onRegisterPasswordInput(e: any) {
    this.setData({
      registerPassword: e.detail.value
    });
  },

  // 确认密码输入事件
  onConfirmPasswordInput(e: any) {
    this.setData({
      confirmPassword: e.detail.value
    });
  },

  //用户名和密码登录
  verity () {
    console.log('开始验证登录信息');
    console.log('用户名:', this.data.inputUsername);
    console.log('密码:', this.data.inputPassword);
    
    // 验证输入是否为空
    if (!this.data.inputUsername || !this.data.inputPassword) {
      wx.showToast({
        title: "用户名或密码不能为空",
        icon: "none"
      })
      return;
    }

    const serverURL = `${indexApp.config.apiBaseURL}/users/loginVerity`;

    request({
      url: serverURL,
      data: {
        username: this.data.inputUsername,
        password: this.data.inputPassword
      },
      method: "GET",
      skipAuth: true // 登录验证不需要 token
    }).then((res) => {
      console.log('验证结果:', res);
      if (res.statusCode === 200) {
        this.Login();
      } else {
        wx.showToast({
          title: "用户名或密码错误",
          icon: "none"
        })
      }
    }).catch((err) => {
      console.log('请求失败:', err);
      wx.showToast({
        title: "网络连接错误，请检查网络",
        icon: "none"
      })
    });
  },
  
  Login(){
    // 登录
    wx.login({
      success: (res) => {
        const loginCode = res.code;
        // 应该发送code到自己的后端服务器
        const serverURL = `${indexApp.config.apiBaseURL}/login`;
        request({
          url: serverURL,
          data: {
            code: loginCode
          },
          skipAuth: true // 登录不需要 token
        }).then((result: any) => {
          if(result.data.errcode) {
            console.error("向后端发起登录请求返回错误：", result.data.errmsg)
            return
          }
          const userData = result.data.data.user
          const userToken = result.data.data.token
          console.log("用户成功登录，服务器返回的数据：",result.data.data);
          indexApp.globalData.userInfo = userData;
          indexApp.globalData.token = userToken;
          wx.setStorage({
            key: "user",
            data: userData
          });
          wx.setStorage({
            key: "token",
            data: userToken
          });
          indexApp.connectWebSocket();
          wx.switchTab({
            url:'../home/home',
          })
        }).catch(err => {
          console.error("请求失败：", err);
          wx.showToast({
            title: "网络连接错误，请检查网络",
            icon: "none"
          })
        });
      },
      fail: (err)=> {
        console.error("微信登录失败：", err)
      }
    })
  },

  changeLoginMethod() {
    if(this.data.loginMethod === "button"){
      this.setData({
        loginMethod: "input"
      })
    }else{
      this.setData({
        loginMethod: "button"
      })
    }
  },

  showForgotPassword() {
    this.setData({ showForgotPassword: true });
  },
  showRegister() {
    this.setData({ showRegister: true });
  },

  backToLogin() {
    this.setData({ 
      showForgotPassword: false,
      showRegister: false 
    });
  },

  handleForgotPassword() {
    // 处理找回密码逻辑
    console.log('找回密码，邮箱:', this.data.inputEmail);
    
    if(!this.data.inputEmail || this.data.inputEmail === ""){
      wx.showModal({
        title: "输入错误",
        content: "邮箱不能为空",
        showCancel: false
      });
      return;
    }

    // 简单的邮箱格式验证
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.data.inputEmail)) {
      wx.showModal({
        title: "邮箱格式错误",
        content: "请输入正确的邮箱地址",
        showCancel: false
      });
      return;
    }

    const serverURL = `${indexApp.config.apiBaseURL}/users/forgotPassword`;
    request({
      url: serverURL,
      method: "POST",
      data: {
        email: this.data.inputEmail
      },
      skipAuth: true // 找回密码不需要 token
    }).then((res) => {
      console.log('找回密码请求结果:', res);
      if (res.statusCode === 200) {
        wx.showModal({
          title: "发送成功",
          content: "密码重置链接已发送到您的邮箱",
          showCancel: false,
          success: () => {
            this.backToLogin();
          }
        });
      } else {
        wx.showModal({
          title: "发送失败",
          content: "邮箱不存在或网络错误",
          showCancel: false
        });
      }
    }).catch((err) => {
      console.log('找回密码请求失败:', err);
      wx.showModal({
        title: "网络错误",
        content: "无法连接到服务器，请检查网络连接",
        showCancel: false
      });
    });
  },

  handleRegister() {
    // 处理注册逻辑
    console.log('开始注册');
    console.log('用户名:', this.data.registerUsername);
    console.log('邮箱:', this.data.registerEmail);
    console.log('密码:', this.data.registerPassword);
    console.log('确认密码:', this.data.confirmPassword);
    
    // 验证输入
    if (!this.data.registerUsername || !this.data.registerEmail || !this.data.registerPassword || !this.data.confirmPassword) {
      wx.showModal({
        title: "输入错误",
        content: "所有字段都不能为空",
        showCancel: false
      });
      return;
    }

    // 验证邮箱格式
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(this.data.registerEmail)) {
      wx.showModal({
        title: "邮箱格式错误",
        content: "请输入正确的邮箱地址",
        showCancel: false
      });
      return;
    }

    // 验证密码长度
    if (this.data.registerPassword.length < 6) {
      wx.showModal({
        title: "密码错误",
        content: "密码长度不能少于6位",
        showCancel: false
      });
      return;
    }

    // 验证两次密码是否一致
    if (this.data.registerPassword !== this.data.confirmPassword) {
      wx.showModal({
        title: "密码错误",
        content: "两次输入的密码不一致",
        showCancel: false
      });
      return;
    }

    const serverURL = `${indexApp.config.apiBaseURL}/register`;
    request({
      url: serverURL,
      method: "POST",
      data: {
        name: this.data.registerUsername,
        email: this.data.registerEmail,
        password: this.data.registerPassword,
      },
      skipAuth: true // 注册不需要 token
    }).then((res) => {
      console.log('注册请求结果:', res);
      if (res.statusCode === 200) {
        wx.showModal({
          title: "注册成功",
          content: "账号注册成功，请登录",
          showCancel: false,
          success: () => {
            this.backToLogin();
          }
        });
      } else {
        wx.showModal({
          title: "注册失败",
          content: (res.data as any)?.message || "注册失败，请重试",
          showCancel: false
        });
      }
    }).catch((err) => {
      console.log('注册请求失败:', err);
      wx.showModal({
        title: "网络错误",
        content: "无法连接到服务器，请检查网络连接",
        showCancel: false
      });
    });
  },

  // 退出登录
  logout() {
    wx.showModal({
      title: '确认退出',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          // 清除本地存储
          this.clearLoginData();
          
          // 关闭WebSocket连接
          if (indexApp.globalData.socketTask) {
            indexApp.globalData.socketTask.close({});
            indexApp.globalData.socketTask = null;
          }
          indexApp.globalData.socketConnected = false;
          
          // 重置页面数据
          this.setData({
            userInfo: null,
            hasUserInfo: false,
            isAuthorizing: false,
            defaultAvatar: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'
          });
          
          wx.showToast({
            title: '已退出登录',
            icon: 'success'
          });
        }
      }
    });
  },

})
