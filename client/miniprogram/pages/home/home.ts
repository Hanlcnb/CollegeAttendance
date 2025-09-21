const homeApp = getApp();
import { request } from '../../utils/request';

interface weekItem{
  label: string,
  value: number
}

interface Course {
  id: string,
  name: string,
  weekDay: number,
  section: number,
  sectionCount: number,
  location: string,
  color?: string,
  key?: string
}

Page({
  data: {
    swiperItems: [
      { num: 1, imageUrl: "https://cdn.pixabay.com/photo/2024/05/14/11/37/tv-8760950_1280.png" },
      { num: 2, imageUrl: "https://cdn.pixabay.com/photo/2024/09/21/10/53/anime-9063542_1280.png" },
      { num: 3, imageUrl: "https://cdn.pixabay.com/photo/2024/09/08/20/30/architecture-9033164_1280.jpg" }
    ],
    pickerVisible: false,
    pickerValue: '',
    pickerTitle: '',
    weekDays: []  as weekItem[],
    // 课程编辑相关数据
    editDialogVisible: false,
    editingCourse: {} as Course,
    editingCourseIndex: -1,
    // 选择器相关数据
    weekPickerVisible: false,
    weekPickerValue: 0,
    sectionPickerVisible: false,
    sectionPickerValue: 0,
    durationPickerVisible: false,
    durationPickerValue: 0,
    weekOptions: [
      { label: '周一', value: 1 },
      { label: '周二', value: 2 },
      { label: '周三', value: 3 },
      { label: '周四', value: 4 },
      { label: '周五', value: 5 },
      { label: '周六', value: 6 },
      { label: '周日', value: 7 }
    ],
    sectionOptions: Array.from({length: 12}, (_, index) => ({
      label: `第${index + 1}节`,
      value: index + 1
    })),
    durationOptions: Array.from({length: 4}, (_, index) => ({
      label: `${index + 1}节`,
      value: index + 1
    })),
    firstClassStartTime: "08:00:00",
    breakTime: "00:15:00",
    nowWeeks: "第一周",
    TotalWeek: 16,
    courseList: [
      {
        name: "高等数学",
        weekDay: 2,
        section: 1,
        sectionCount: 1,
        location: "教学楼107"
      },
      {
        name: "大学英语",
        weekDay: 4,
        section: 4,
        sectionCount: 2,
        location: "教学楼201"
      },
    ],
    windowWidth: 0
  },

  //加载函数
  onLoad: function () {
    const arrWeekDays = Array.from({length: 16} ,(_, index) => ({
      label: `第${index+1}周`,
      value: index+1,
    }));
    wx.getStorage({
      key: "nowWeeks",
      success: (res) => {
        this.setData({
          nowWeeks: res.data
        })
      }
    })
    this.setData({
      windowWidth: homeApp.globalData.systemSetting.screenWidth,
      weekDays: arrWeekDays
    });
    this.getCoursesData();
  },

  handleCourseTap: function (event: WechatMiniprogram.TouchEvent) {
    const course = event.detail;
    console.log('点击了课程：', course);
    wx.showToast({
      title: course.name,
      duration: 2000
    });
  },

  // 扫描二维码
  scanQRCode() {
    wx.scanCode({
      onlyFromCamera: true,
      scanType: ['qrCode'],
      success: (res) => {
        // 处理扫码结果
        const result = res.result
        console.log(result);
        // 示例：处理考勤二维码
        this.handleAttendance(result);
      },
      fail: () => {
        wx.showToast({
          title: '扫码失败',
          icon: 'none'
        })
      }
    })
  },

  // 处理考勤
  handleAttendance(taskID: string) {
    const serverURL = `${homeApp.config.apiBaseURL}/checkInRecords/checkIn`;
    const studentID = homeApp.globalData.userInfo.id;
    const nowTimestamp = Date.now();
    const formattedDate = this.formatDateFromTimestamp(nowTimestamp);
    
    request({
      url: serverURL,
      method: 'POST',
      data: {
        taskId: taskID,
        studentId: studentID,
        checkInTime: formattedDate,
        status: 'success'
      }
    }).then((res: any) => {
      if (res.data.success) {
        wx.showModal({
          title: "提示",
          content: "签到成功"
        });
        wx.navigateBack({
          delta: 1
        })
      } else {
        wx.showToast({
          title: res.data.message || '考勤失败',
          icon: 'none'
        })
      }
    }).catch(() => {
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      })
    });
  },

  // 获取学生课程数据
  getCoursesData: function () {
    const id = homeApp.globalData.userInfo.id;
    const role = homeApp.globalData.userInfo.role;
    const serverURL = `${homeApp.config.apiBaseURL}/${role}Courses/getCourses?${role}Id=${id}`;
    
    request({
      url: serverURL,
      method: 'GET'
    }).then((res: any) => {
      if (res.statusCode === 200 ) {
        const rawCourseData = res.data.data;
        const courseData = Array.isArray(rawCourseData) ? rawCourseData : [rawCourseData];
        const formattedCourseData = courseData.map((course: any) => {
          const randomColor = this.getRandomColor();
          return {
            ...course,
            color: randomColor
          };
        });

        homeApp.globalData.course = formattedCourseData;
        this.setData({
          courseList: formattedCourseData
        });
        console.log("成功获取课程数据courseList：", this.data.courseList)
      } else {
        console.error('获取课程数据失败或数据格式不正确:', JSON.stringify(res));
        wx.showToast({
          title: '获取课程数据失败',
          icon: 'none'
        });
      }
    }).catch((error) => {
      console.error('获取课程数据请求失败:', error);
      wx.showToast({
        title: '获取课程数据请求失败',
        icon: 'none'
      });
    });
  },

  // 随机颜色函数
  getRandomColor(): string {
    const r = Math.floor(Math.random() * 256);
    const g = Math.floor(Math.random() * 256);
    const b = Math.floor(Math.random() * 256);
    return `rgb(${r}, ${g}, ${b})`;
  },

  formatDateFromTimestamp(timestamp : number) {
    const date = new Date(timestamp);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // 月份从 0 开始，需要加 1，并补零
    const day = String(date.getDate()).padStart(2, '0');
    const hour = String(date.getHours()).padStart(2, '0');
    const minute = String(date.getMinutes()).padStart(2, '0');
    const second = String(date.getSeconds()).padStart(2, '0');
  
    return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
  },

  onPickerChange(e:any) {
    const { value } = e.detail;
    const nowWeek = e.detail.label[0];
    console.log('picker change:', e.detail.label[0]);
    this.setData({
      pickerVisible: false,
      pickerValue: value,
      nowWeeks: nowWeek
    });
    wx.setStorage({
      key: "nowWeeks",
      data: nowWeek
    })
  },

  onPickerCancel() {
    this.setData({
      pickerVisible: false,
    });
  },

  pickNowWeeks(){
    this.setData({
      pickerVisible: true,
      pickerTitle: '选择当前周数'
    })
  },

  // 编辑课程
  editCourse(e: WechatMiniprogram.TouchEvent) {
    const tmpCourse = e.currentTarget.dataset.course;
    const courseIndex = this.data.courseList.findIndex(item => item.name === tmpCourse.name);
    
    this.setData({
      editDialogVisible: true,
      editingCourse: { ...tmpCourse },
      editingCourseIndex: courseIndex
    });
  },

  // 保存课程编辑
  saveCourseEdit() {
    const { editingCourse, editingCourseIndex, courseList } = this.data;
    
    // 验证必填字段
    if (!editingCourse.name.trim()) {
      wx.showToast({
        title: '请输入课程名称',
        icon: 'none'
      });
      return;
    }
    
    if (!editingCourse.location.trim()) {
      wx.showToast({
        title: '请输入上课地点',
        icon: 'none'
      });
      return;
    }

    // 更新课程列表
    const newCourseList = [...courseList];
    const newCourse = { ...editingCourse };
    newCourseList[editingCourseIndex] = newCourse;
    
    this.setData({
      courseList: newCourseList,
      editDialogVisible: false,
      editingCourse: {} as Course,
      editingCourseIndex: -1
    });
    const serverURL = `${homeApp.config.apiBaseURL}/courseSchedules/updateCourseSchedules`
    request({
      url: serverURL,
      method: 'POST',
      data: newCourse
    }).then((res) => {
      if(res.statusCode === 200){
        console.log("课程数据更新成功")
      }else{
        console.log("服务器响应错误");
      }
    }).catch((err) => {
      console.error("课程数据更新失败:", err);
    });
    wx.showToast({
      title: '课程信息已更新',
      icon: 'success'
    });
  },

  // 取消课程编辑
  cancelCourseEdit() {
    this.setData({
      editDialogVisible: false,
      editingCourse: {} as Course,
      editingCourseIndex: -1
    });
  },

  // 弹窗可见性变化
  onEditDialogVisibleChange(e: any) {
    if (!e.detail.visible) {
      this.cancelCourseEdit();
    }
  },

  // 课程名称输入
  onCourseNameInput(e: WechatMiniprogram.Input) {
    this.setData({
      'editingCourse.name': e.detail.value
    });
  },

  // 课程地点输入
  onCourseLocationInput(e: WechatMiniprogram.Input) {
    this.setData({
      'editingCourse.location': e.detail.value
    });
  },

  // 显示星期选择器
  showWeekPicker() {
    this.setData({
      weekPickerVisible: true,
      weekPickerValue: this.data.editingCourse.weekDay
    });
  },

  // 星期选择变化
  onWeekChange(e: any) {
    const { value } = e.detail;
    const wd = e.detail.value[0];
    this.setData({
      weekPickerVisible: false,
      weekPickerValue: value,
      'editingCourse.weekDay': wd
    });
  },

  // 取消星期选择
  onWeekPickerCancel() {
    this.setData({
      weekPickerVisible: false
    });
  },

  // 显示节次选择器
  showSectionPicker() {
    this.setData({
      sectionPickerVisible: true,
      sectionPickerValue: this.data.editingCourse.section
    });
  },

  // 节次选择变化
  onSectionChange(e: any) {
    const { value } = e.detail;
    const sec = e.detail.value[0];
    this.setData({
      sectionPickerVisible: false,
      sectionPickerValue: value,
      'editingCourse.section': sec
    });
  },

  // 取消节次选择
  onSectionPickerCancel() {
    this.setData({
      sectionPickerVisible: false
    });
  },

  // 显示持续节数选择器
  showDurationPicker() {
    this.setData({
      durationPickerVisible: true,
      durationPickerValue: this.data.editingCourse.sectionCount
    });
  },

  // 持续节数选择变化
  onDurationChange(e: any) {
    const { value } = e.detail;
    const sC = e.detail.value[0];
    this.setData({
      durationPickerVisible: false,
      durationPickerValue: value,
      'editingCourse.sectionCount': sC
    });
  },

  // 取消持续节数选择
  onDurationPickerCancel() {
    this.setData({
      durationPickerVisible: false
    });
  }
});