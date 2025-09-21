const verifyFaceApp = getApp()

Component({
  data:{
    width: 0,
    height: 0,
    hasCamera: false,
  },
  ready(){
    this.setData({
      width: verifyFaceApp.globalData.systemSetting.screenWidth,
      height: verifyFaceApp.globalData.systemSetting.screenHeight,
    });
  },
  methods:{
    // 检查相机权限
    checkCameraAuth() {
      const that = this;
      wx.authorize({
        scope: 'scope.camera',
        success: () => {
          that.setData({
            hasCamera: true
          });
        },
        fail: () => {
          // 如果用户之前拒绝了授权，则引导用户去设置页面开启
          wx.showModal({
            title: '需要相机权限',
            content: '请允许使用相机进行拍照',
            success: (res) => {
              if (res.confirm) {
                wx.openSetting({
                  success: (settingRes) => {
                    if (settingRes.authSetting['scope.camera']) {
                      this.setData({
                        hasCamera: true
                      });
                    }
                  }
                });
              }
            }
          });
        }
      });
    },

    //拍摄图片
    photoCapture() {
      if (!this.data.hasCamera) {
        wx.showToast({
          title: '请先授权相机权限',
          icon: 'none'
        });
        this.checkCameraAuth();
        return;
      }

      const ctx = wx.createCameraContext()
      ctx.takePhoto({
        quality: 'high',
        success: (res) => {
          // 保存临时文件的地址
          const tempImagePath = res.tempImagePath;
          // 直接转换临时文件为base64
          this.convertToBase64(tempImagePath);
        },
        fail: (err) => {
          console.error('拍照失败', err);
          wx.showToast({
            title: '拍照失败',
            icon: 'none'
          });
        }
      });
    },

    // 将图片转换为base64
    convertToBase64(filePath: string) {
      const fm = wx.getFileSystemManager();
      fm.readFile({
        filePath: filePath,
        encoding: 'base64',
        success: (res) => {
          const base64Data = res.data;
          // TODO 将base64数据作为参数，向腾讯云人脸识别API发起请求
          const serverURL = `${verifyFaceApp.config.apiBaseURL}/verify`;
          const userId = verifyFaceApp.globalData.userInfo.id;
          wx.request({
            url: serverURL,
            method: "POST",
            data: {
              imageBase64: base64Data,
              personId: userId
            },
            success: (res) =>{
              console.log("成功调用腾讯API服务:",res.data);
              if(res.statusCode === 200){
                console.log("人脸验证成功");
                //TODO 向后端发送POST请求，完成签到
                wx.showModal({
                  title: "提示",
                  content: "签到成功"
                });
                wx.navigateBack({
                  delta: 1
                })
              }
            }
          })
        },
        fail: (err) => {
          console.error('Base64转换失败', err);
          wx.showToast({
            title: 'Base64转换失败',
            icon: 'none'
          });
        }
      });
    }
  }
})