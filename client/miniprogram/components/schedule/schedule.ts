const scheduleApp = getApp()

Component({
  /**
   * 组件的初始数据
   */
  data: {
    firstClassStartTime: "08:00:00",
    breakTime: "00:15:00",
    nowWeeks: 1,
    TotalWeek: 16,
    weekDays: "",
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
    windowWidth : 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
    // 获取学生课程数据
    getCoursesData: function (this: WechatMiniprogram.Component.Instance) {
      const id = scheduleApp.globalData.userInfo.id;
      const role = scheduleApp.globalData.userInfo.role;
      const serverURL = `${scheduleApp.config.apiBaseURL}/${role}Courses/getCourses?${role}Id=${id}`;
      wx.request({
        url: serverURL,
        method: 'GET',
        success: (res: any) => {
          if (res.statusCode === 200 ) {
            const rawCourseData = res.data.data;
            const courseData = Array.isArray(rawCourseData) ? rawCourseData : [rawCourseData];
            const formattedCourseData = courseData.map((course: Course) => {

              let randomColor = this.getRandomColor();

              return {
                ...course,
                color: randomColor
              };
            });

            scheduleApp.globalData.course = formattedCourseData;
            this.setData({
              courseList: formattedCourseData
            });
            console.log("当前courseList：",this.data.courseList)
          } else {
            console.error('获取课程数据失败或数据格式不正确:', JSON.stringify(res));
            wx.showToast({
              title: '获取课程数据失败',
              icon: 'none'
            });
          }
        },
        fail: (error) => {
          console.error('获取课程数据请求失败:', error);
          wx.showToast({
            title: '获取课程数据请求失败',
            icon: 'none'
          });
        }
      });
    },

    // 随机颜色函数
    getRandomColor(): string {
      const r = Math.floor(Math.random() * 256);
      const g = Math.floor(Math.random() * 256);
      const b = Math.floor(Math.random() * 256);
      return `rgb(${r}, ${g}, ${b})`;
    },
  },
  
  attached(){
    this.setData({
      windowWidth: scheduleApp.globalData.systemSetting.screenWidth,
    });
    this.getCoursesData();
  },
});