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
        
7.过滤敏感词
    前缀树
        名称：Trie  字典树  查找树
        特点：查找效率高，消耗内存大
        应用：字符串检索，词频统计，字符串排序等
    敏感词过滤器
        定义前缀树
        根据敏感词，初始化前缀树
        编写过滤敏感词的方法
        
8.发布贴子
    AJAX请求，添加数据
    
9.贴子详情
    DiscussPostMapper
    DiscussPostService
    DiscussPostController
    index.html
        在贴子标题上增加访问详情页面的链接
    discuss-detail.html
        处理静态资源的访问路径
        复用index.html的header区域
        显示标题，作者，发布时间、贴子正文等内容
   
10.事务管理
    什么是事务
        事  务是由N步数据库操作序列组成的逻辑执行单元，这系列操作要么全执行，要么全放弃执行
    事务的特性
        原子性
        一致性
        隔离性
        持久性   
    事务的隔离性
        常见的并发异常
            第一类丢失更新（某一个事务的回滚，导致另外一个事务已更新的数据丢失），
            第二类丢失更新（某一个事务的提交，导致另外一个事务已更新的数据丢失）
            脏读，
            不可重复读，
            幻读
        常见的隔离级别
            读取未提交
            读取已提交
            可重复读
            串行化  
    实现机制
        悲观锁（数据库）
            共享锁（S锁）
                事务A对某数据加了共享锁之后，其他事务只能对该数据加共享锁，但不能加排他锁
            排它锁（X锁）
                事务A对某数据加了排他锁之后，其他事务对该数据既不能加共享锁，也不能加排他锁
        乐观锁（自定义） 
            版本号，时间戳等
            在更新数据前，检查版本号是否发生变化，若变化取消本次更新，否则就更新数据（版本号+1）
    Spring事务管理
        声明式事务
            通过xml配置，声明某方法的事务特征
            通过注解，声明某方法的事务特征
        编程式事务
            通过TransactionTemplate管理事务，并通过它执行数据库操作
  
11.添加评论
    数据层
        增加评论数据
        修改贴子的评论数量
    业务层
        处理添加评论的业务
        先增加评论，再更新贴子的评论数量
    表现层
        处理添加评论数量的请求
        设置添加评论的表单                  

12.私信列表
    私信列表
        查询当前用户的会话列表
        每个会话只显示一条最新的私信
        支持分页显示
    私信详情
        查询某个会话所包含的私信  
        支持分页显示
        
13.发送私信
    发送私信
        采用异步方法发送私信
        发送成功后刷新私信列表
    设置已读
        访问了私信详情时，将状态改为已读
  
14.统一异常处理（目前只使用了异常处理方案：统一异常控制器通知）
    @ControllerAdvice
        用于修饰类，表示该类是Controller的全局配置类
        在此类中，可以对Controller进行如下三种全局配置：
            异常处理方案
                    @ExceptionHandler
                             用于修饰方法，该方法会在Controller出现异常后被调用，用于处理捕获到的异常
            绑定数据方案
                    @ModelAttribute
                             用于修饰方法，该方法会在Controller方法执行前被调用，用于Model对象绑定参数
            绑定参数方案
                    @DataBinder
                             用于修饰方法，该方法会在Controller方法执行前被调用，用于绑定参数的转换器  
                             
15.统一记录日志（AOP）（无异常也需要记录日志）（主要是service业务中处理日志）
    AOP的概念
        面向切面编程，aop是一种编程思想，是对oop的补充，可以进一步提高编程效率
    AOP术语
        Weaving（织入）方式:
            编译时织入，需要使用特殊的编译器
            装载时织入，需要使用特殊的类装载器
            运行时织入，需要为目标生成代理对象
        Joinpoint:织入点
        Aspect:方面组件：
            Pointcut: 声明织入点位置
            Advice（通知）: 具体织入逻辑
    AOP的实现
        Aspectj
            Aspectj是语言级别的实现，它拓展了Java语言，定义了AOP语法
            Aspectj在编译期织入代码，它有一个专门的编译器，用来生成遵守Java字节码规范的class文件
        Spring AOP
            Spring AOP 使用纯Java实现，它不需要专门的编译过程，也不需要特殊的类装载器
            Spring AOP在运行时通过代理的方式织入代码，只支持方法类型的连接点
            Spring支持对Aspectj的集成 
                JDK动态代理
                    java提供的动态代理技术，可以在运行时创建【接口】的代理实例
                    spring aop 默认采用这种方式，在接口的代理实例中织入代码
                CGLib动态代理
                    采用底层的字节码技术，在运行时创建子类代理实例
                    当目标对象不存在接口时，spring aop 会采用此种方法，在子类实例中织入代码  
                    
