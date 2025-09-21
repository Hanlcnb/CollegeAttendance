// attendance.ts
const attendanceApp = getApp()
import { request } from '../../utils/request';

interface AttendanceRecord {
  id: string;
  courseName: string;
  status: 'present' | 'absent' | 'late' | 'leave';
  location?: string;
  remark?: string;
  checkInTime: Date
}

Component({
  data: {
    statusRange: 'all',
    dateRange: 'all',
    statusOptions: [
      { text: '全部状态', value: 'all' },
      { text: '正常', value: 'present' },
      { text: '缺勤', value: 'absent' },
      { text: '迟到', value: 'late' },
      { text: '请假', value: 'leave' }
    ],
    dateOptions: [
      { text: '全部时间', value: 'all' },
      { text: '最近一周', value: 'week' },
      { text: '最近一月', value: 'month' },
      { text: '最近三月', value: 'quarter' }
    ],
    attendanceRecords: [] as AttendanceRecord[],
    loading: false,
    page: 1,
    hasMore: true
  },

  methods: {
    onLoad() {
      this.loadAttendanceRecords()
    },

    // 加载考勤记录
    loadAttendanceRecords(refresh = false) {
      if (this.data.loading || (!refresh && !this.data.hasMore)) return

      this.setData({ loading: true })
      const page = refresh ? 1 : this.data.page;
      const studentID = attendanceApp.globalData.userInfo.id;
      const tmpStatus = this.data.statusRange;
      const tmpDateChange = this.data.dateRange;
      const serverURL = `${attendanceApp.config.apiBaseURL}/checkInRecords/student`
      request({
        url: serverURL,
        method: 'GET',
        data: {
          studenId: studentID,
          pages: page,
          statusRange: tmpStatus,
          dateRange: tmpDateChange
        }
      }).then((res: any) => {
        const { records, hasMore } = res.data
        this.setData({
          attendanceRecords: refresh ? records : [...this.data.attendanceRecords, ...records],
          page: page + 1,
          hasMore,
          loading: false
        })
      }).catch(() => {
        wx.showToast({
          title: '加载失败',
          icon: 'none'
        })
        this.setData({ loading: false })
      });
    },

    // 状态筛选变化
    onStatusChange(event: any) {
      this.setData({ status: event.detail })
      this.loadAttendanceRecords(true)
    },

    // 时间范围筛选变化
    onDateChange(event: any) {
      this.setData({ dateRange: event.detail })
      this.loadAttendanceRecords(true)
    },

    // 显示考勤详情
    showDetail(event: any) {
      const id = event.currentTarget.dataset.id
      wx.navigateTo({
        url: `/pages/attendance/detail/detail?id=${id}`
      })
    },

    // 下拉刷新
    onPullDownRefresh() {
      this.loadAttendanceRecords(true)
      wx.stopPullDownRefresh()
    },

    // 上拉加载更多
    onReachBottom() {
      this.loadAttendanceRecords()
    }
  }
})