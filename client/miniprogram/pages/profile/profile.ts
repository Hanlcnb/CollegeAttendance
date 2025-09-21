//profile.ts
//用户的个人信息页面
const profileApp = getApp()
import { request } from '../../utils/request';
//暂时给userInfo赋值
const dataToUserInfo = {
  name: "Hanlc",
  avatarUrl: "/images/avatar/mine.jpg"
}

Component({
  data: {
    user: {},
    cellItems: [
      { name: "个人信息" , toPath: '../userinfo/userinfo'},
      { name: "考勤历史" , toPath: '../attendance/attendance'},
      { name: "课程查询" , toPath: '../logs/logs'},
      { name: "设置" , toPath: '../settings/settings'},
    ],
    buttons: [{text: '取消'}, {text: '确认'}]
  },
  methods: {
    //页面加载
    onLoad: function(){
      this.setData({user: profileApp.globalData.userInfo});
      console.log(this.data.user);
    },

    changeAvatar(){
      wx.chooseMedia({
        count: 1,
        mediaType: ['image'],
        sourceType: ['album', 'camera'],
        success: (res) => {
          const tempFilePath = res.tempFiles[0].tempFilePath;
          this.setData({
            'userInfo.avatarUrl': tempFilePath
          });
          //TODO 将用户上传的图片上传到服务器
          const serverURL = `${profileApp.config.apiBaseURL}/uploadfile`
          request({
            url: serverURL,
            method: "POST",
            data:{
              tempFilePath
            }
          }).then((res) => {
            wx.showToast({
              title: '成功上传头像',
              icon: 'success'
            });
          }).catch((err) => {
            console.error('上传头像失败:', err);
            wx.showToast({
              title: '上传失败',
              icon: 'none'
            });
          });
        },
        fail: (err) => {
          wx.showToast({
            title: '选择图片失败',
            icon: 'none'
          });
        }
      });
    },

    toItemsPath(e: any){
      const p = e.currentTarget.dataset.path;
      wx.navigateTo({
        url: p,
      })
    },
  },
})