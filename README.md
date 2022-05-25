1.开发注册功能
    1.1 访问注册页面
        点击顶部区域链接，打开注册页面
    1.2 提交注册数据
        通过表单提交数据
        服务端验证账号是否存在，邮箱是否已经注册
        服务端发送激活邮件
    1.3激活账号
        点击邮件中的链接，访问服务端的激活服务
        
2.生成验证码
    Kaptcha
        导入jar包
        编写kaptcha配置类
        生成随机字符，生成图片
        
3.开发登录功能，退出功能
    访问登录页面
        点击顶部区域的链接，打开登录页面
    登录
        验证账号密码验证码
        成功时，生成登录凭证，发放给客户端
        失败时，跳转回登录页
   退出
        将登陆凭证修改为失效状态
        跳转至网站首页
        
4.显示登录信息
    拦截器示例
        定义拦截器，实现HandlerInterceptor
        配置拦截器WebMvcConfig, 它需要实现WebMvcConfigurer，为它指定拦截，排除的路径
    拦截器应用
        在请求开始时查询登录用户
        在本次请求中持有用户数据
        在模板视图上显示用户数据
        在请求结束时清理用户数据
        
5.账号设置
    上传文件
        请求：必须是post请求
        表单：enctype="multipartFile处理上传文件
        SpringMvc： 通过MultipartFile处理上传文件
    开发步骤
        访问账号设置页面
        上传头像
        获取头像
        
6.检查登录状态
    使用拦截器
        在方法前面标注自定义注解
        拦截所有请求，只处理带有该注解的方法
    自定义注解
        常用的元注解
        @Target|  作用在哪：方法。类。属性上
        @Retention|   声明自定义注解保存有效的时间点，编译时还是运行时
        @Document|    声明文档的时候要不要带上这个注解
        @Inherited      用于继承；子类继承父类的注解
        如何读取注解
        Method.getDeclaredAnnotations()     
        Method.getAnnotation(Class<T> annotationClass)