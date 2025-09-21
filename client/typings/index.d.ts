/// <reference path="./types/index.d.ts" />

interface IAppOption {
  globalData: {
    systemSetting: {
      screenWidth: number,
      screenHeight: number
    }
    userRole: string;
    userInfo?: WechatMiniprogram.UserInfo,
  }
  userInfoReadyCallback?: WechatMiniprogram.GetUserInfoSuccessCallback,
}