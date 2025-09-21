const app = getApp()
import { request } from '../../utils/request';

interface Settings {
  notification: boolean;
  darkMode: boolean;
}

interface UserInfo {
  phone: string;
  email: string;
}

Component({
  data: {
    settings: {
      notification: true,
      darkMode: false
    } as Settings,
    userInfo: {
      phone: '',
      email: ''
    } as UserInfo
  },

  methods: {
    onLoad() {
      this.loadSettings()
      this.loadUserInfo()
    },

    // 加载设置
    loadSettings() {
      const settings = wx.getStorageSync('settings')
      if (settings) {
        this.setData({ settings })
        this.applyDarkMode(settings.darkMode)
      }
    },

    // 加载用户信息
    loadUserInfo() {
      const serverURL = `${app.config.apiBaseURL}/users/profile`
      request({
        url: serverURL,
        method: 'GET'
      }).then((res: any) => {
        this.setData({
          userInfo: {
            phone: res.data.phone || '未绑定',
            email: res.data.email || '未绑定'
          }
        })
      }).catch((err) => {
        console.error('获取用户信息失败:', err);
        this.setData({
          userInfo: {
            phone: '获取失败',
            email: '获取失败'
          }
        })
      });
    },

    // 消息通知开关
    onNotificationChange(event: any) {
      const notification = event.detail
      this.setData({ 'settings.notification': notification })
      this.saveSettings()
    },

    // 深色模式开关
    onDarkModeChange(event: any) {
      const darkMode = event.detail
      this.setData({ 'settings.darkMode': darkMode })
      this.applyDarkMode(darkMode)
      this.saveSettings()
    },

    // 应用深色模式
    applyDarkMode(darkMode: boolean) {
      if (darkMode) {
        wx.setNavigationBarColor({
          frontColor: '#ffffff',
          backgroundColor: '#1c1c1e'
        })
      } else {
        wx.setNavigationBarColor({
          frontColor: '#000000',
          backgroundColor: '#ffffff'
        })
      }
    },

    // 保存设置
    saveSettings() {
      wx.setStorageSync('settings', this.data.settings)
    },

    // 清除缓存
    onClearCacheTap() {
      wx.showModal({
        title: '提示',
        content: '确定要清除缓存吗？',
        success: (res) => {
          if (res.confirm) {
            wx.clearStorage({
              success: () => {
                wx.showToast({
                  title: '清除成功',
                  icon: 'success'
                })
              }
            })
          }
        }
      })
    },

    // 修改密码
    onChangePasswordTap() {
      wx.navigateTo({
        url: '/pages/settings/change-password/change-password'
      })
    },

    // 绑定手机
    onBindPhoneTap() {
      wx.navigateTo({
        url: '/pages/settings/bind-phone/bind-phone'
      })
    },

    // 绑定邮箱
    onBindEmailTap() {
      wx.navigateTo({
        url: '/pages/settings/bind-email/bind-email'
      })
    },

    // 关于我们
    onAboutTap() {
      wx.navigateTo({
        url: '/pages/settings/about/about'
      })
    },

    // 用户协议
    onUserAgreementTap() {
      wx.navigateTo({
        url: '/pages/settings/agreement/agreement'
      })
    },

    // 隐私政策
    onPrivacyPolicyTap() {
      wx.navigateTo({
        url: '/pages/settings/privacy/privacy'
      })
    },

    // 退出登录
    onLogoutTap() {
      const dialog = this.selectComponent('#van-dialog')
      dialog.showDialog({
        title: '提示',
        message: '确定要退出登录吗？',
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        success: (action: string) => {
          if (action === 'confirm') {
            // 清除登录状态
            wx.clearStorageSync()
            // 跳转到登录页
            wx.reLaunch({
              url: '/pages/login/login'
            })
          }
        }
      })
    },

    // 页面跳转
    navigateTo(event: any) {
      const url = event.currentTarget.dataset.url
      wx.navigateTo({ url })
    }
  }
})
 