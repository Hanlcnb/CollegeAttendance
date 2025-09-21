// pages/chat/chat.ts
Page({

  /**
   * 页面的初始数据
   */
  data: {
    courseName: '高等数学',
    anchor: '',
    avatar: '',
    myavatar: '/miniprogram/images/avatar/cat.jpg',
    courseId: 6 ,
    messages: [],           //消息数组包含了(messageId,from,content,time)
    input: '',
    keyboardHeight: 0,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  handleKeyboardHeightChange(event: any){
    const { height } = event.detail
    if(!height) return;
    this.setData({ keyboardHeight: height});
  },
  handleBlur(){
    this.setData({ keyboardHeight: 0});
  },
  handleInput(event:any){
    this.setData({ input: event.detail.value });
    
  },

  sendMessage(){
    const { courseId , messages , input: content} = this.data;
    if(!content) return;
    const message = { messageId: null , from: 0 , content , time: Date.now() ,read: true};
    messages.push(message);
    this.setData({input: "" , messages});
    wx.nextTick(this.scrollToBottom);
  },
  scrollToBottom(){
    this.setData({ anchor : "bottom"});
  }
})