const noticeApp = getApp()
import { request } from '../../utils/request'

Page({
  data: {
    notices: []
  },

  onLoad() {
    // 页面加载时的逻辑
    const userID = noticeApp.globalData.userInfo.id;
    const role = noticeApp.globalData.userInfo.role;
    const serverURL = `${noticeApp.config.apiBaseURL}/messages/${role}GetNotice`
    
    request({
      url: serverURL,
      method: "GET",
      data: {
        userId: userID
      }
    }).then((res) => {
      if(res.statusCode == 200){
        console.log("成功获取用户通知列表", res);
        const noticesWithTime = (res.data.data || []).map((n: any) => ({
          ...n,
          createdAt: formatNoticeTime(n.createdAt)
        }));
        this.setData({
          notices: noticesWithTime,
        })
      }
    }).catch((err) => {
      console.log("无法获取用户未读的最新通知列表", err)
    })
    /*
    const formattedNotices = courses.map((course: any) => {
      const courseName = course.name;
      const iconURL = `/images/courseicon/${courseName || 'pending'}.png`;
      return {
        ...notice,
        icon: iconURL,
        courseName: coursename
      };
    });
    this.setData({
      notices: formattedNotices
    });
    */
  },

  toPageDetail(e: any) {
    const courseName = e.currentTarget.dataset.coursename as string;
    const courseId = e.currentTarget.dataset.courseid as string;
    wx.navigateTo({
      url: `/pages/message/message?coursename=${courseName}&courseId=${courseId}`
    });
  },

  loadMessages() {
    const serverURL = `${messageApp.config.apiBaseURL}/messages/`;
    wx.request({
      url: serverURL,
      success: (res) => {
        this.setData({
          messages: res.data
        });
      }
    });
    // 模拟消息数据
    const mockMessages = [
      {
        type: 'text',
        content: '你好！',
        sender: 'other',
        time: Date.now() - 3600000,
        showTime: true
      },
      {
        type: 'text',
        content: '你好，有什么可以帮你的吗？',
        sender: 'me',
        time: Date.now(),
        showTime: false
      }
    ];

    this.setData({
      messages: mockMessages
    });
  }
});

function formatNoticeTime(input: any): string {
  const now = new Date();
  const date = typeof input === 'number' ? new Date(input) : new Date(input);
  if (isNaN(date.getTime())) return '';

  //const diffMs = now.getTime() - date.getTime();
  //const oneDayMs = 24 * 60 * 60 * 1000;

  // 同一天：显示 HH:mm
  if (isSameDay(now, date)) {
    return format(date, 'HH:mm');
  }

  // 昨天
  const yesterday = new Date(now.getFullYear(), now.getMonth(), now.getDate() - 1);
  if (isSameDay(date, yesterday)) {
    return '昨天';
  }

  // 同一周内（含本周其他天）：返回星期几
  if (isSameWeek(now, date)) {
    const weekdays = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
    return weekdays[date.getDay()];
  }

  // 其他：ISO-like 到分钟（当地时区）
  return format(date, 'yyyy-MM-dd HH:mm');
}

function isSameDay(a: Date, b: Date): boolean {
  return a.getFullYear() === b.getFullYear() && a.getMonth() === b.getMonth() && a.getDate() === b.getDate();
}

function getWeekStart(d: Date): Date {
  const day = d.getDay(); // 0 周日
  const diff = d.getDate() - day; // 本周周日的日期
  return new Date(d.getFullYear(), d.getMonth(), diff);
}

function isSameWeek(a: Date, b: Date): boolean {
  const sa = getWeekStart(a);
  const sb = getWeekStart(b);
  return isSameDay(sa, sb);
}

function pad(num: number): string {
  return num < 10 ? `0${num}` : String(num);
}

function format(d: Date, pattern: string): string {
  const map: Record<string, string> = {
    'yyyy': String(d.getFullYear()),
    'MM': pad(d.getMonth() + 1),
    'dd': pad(d.getDate()),
    'HH': pad(d.getHours()),
    'mm': pad(d.getMinutes()),
  };
  return pattern.replace(/yyyy|MM|dd|HH|mm/g, (m) => map[m]);
}