16.Spring整合redis
    引入依赖
        spring-boot-start-redis
    配置Redis
        配置数据库参数
        编写配置类，构造RedisTemplate(spring中默认的key是object，但是我们一般使用string,所以重新配，用我们自己的方便)
    访问Redis
        redisTemplate.opsForValue()  //string   set/get
        redisTemplate.opsForHash()   //hash     put/get
        redisTemplate.opsForList()  //list      leftPush/leftPop
        redisTemplate.opsForSet()   //set       add/pop
        redisTemplate.opsForZSet() // zset`     add/rank|range
        
17.点赞（redis)
    点赞
        支持对贴子，评论的点赞
        第1次点赞，第二次点击取消
    首页点赞数量
        统计贴子的点赞数量
    详情页点赞数量
        统计点赞数量
        显示点赞状态      
        
18.我收到的赞（统计用户总共收到的赞，在主页展示）
    重构点赞功能
        以用户为key,记录点赞数量
        increment(key),decrement(key)
    开发个人主页
        以用户为key,查询点赞数量
        
19.关注 取消关注（userController，FollowController）
    需求
        开发关注，取消关注功能
        统计用户的关注数，粉丝数
    关键
        若A关注了b，则a是b的follower(粉丝)，b是a的followee(目标)
        关注的目标可以是用户，贴子，题目等，在实现时将这些目标抽象为实体
      
20.关注列表，粉丝列表
    业务层
        查询某个用户关注的人，支持分页
        查询某个用户的粉丝，支持分页
    表现层
        处理“查询关注的人”，”查询粉丝“请求
        编写“查询关注的人”，“查询粉丝”模板  
        
21.优化登录模块
    使用redis储存验证码
        验证码需要频繁的访问与刷新，对性能要求较高
        验证码不需要永久保存，通常在很短的时间后就会失效
        分布式部署时，存在session共享的问题
    使用redis存储登录凭证
        处理每次请求时，都要查询用户的登录凭证，访问频率非常高
    使用redis缓存用户信息
        处理每次请求时，都要根据凭证查询用户信息，访问的频率非常高
        
