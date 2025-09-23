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
     |-----miniprogram
           |-----components         //组件
           |-----images             //图片
           |-----lib        
           |-----miniprogram_npm    //快速构建npm后生成的文件夹
           |-----mock               //模拟数据
           |-----pages              //所有页面
           |-----utils              //自定义工具
|----server 服务器
     |-----
|----bddesign.sql                   //生成数据库
|----README.md                      //项目简介
```

### 部署步骤：

1. 通过git将代码拷贝到电脑上
2. 打开微信开发者工具导入client文件夹，将相关小程序配置更改为自己的配置
3. 通过“npm install”安装所有依赖组件库，点击“工具” -> "构建npm"
4. 打开server文件夹，在allication.properties中将数据库配置更改你的数据库连接。
5. 打开mysql，导入生成数据库的sql文件。
6. （可选）本项目后端使用了腾讯云服务，需要开通相关的API。将生成的密钥配置到allication.properties中。
