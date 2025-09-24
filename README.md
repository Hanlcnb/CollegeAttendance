# 课堂考勤系统

### 项目功能：

用户可以自行修改个人信息，主页部分课程表展示了学生当前时间的课程安排；通知页面类似于社交媒体的消息列表，让用户交流更加便捷；系统实现了多种签到方式供教师选择

> 前端部分截图

<table>
     <tr>
          <td ><center><img src="/photo/index.png"/>微信授权登录</center></td>
          <td ><center><img src="/photo/USPW.png"/>用户名密码登录</center></td>
     </tr>
</table>

<table>
     <tr>
          <td ><center><img src="/photo/home.png"/>主页</center></td>
          <td ><center><img src="/photo/notice.png"/>通知</center></td>
     </tr>
</table>

<table>
     <tr>
          <td ><center><img src="/photo/profile.png"/>个人</center></td>
          <td ><center><img src="/photo/messages.png"/>聊天页面</center></td>
     </tr>
</table>

### 前端：

微信小程序

### 后端：

Springboot Netty Mybatis , MinIO

### 数据库：

Mysql ，Redis , 



### 项目结构：

```
CollegeAttendance
|----client 客户端
     |----miniprogram
          |----components                // 组件（课程日程、刷脸核验等）
          |----images                    // 图片资源（头像、图标、课程图标、tabbar）
          |----lib                       // 第三方库（如 math.min.js）
          |----miniprogram_npm           // 构建生成的 npm 依赖（@vant、tdesign、dayjs 等）
          |----mock                      // 模拟数据与请求封装
          |----pages                     // 所有页面（打卡、课程、消息、发布、设置等）
          |----utils                     // 工具方法与请求封装
          |----app.ts / app.wxss / app.json
|----server 服务器
     |----pom.xml
     |----miniprogram/utils/websocket.js // 小程序端 WebSocket 辅助
     |----src
          |----main
               |----java
                    |----com.hanlc.attendence
                         |----AttendenceApplication.java
                         |----common                 // 统一返回、人脸识别服务
                         |----config                 // 拦截器、CORS、JWT、WebSocket、Jackson、Minio 等
                         |----controller             // 登录、打卡、课程、消息、二维码、上传等接口
                         |----entity
                              |----domain            // 实体：用户、课程、班级、考勤记录等
                              |----enums             // 枚举：打卡方式、消息类型、结果码
                              |----request           // 请求对象
                         |----handler                // 全局异常处理
                         |----mapper                 // MyBatis Mapper 接口
                         |----model/vo               // 视图对象（如 WxLoginResponse）
                         |----netty                  // WebSocket 服务与心跳
                         |----service (+impl)        // 业务服务层
                         |----utils                  // JwtUtils、RandomUtils 等
               |----resources
                    |----mapper                     // *Mapper.xml
|----README.md                          // 项目简介
```

### 部署步骤：

1. 通过git将代码拷贝到电脑上
```
git clone https://github.com/Hanlcnb/CollegeAttendance.git
```
3. 打开微信开发者工具导入client文件夹，更改project.config.json，其中appid的值要改为自己的小程序appid
4. 通过“npm install”安装所有依赖组件库，点击“工具” -> "构建npm"
5. 打开server文件夹，在allication.properties中将带有*的属性修改为个人设置
6. 打开mysql，导入生成数据库的sql文件。
7. （可选）本项目后端使用了腾讯云服务，需要开通相关的API。将生成的密钥配置到allication.properties中。
