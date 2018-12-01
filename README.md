# iPET 项目管理系统服务端

## 功能

- 1.项目立项：立项Excel模板导入、项目查询、创建、更新、激活、项目信息Excel导出；
- 2.项目计划：项目计划、周报、成本统计、风险与问题、项目干系人的查询、增、删、改及导出Excel；
- 3.项目验收：实施清单、交付物、软硬件、工勘要求的查询、增、删、改；拓扑图、物理图上传、下载及删除以及导出Excel；
- 4.项目结项：申请结项、结项审批的查询、增、改、查、发起、审批及导出Excel；
- 5.消息通知：系统推送及前端获取消息。

## 设计目标

- 构建方便快捷项目管理系统

## API设计

- This is [IPET项目管理测试系统](http://172.20.3.131:8088/ipet/v1/swagger-ui.html) swagger link
- 或者见/doc/markdown/all.down及/doc/swagger/swagger.json

# 实现

### 总览

- spring-boot为基础；mybatis作为和mysql数据库的交互框架；spring security 用做用户鉴权；
spring schedule用做实现定时任务调度；poi用做文件的上传和下载；logback用做日志记录；
redis 用作数据缓存；websocket用作消息通知。

### 框架
- spring-boot (1.5.14.RELEASE)

- mybatis-spring-boot-starter (1.3.2)

- mysql-connector-java (5.1.46)

- springfox-swagger2 (2.7.0)

- springfox-swagger-ui (2.7.0)

- spring-boot-starter-data-redis(1.5.14.RELEASE)

- spring-boot-starter-security （1.5.14.RELEASE）

### 依赖
- cassandra-all(0.8.1)
- poi-ooxml(3.9)
- pagehelper（4.1.6）
- dozer-spring（5.5.1）
- spring-boot-starter-websocket（1.5.14.RELEASE）
- spring-boot-starter-data-ldap（1.5.14.RELEASE）
- commons-lang3 (3.4)
- spring-restdocs-mockmvc （1.1.3.RELEASE）
#### 基础设施

- JDK 8
- Docker (18.03.1)
- MySQL （5.7）

#### 服务

- Redis(4.0.11)

### 编译

`mvn clean package`

### 已知问题

## 开发规范

### 分支

- 有改动时，新建专门的开发分支，等开发完成并通过测试之后，提交合并到master的MR
- 开发分支名称可使用小写字母、数字和下划线
- 如果不同开发者之间的工作紧密耦合，可使用同一个开发分支；否则应使用不同的开发分支

### 代码

- 必要的注释信息
- 分级输出详细的日志，能够达到通过看日志看出程序的运行状态
- 及时清理不用的代码

### 测试

- 每个模块的开发者都要自行实现单元测试，代码覆盖率应高于70%
- 在集成测试/联调之前，每个开发者保证自己的模块通过了所有单元测试
- 开发组分工实现功能测试的用例

### 接口文档生成

- 目前接口使用Swagger生成，可通过访问 测试环境 http://172.20.3.131:8082/ipet/v1/swagger-ui.html 查看
- 系统基于Swagger可生成json以及markdown文档
- 生成文档方式已集成maven中，使用 mvn clean test 或 mvn clean package 即可在ipet-server/doc下生成文档


### 消息

- 消息采用websocket实现，见com.sensetime.iva.ipet.common.config.WebSocketConfig
- 目前消息只有系统消息，当事件触发时，若相关人在线，会推送相关消息到指定人
- 前端应在页面加载时调用  未读消息 接口获取该用户未读消息列表
- 当申请结项成功后，会将该申请消息推送给所有拥有 Resource.code为IConstant.PROJECT_CONCLUSION_APPROVAL_RESOURCE_CODE 权限的用户
- 并生成对应的未处理状态的 message 数据，当任一用户审批后，会自动设置对应消息为已处理，同时推送 event为 IConstant.REFRESH_MESSAGE 消息，前端用于刷新未读消息列表
- 其他消息用户触发后，前端应调用 处理消息 接口将消息状态设置未已处理

### 权限

- 目前项目使用Spring Security 权限框架，见 com.sensetime.iva.ipet.common.config.security 包下
- 应用启动时会初始化所有资源（添加新资源，清除无效资源），并按照规则初始化 超级管理员、管理员、项目经理 角色权限
- 通过request url 和 method 获取redis中缓存资源以及对应角色，比对请求用户的角色，进行权限校验
- 登陆后会将用户信息存放入redis，每次成功请求会刷新redis超时时间，当超时时间内未操作，清除数据用户再次访问需要重新登陆
- 当检测到未登录或者登陆异常，会重定向到 /login_p 并提示未登陆，前端收到该响应后应跳转到登陆页面
- 通过在controller function上添加自定义注解@MyResources，应用初始化时会动态加载资源
- 可通过application.yml 中的 isOpenSecurity 属性开启或关闭权限验证
- 可通过application.yml 中的 isOpenScanResource 属性开启或关闭自动扫描资源功能

### docker部署镜像文件到测试机的流程
- 将application.yml 的spring.profiles.active设置为test(测试环境)。
- 将application.yml 的redis的host改成redis服务所在服务器的ip，当前测试环境ip是172.20.3.131。
- 执行命令: ``mvn clean package``将项目打成jar包。使jar包的名称及后缀名与\ipet-server\doc\docker\Dockerfile里的jar名称保持一致。否则无法生成镜像文件。
- docker verson: 18.03.1-ce (``yum install docker`` 安装成功后 ``service docker start`` 启动docker容器)。
- 测试服务器上手动在根目录创建/ipet文件夹(此目录及子目录下放置jar包和DockerFile以及对应运行环境的配置文件)。
- 将项目jar包和DockerFile上传到/ipet文件夹下(rz命令上传 不支持就``yum install -y lrzsz``)。
- 进入/ipet目录，创建/config文件夹。进入config文件夹，根据服务运行的环境创建dev、test、prod文件夹。再在每个环境的文件夹下上传对应的application-*.yml。
- 进入/ipet目录执行命令: ``docker build -t ipet:v1.0.0 .`` (双引号内容为镜像明称，`` .``表示当前执行路径下的Dockerfile)。
- 执行成功后最后又Successfully标识以及创建镜像成功后的IMAGE ID。
- 也可执行命令: ``docker images`` 查看ipet的镜像信息。需要用到IMAGE ID。
- 执行命令: ``docker run --restart=always --name ipet -p 8088:8088 -v /home/sensetime/ipet/load:/home/sensetime/ipet/load  -v /home/sensetime/ipet/server_log:/home/sensetime/ipet/server_log -v /ipet/config/test:/ipet/config -v /etc/localtime:/etc/localtime:ro --privileged=true -d  IMAGE ID``。

### 命令及注释

| 命令                                                                | 注释           | 
| ------------------------------------------------------------------- | -------------- |
| --restart=always                                              | 表示当容器退出代码为非0时，Docker会尝试自动重启该容器|
| --name ipet                                                         | 命名一个容器名称，后续可以启动，停止|
| -p 8088:8088                                                        | -p 暴露端口；前一个8088是宿主机端口(访问端口)，后一个8088为容器端口。表示端口映射关系。端口号取决于application-*.yml的server-port配置|
| -v /home/sensetime/ipet/load:/home/sensetime/ipet/load              | -v 目录挂载，冒号前面是宿主机目录，后面是docker容器目录。将用户的上传文件保存到宿主机上。路径取决于application-*.yml的LoadFileConfig的配置|
| -v /home/sensetime/ipet/server_log:/home/sensetime/ipet/server_log  |用于保存日志路径，路径取决于logback-spring.yml的springProfile的配置|
|-v /ipet/config/test:/ipet/config                                    |将容器的config和宿主机config/test文件映射，这样可以容器外修改配置重启容器即可|
|-v /etc/localtime:/etc/localtime:ro                                  |统一宿主机与容器的时间|
| -d                                                                  | 后台执行|
| IMAGE ID                                                            |Dockerfile创建成功后的镜像ID|
- 执行命令: ``docker ps`` 可以看到镜像在容器中的运行信息。有个CONTAINER ID。
- 执行命令：``docker logs CONTAINER ID`` 可查看该容器的运行日志。
- 停止ipet服务可执行命令: ``docker stop CONTAINER ID``。
- 再次启动ipet服务可执行命令: ``docker start CONTAINER ID`` 或者 ``docker start ipet``(ipet为创建时--name的名称)。
- 删除ipet容器可执行命令: ``docker rm CONTAINER ID``。
- 删除ipet镜像可执行命令: ``docker rmi IMAGE ID``。

### 全局异常处理

- 已集成ExceptionHandler捕获全局异常，并添加BusinessException自定义异常
- 异常以ResponseBody{code:xxxxx,msg:删除用户失败(请求操作+"失败"),desc: NullPointerException: projectId is null (RootCauseMessage)} 形式返回

## 项目目录及部分文件介绍
### /doc

#### /backendDocker/Dockfile
- 用于容器化部署ipet后端项目的命令文件，详细信息见上方*docker部署镜像文件到测试机的流程*

#### /designDocker
- 项目主要流程示意图

#### /fontendDocker/Dockerfile&nginx.conf
- 前端容器化部署的命令文件和容器中nginx服务器的配置信息

#### /markdown/all.md 和 /swagger/swagger.json
- 执行maven的test或package命令时会生成全新的API信息。

#### com.sensetime.iva.ipet.common
- 全部为静态常量包含（Excel的行和列、http响应码、项目导入及导出的标题及属性、异常信息、响应前端的信息）。

#### com.sensetime.iva.ipet.config
-  相关配置文件：包含项目上传文件的路径、鉴权、数据库、redis、session和websocket相关配置。
- /dozer  只用于List VO与Entity的相互赋值，两边都有的字段才会赋值，效率较低。
- /filter logback日志输出中增加MDC参数选项。
- /security 登陆、鉴权。

#### com.sensetime.iva.ipet.entity
- POJO

####  com.sensetime.iva.ipet.mapper
- 包含增、删、改、查及一些自定义的接口方法。

#### com.sensetime.iva.ipet.scheduler

#### 定时任务：

- 1.每周一对存在的项目周报和工时进行自动新增本周的周报和工时。

- 2.每月初新建一条本月数据，方便后期报表的日期对比。
 

#### com.sensetime.iva.ipet.service
- 包含增、删、改、查及一些自定义的service接口方法
- /impl  实现service的接口方法，包含增、删、改、查及一些自定义的方法

#### com.sensetime.iva.ipet.util
- 工具类 包含获取登陆用户、数字与汉字互相转换、日期转化、导出Excel、
MD5加密、获取对应MDC用于日志的key、响应体生成。

#### com.sensetime.iva.ipet.vo
- 用于与前端交互的特定java类。

#### com.sensetime.iva.ipet.web
- /annotation 用于注解资源。
- /controller 处理前端请求及简单逻辑判断并返回相应信息。
- /exception 自定义异常（用于业务中抛出既定异常）和全局异常处理。
- /interceptor  暂时没用到。

## ipet项目整体Docker镜像部署生产机流程

### 配置文件
- 将`application.yml`中的`active`设置为`prod`。
- 将`application-prod.yml`配置好。
- 查看前端的dist文件夹下的**config.js**中的 host是不是配置从了生产机的ip。
- `mvn clean package` 将项目打成**ipet-server-1.0.jar**的jar包，包名不可更改(后端Dockerfile会用到该包名)。
- 按照下列目录树存放文件,并压缩成**ipet.zip**文件
```
ipet
│   Dockfile(后端)
│   ipet-server-1.0.jar    
│   backup.sh
└───config
│   │
│   └───prod
│          application-prod.yml
│ 
└───web
    │   Dockfile(前端)
    │   nginx.conf
    │ 
    └───dist(前端打包的文件夹)
```

### 镜像制作

#### 后端镜像

- 当前远程连接LINUX服务器工具是SecureCRT
- 登陆服务器 **root**用户下 `cd /`
- 将**ipet.zip**压缩包通过`rz`命令上传到根目录,并`unzip ipet.zip`命令解压得到**ipet**文件夹
- `cd /ipet`命令进入**ipet**文件夹
- 生成镜像的命令`docker build -t ipet:v1.0 .`
- 运行镜像的命令`docker run --restart=always --name ipet_1.0 -p 8082:8082 -v /home/sensetime/ipet/load:/home/sensetime/ipet/load  -v /home/sensetime/ipet/server_log:/home/sensetime/ipet/server_log  -v /ipet/config/prod:/ipet/config -v /etc/localtime:/etc/localtime:ro --privileged=true -d ${IMAGE ID}`**${IMAGE ID}** 是ipet镜像id
- 查看ipet容器运行情况命令`docker ps`，如果没有看见就执行命令`docker ps -a`找到ipet容器的**CONTAINER ID**,再执行命令`docker logs ${CONTAINER ID}`就能看到报错信息

#### 前端镜像
- `cd /ipet/web`命令进入**web**文件夹
- 生成镜像的命令`docker build -t ipet_web:v1.0 .`
- 运行镜像的命令`docker run -d -p 8080:8080 -v /home/sensetime/ipet/nginx/logs:/usr/local/nginx/logs/ipet --name ipet_web_1.0 ${IMAGE ID}`
- 查看容器运行情况同上

#### 后期修改配置文件

- 后端： 先`cd /ipet/prod`再`vim application-prod.yml`,可以修改mysql、redis服务器、文件上传下载地址及端口等配置。
- 后端代码发生修改需要打包重新镜像部署。
- 前端：后端服务器ip和地址发生变化的时候需要修改容器内dist文件夹下的**config.js**里面的配置，前端端口发生变化时修改**nginx.conf**配置，具体位置查看前端Dockerfile。
- 进入容器命令`docker exec -it ${CONTAINER ID} /bin/bash`,后续操作文件与宿主机一样。
- 前端代码发生修改需要打包重新镜像部署。
- 备注：如果文件存储服务器的位置或端口发生变化，需要先停掉原来运行中的服务，重新开启另一个容器进行挂载的配置启动。

###mysql数据库自动备份

- 执行命令`chmod +x /ipet/backup.sh`添加执行权限
- 执行命令`crontab -e` 添加定时计划
- 添加以下内容保存退出
   ```
    #ipet项目管理系统数据备份
     0 5 * * * /ipet/backup.sh
    ```
- 查看cron的运行情况：`vim /var/log/cron.log`或`tail -f /var/log/cron`
