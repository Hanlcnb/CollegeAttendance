// pages/attend/attend.ts
const attendApp = getApp()
const myMath = require('../../lib/math.min.js')
import { request } from '../../utils/request';
interface Coordinate{
  lat: number,
  lng: number
}

Page({
  /**
   * 页面的初始数据
   */
  data: {
    width: 0,
    height: 0,
    taskID: 0,
    targetLat: 0,
    targetLng: 0,
    position: '',
    overlayVisible: false,
    overlayDuration: 2000,
    photoPath: "",
    savedFilePath: "",
    currentTime: '',
    qrCodeUrl: '',
    signType: 'face',
    attend: {
      title: "",
      method: "",
      starttime: "",
      endtime: "",
      location: "",
      allowdistance: 0,
      status: ""
    },
    timer: null as any
  },
  
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options: any) {
    //获取设备屏幕宽度和高度
    this.setData({
      width: attendApp.globalData.systemSetting.screenWidth,
      height: attendApp.globalData.systemSetting.screenHeight,
    });
    //验证用户是否在指定范围内，是否具有签到资格
    wx.getSetting({
      success: (res) => {
        //如果用户未授权获取地理位置，则先要求用户进行授权
        if(!res.authSetting['scope.userLocation']){
          wx.authorize({
            scope: 'scope.userLocation',
            success: () => {
              console.log("用户已经成功授权获取地理位置")
              this.getUserLocation()
            },
            fail: () => {
              // 用户拒绝授权，引导去设置页手动开启
              wx.showModal({
                title: '权限提示',
                content: '需要地理位置权限才能签到，是否去设置开启？',
                success: (res) => {
                  if (res.confirm) {
                    wx.openSetting(); // 打开设置页
                  }
                }
              });
            }
          })
        }
      },
      fail: (err) => {
        console.error("检查权限失败:", err);
        wx.showToast({ title: "检查权限失败", icon: "none" });
      }
    });
    // 获取签到任务详情
    const taskID = options.taskId;
    const serverURL = `${attendApp.config.apiBaseURL}/checkInTasks/getTask?taskId=${taskID}`;
    
    request({
      url: serverURL,
      method: 'GET'
    }).then((res: any) => {
      console.log("获取的签到任务详情：",res.data)
      if (res.data.code === 200) {
        this.setData({
          attend: res.data.data
        });
        const parts = this.data.attend.endtime.split(" ");
        const dateParts = parts[0].split('-');
        const timeParts = parts[1].split(':');

        const year = parseInt(dateParts[0]);
        const month = parseInt(dateParts[1]) - 1; // 月份从 0 开始
        const day = parseInt(dateParts[2]);
        const hours = parseInt(timeParts[0]);
        const minutes = parseInt(timeParts[1]);
        const seconds = parseInt(timeParts[2]);

        const dateObject = new Date(year, month, day, hours, minutes, seconds);
        const timestamp2 = dateObject.getTime();
        console.log("现在的时间是：" , Date.now())
        if(timestamp2 < Date.now()){
          wx.showModal({
            title: "注意",
            content: "已经超过签到时间"
          });
          wx.navigateBack({
            delta: 1
          })
        }else{
          this.getUserLocation();
        }
      } else {
        wx.showToast({
          title: '获取签到任务失败',
          icon: 'none'
        });
      }
    }).catch(() => {
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      });
    });
  },

  onReady(){

  },

  getUserLocation(){
    wx.getLocation({
      type:'gcj02',
      success: (res) => {
        const userLat = res.latitude;
        const userLng = res.longitude;
        const userPoint : Coordinate = { lat:userLat , lng:userLng };
        const targetPoint: Coordinate = {
          lat: this.data.targetLat, 
          lng: this.data.targetLng,
        }
        // 计算距离
        var distance = this.getDistance(userPoint, targetPoint);
        console.log(`距离目标点 ${distance} 米`);
        wx.showToast({
          title: distance <= 500 ? 
          '在签到范围内' : 
          `距离目标还有${distance}米`,
          icon: 'none'
        });
      },
      fail:(err)=>{
        console.error("获取用户当前位置失败：",err);
        wx.showToast({ title: "获取位置失败", icon: "none" });
      }
    })
  },
   getDistance(point1: Coordinate, 
    point2: Coordinate,
    earthRadius: number = 6371000): number{
      const { lat: lat1, lng: lng1 } = point1;
      const { lat: lat2, lng: lng2 } = point2;
      const dLat = (lat2 - lat1) * myMath.PI / 180;
      const dLng = (lng2 - lng1) * myMath.PI / 180;
      const a = myMath.sin(dLat/2) * myMath.sin(dLat/2) + 
      myMath.cos(lat1 * myMath.PI / 180) * myMath.cos(lat2 * myMath.PI / 180) * 
      myMath.sin(dLng/2) * myMath.sin(dLng/2); 
      return earthRadius * 2 * myMath.atan2(myMath.sqrt(a), myMath.sqrt(1-a));
    },

  onUnload() {
    // 清除定时器
    if (this.data.timer) {
      clearInterval(this.data.timer);
    }
  },

  handleOverlayClick(){
    wx.navigateBack({
      delta: 1
    })
  }
})