22.kafka相关知识
    早生有关原理：
        阻塞队列
            BlockingQueue（接口）
                解决线程通信的问题
                阻塞方法：put（存入队列） take（队列中取出）
            生产者消费者模式
                生产者：产生数据的线程
                消费者：使用数据的线程
            实现类
                ArrayBlockingQueue
                LinkedBlockingQueue
                PriorityBlockingQueue,SynchronousQueue,DelayQueue等
    Kafka入门
        简介
            kafka是一个分布式的流媒体平台
            应用：消息系统(本次使用），日志收集，用户行为追踪，流式处理
        特点
            高吞吐，消息持久化（硬盘的顺序读写速度很快，高可用），高可靠，高拓展性
        术语
            Broker（每一台kafka服务器）,Zookeeper（用来管理集群）
            Topic（主题【存储消息的位置】：点对点，发布订阅方式【kafka使用这种】）,Partition（对主题做分区，提高并发能力）,Offset（消息在分区存放的位置索引）
            Leader Replica（主副本：数据备，还可以处理数据）,Follower Replica（从副本：不可以处理数据，只做备份用）
    window使用
        修改 zookeeper.properties，server.properties中的一个路径
        启动window中zookeeper
            ① d:
            ② cd   D:\kafka\kafka_2.13-2.7.1
            ③ bin\windows\zookeeper-server-start.bat config\zookeeper.properties   
        启动kafka
            d:
            cd  D:\kafka\kafka_2.13-2.7.1
            bin\windows\kafka-server-start.bat config\server.properties 
        创建主题
            d:
            cd D:\kafka\kafka_2.13-2.7.1\bin\windows
            ③ 创建主题：
                kafka-topics.bat --create --bootstrap-server  localhost:9092 --replication-factor 1 --partitions 1 --topic test
            ④ 查看主题
                kafka-topics.bat --list --bootstrap-server localhost:9092
        生产者往哪一个服务器，哪一个主题发消息
            d:
            cd D:\kafka\kafka_2.13-2.7.1\bin\windows
            kafka-console-producer.bat --broker-list localhost:9092 --topic test
        消费者在哪个服务器，主题消费
            d:
            cd D:\kafka\kafka_2.13-2.7.1\bin\windows
            kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
23.spring整合kafka
    引入依赖
        spring-kafka
    配置kafka
        配置server,consumer
    访问kafka
        生产者
            kafkaTemplate.send(topic,data)
        消费者
            @KafkaListener(topics = {"test"})  
            public void handleMessage(ConsumerRecord record) {}
24.发送系统通知
    触发事件（主题）
        评论后，发布通知
        点赞后，发布通知
        关注后，发布通知
    处理事件
        封装事件对象
        开发事件生产者
        开发事件消费者        
25.显示系统通知
    通知列表
        显示评论，点赞，关注三种类型的通知
    通知详情
        分页显示某一类主题所包含的通知
    未读消息
        在页面头部显示所有的未读消息数量    
         
26.Elasticsearch入门（类比于一个数据库）
    简介
        一个分布式的，Restful风格的搜索引擎
        支持对各种类型的数据的检索
        搜索速度快，可以提供实时的搜索服务
        便于水平拓展，美秒可以以处理PB级海量数据
    术语
        索引:数据库
        类型：表（想废弃，用索引来代替对应表）（6.0用一个单词来代替了类型，7.0就彻底废弃了）
        文档：行，一条数据
        字段：一列
        --------------------------------
        集群：
        节点：集群中每一台es服务器
        分片：对索引的进一步划分，提高并发能力
        副本：对分片的备份，提高可用性 
    使用
        1.命令行示例：curl -X GET  "localhost:9200/_cat/health?v"
        2.postman
        
27.spring整合Elasticsearch
    引入依赖
        spring-boot-starter-elasticsearch
    配置elasticsearch
        cluster-name,cluster-nodes
    Spring Data Elasticsearch
        ElasticsearchTemplate
        ElasticsearchRepository
        
28.开发社区搜索功能
    搜索服务
        将贴子保存到Elasticsearch服务器
        从Elasticsearch服务器删除贴子
        从Elasticsearch服务器搜索贴子
    发布事件
        发布贴子时，将贴子异步的提交到elasticsearch服务器
        增加评论时，将贴子异步的提交到elasticsearch服务器
        在消费组件中增加一个方法，消费贴子发布事件
    显示结果
        在控制器中处理搜索请求，在HTML上显示搜索结果
  
29.Spring Security
    简介
        Spring Security是一个专注于为Java应用程序提供身份认证和授权的框架，它的强大之处在于它可以轻松拓展以满足自定义的需求
    特征
        对身份的认证和授权提供全面的，可拓展的支持
        防止各种攻击，如会话固定攻击，点击劫持，csrf攻击等
        支持与servlet API，spring mvc等Web技术集成 
    拓展知识
    ------------------------------------------------------------------------------------
    ||JavaEE---------------|---------Spring MVC---------------------------------------||
    ||   Filter   ->       |  DispatcherServlet   ->   Interceptor   ->   Controller  || 
    ------------------------------------------------------------------------------------   
    
30.权限控制
    登录检查
        之前采用拦截器实现登录检查，这是简单的权限管理方案，现在将其废弃
    授权配置
        对当前系统内包含的所有的请求，分配访问权限（普通用户0，管理员1，版主2）
    认证方案（认证使用自己的）
        绕过Security认证流程，采用系统原来的认证方案
    CSRF配置（访问表单页面，服务端会产生一个隐含的tocken，携带给客户端，防止ticket被泄密后造成不良结果)（该系统跳过csrf检测了）
        防止CSRF攻击的基本原理，以及表单，AJAX相关的配置   
        
31.置顶加精删除[账号：0-普通用户; 1-超级管理员; 2-版主;]
    功能实现
        点击置顶，修改贴子类型
        点击加精，删除，修改贴子的状态
    权限管理
        版主可以执行置顶，加精操作
        管理员可以执行删除操作
    按钮的显示
        版主可以看到置顶，加精按钮
        管理员可以看到删除按钮
        