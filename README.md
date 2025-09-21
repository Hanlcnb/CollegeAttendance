# 课堂考勤系统

### 项目功能：

实现了课堂中教师让学生签到的功能，提供了多种签到方式（密码，手势，二维码和人脸识别）。同时为了方便教师和学生进行联系，本项目具有聊天功能，帮助师生实时沟通。首页中的课程表有利于提醒学生课程安排。

部分截图：

![{CAC98226-3201-45AD-96D3-C46E664F3633}](C:\Users\Hanlcnb\AppData\Local\Packages\MicrosoftWindows.Client.CBS_cw5n1h2txyewy\TempState\ScreenClip\{CAC98226-3201-45AD-96D3-C46E664F3633}.png)



### 前端：

微信小程序

### 后端：

springboot+redis+netty+mybatis

### 数据库：

mysql



该系统基本实现考勤签到的功能要求，但要上线实际使用任需完善许多功能。

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
```

### 部署步骤：

1. 通过git将代码拷贝到电脑上
2. 打开微信开发者工具导入server文件夹，将相关小程序配置更改为自己的配置
3. 通过“npm install”安装所有依赖组件库，点击“工具” -> "构建npm"
4. 打开client文件夹，在allication.properties中将数据库配置更改你的数据库连接。
5. 打开mysql，导入生成数据库的sql文件。
6. （可选）本项目后端使用了腾讯云服务，需要开通相关的API。将生成的密钥配置到allication.properties中。
