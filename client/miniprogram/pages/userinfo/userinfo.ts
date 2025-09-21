// userinfo.ts
const userInfoApp = getApp()
import { request } from '../../utils/request';

Page({
  data: {
    userInfo: {
      id: "0",
      username: "",
      realName: "",
      avatar: 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0',
      phone: "",
      email: "",
      role: "student",
    },
    isModified: false
  },

  onLoad() {
    this.getUserInfo()
  },

  // 获取用户信息
  getUserInfo() {
    this.setData({
      userInfo: userInfoApp.globalData.userInfo
    })
  },

  // 更换头像
  changeAvatar() {
    wx.chooseMedia({
      count: 1,
      mediaType: ['image'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        const tempFilePath = res.tempFiles[0].tempFilePath
        this.uploadAvatar(tempFilePath)
      },
      fail: () => {
        wx.showToast({
          title: '选择图片失败',
          icon: 'none'
        })
      }
    })
  },

  // 上传头像
  uploadAvatar(filePath: string) {
    const serverURL = `${userInfoApp.config.apiBaseURL}/users/avatar`
    wx.uploadFile({
      url: serverURL,
      filePath: filePath,
      name: 'avatar',
      success: (res) => {
        const data = JSON.parse(res.data)
        this.setData({
          'userInfo.avatarUrl': data.avatarUrl,
          isModified: true
        })
        wx.showToast({
          title: '头像更新成功',
          icon: 'success'
        })
      },
      fail: () => {
        wx.showToast({
          title: '头像上传失败',
          icon: 'none'
        })
      }
    })
  },

  // 输入框变化处理函数
  onUsernameChange(event: any) {
    this.setData({
      'userInfo.username': event.detail,
      isModified: true
    })
  },

  onRealnameChange(event: any) {
    this.setData({
      'userInfo.realName': event.detail,
      isModified: true
    })
  },

  onEmailChange(event: any) {
    this.setData({
      'userInfo.email': event.detail,
      isModified: true
    })
  },

  onPhoneChange(event: any) {
    this.setData({
      'userInfo.phone': event.detail,
      isModified: true
    })
  },

  // 保存用户信息
  saveUserInfo() {
    if (!this.data.isModified) {
      wx.showToast({
        title: '未做任何修改',
        icon: 'none'
      })
      return
    }

    const serverURL = `${userInfoApp.config.apiBaseURL}/users/updateUser`
    request({
      url: serverURL,
      method: 'PUT',
      data: this.data.userInfo
    }).then(() => {
      wx.showToast({
        title: '保存成功',
        icon: 'success'
      })
      this.setData({ isModified: false })
    }).catch(() => {
      wx.showToast({
        title: '保存失败',
        icon: 'none'
      })
    });
  }
}) 