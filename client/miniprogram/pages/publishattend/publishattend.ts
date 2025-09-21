const publishApp = getApp();
import { request } from '../../utils/request';

interface SignTarget {
  name: string;
  value: string;
  checked: boolean;
}

interface SignType {
  name: string;
  value: string;
  checked?: boolean;
}

Page({
  data: {
    courseName: '',
    location: '',
    signTime: '',
    signTarget: 'student', // 默认学生签到
    signType: 'qr', // 默认二维码签到
    signTypes: [] as SignType[], // 动态设置签到方式
    signRange: '',
    faceImage: '',
    description: '',
    duration: '',
    qrDuration: '5', // 二维码默认有效期5分钟
    allowLate: false,
    lateTime: ''
  },

  onLoad() {
    // 初始化签到方式
    this.updateSignTypes();
  },

  // 更新签到方式列表
  updateSignTypes() {
    let types: SignType[] = [];
    if (this.data.signTarget === 'student') {
      // 学生可用的签到方式
      types = [
        { name: '二维码签到', value: 'qr', checked: true },
        { name: '位置签到', value: 'location' },
        { name: '人脸识别', value: 'face' }
      ];
    } else {
      // 教师可用的签到方式
      types = [
        { name: '二维码签到', value: 'qr', checked: true },
        { name: '位置签到', value: 'location' }
      ];
    }
    this.setData({
      signTypes: types,
      signType: types[0].value // 默认选择第一个签到方式
    });
  },

  // 签到对象选择
  onSignTargetChange(e: WechatMiniprogram.CustomEvent) {
    const signTargets = this.data.signTargets.map(item => ({
      ...item,
      checked: item.value === e.detail.value
    }));
    this.setData({
      signTarget: e.detail.value,
      signTargets
    });
    // 更新签到方式
    this.updateSignTypes();
  },

  // 课程名称输入
  onCourseNameInput(e: any) {
    this.setData({
      courseName: e.detail.value
    });
  },

  // 上课地点输入
  onLocationInput(e: any) {
    this.setData({
      location: e.detail.value
    });
  },

  // 签到时间选择
  onTimeChange(e: any) {
    this.setData({
      signTime: e.detail.value
    });
  },

  // 签到方式选择
  onSignTypeChange(e: any) {
    const signTypes = this.data.signTypes.map(item => ({
      ...item,
      checked: item.value === e.detail.value
    }));
    this.setData({
      signType: e.detail.value,
      signTypes
    });
  },

  // 签到范围输入
  onRangeInput(e: any) {
    this.setData({
      signRange: e.detail.value
    });
  },

  // 二维码有效期输入
  onQRDurationInput(e: any) {
    this.setData({
      qrDuration: e.detail.value
    });
  },

  // 上传人脸照片
  uploadFaceImage() {
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success: (res) => {
        this.setData({
          faceImage: res.tempFilePaths[0]
        });
      }
    });
  },

  // 签到说明输入
  onDescriptionInput(e: any) {
    this.setData({
      description: e.detail.value
    });
  },

  // 签到时长输入
  onDurationInput(e: any) {
    this.setData({
      duration: e.detail.value
    });
  },

  // 是否允许补签
  onAllowLateChange(e: any) {
    this.setData({
      allowLate: e.detail.value
    });
  },

  // 补签截止时间选择
  onLateTimeChange(e: any) {
    this.setData({
      lateTime: e.detail.value
    });
  },

  // 表单验证
  validateForm() {
    if (!this.data.courseName) {
      wx.showToast({
        title: '请输入课程名称',
        icon: 'none'
      });
      return false;
    }
    if (!this.data.location) {
      wx.showToast({
        title: '请输入上课地点',
        icon: 'none'
      });
      return false;
    }
    if (!this.data.signTime) {
      wx.showToast({
        title: '请选择签到时间',
        icon: 'none'
      });
      return false;
    }
    if (this.data.signType === 'location' && !this.data.signRange) {
      wx.showToast({
        title: '请输入签到范围',
        icon: 'none'
      });
      return false;
    }
    if (this.data.signType === 'face' && !this.data.faceImage) {
      wx.showToast({
        title: '请上传人脸照片',
        icon: 'none'
      });
      return false;
    }
    if (this.data.signType === 'qr' && !this.data.qrDuration) {
      wx.showToast({
        title: '请输入二维码有效期',
        icon: 'none'
      });
      return false;
    }
    if (!this.data.duration) {
      wx.showToast({
        title: '请输入签到时长',
        icon: 'none'
      });
      return false;
    }
    if (this.data.allowLate && !this.data.lateTime) {
      wx.showToast({
        title: '请选择补签截止时间',
        icon: 'none'
      });
      return false;
    }
    return true;
  },

  // 提交表单
  submitForm() {
    if (!this.validateForm()) {
      return;
    }

    // 这里应该调用后端API提交表单数据
    const formData = {
      courseName: this.data.courseName,
      location: this.data.location,
      signTime: this.data.signTime,
      signTarget: this.data.signTarget,
      signType: this.data.signType,
      signRange: this.data.signRange,
      qrDuration: this.data.qrDuration,
      description: this.data.description,
      duration: this.data.duration,
    };

    // 模拟提交
    wx.showLoading({
      title: '提交中...',
    });
    const serverURL = `${publishApp.config.apiBaseURL}/checkInTasks/publish`
    request({
      url: serverURL,
      method: 'POST',
      data: formData
    }).then((res) => {
      wx.hideLoading();
      wx.showToast({
        title: '发布成功',
        icon: 'success',
        duration: 2000,
        success: () => {
          setTimeout(() => {
            wx.navigateBack();
          }, 2000);
        }
      });
    }).catch(err => {
      wx.hideLoading();
      console.error("请求失败：", err);
      wx.showToast({
        title: '发布失败',
        icon: 'none'
      });
    });
      
  },

  // 取消
  cancelForm() {
    wx.navigateBack();
  }
}); 