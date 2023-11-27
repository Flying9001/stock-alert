## Stock Alert Docker 部署文档

Stock Alert 项目 Docker 一键部署文档，以centOS7(Linux) 为例。



### 1 安装 Docker

```bash
sudo yum install -y yum-utils

sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

sudo yum install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# 启动 docker
sudo systemctl start docker

# 设置开机启动
sudo systemctl enable docker
```



### 2 下载与配置项目信息

在 Release 页面下载最新的部署包  

[https://github.com/Flying9001/stock-alert/releases](https://github.com/Flying9001/stock-alert/releases)  

创建项目目录  

```
mkdir /opt/springboot
```

将项目部署包放到目录下进行解压  

```
cd /opt/springboot

tar -zxvf stock-alert-1.0.0.tar.gz 

tar -zxvf stock-alert-schedule-1.0.0.tar.gz 
```

修改项目配置，需要修改的配置文件为:  

```bash
# redis 配置
./stock-alert/docker-compose.yml

# 数据库与消息队列配置
./stock-alert/docker.env
```

修改项目环境变量，与 docker 中的设置密码信息保持一致  

```bash
./stock-alert/conf/application-test.yml
./stock-alert-schedule/conf/application-test.yml
```



### 3 运行项目

进入 stock-alert 项目目录，执行项目基础环境搭建脚本  

```bash
cd /opt/springboot/stock-alert

docker compose up -d 
```

执行此脚本会创建mysql、redis、rabbitMQ docker应用以及 stock-alert 应用  

等待环境创建完成后，连接数据库，执行初始化脚本  

[在线最新初始化SQL脚本](https://github.com/Flying9001/stock-alert/tree/master/doc/sql)  

```bash
./doc/sql/stock_alert_create.sql
./doc/sql/stock_source_init_data.sql
```

进入 stock-alert-schedule 项目目录，启动定时任务项目  

```bash
cd /opt/springboot/stock-alert-schedule

docker compose up -d
```

项目执行日志的目录  

```
/opt/springboot/logs/stock-alert
/opt/springboot/logs-stock-alert-schedule
```

在执行 stock-alert-schedule 项目时，如果未设置管理员账号，系统会自动生成一个，账号密码随机生成，在启动日志中，格式为  

```verilog
init-初始化管理员账号-account:xxxx,passcode:xxxxx
```



### 4 日常运维指令

#### 4.1 管理 stock-alert 项目  

先进入项目目录  

```bash
cd /opt/springboot/stock-alert
```

执行启动脚本  

```bash
./start_docker.sh
```

停止项目，执行停止脚本

```bash
./shutdown_docker.sh
```

如果脚本执行失败，可安装 dos2unix  

```bash
sudo yum install -y dos2unix 
```

创建转换脚本 

dos2unix.sh  

```bash
#!/bin/sh
# stock-alert 项目执行脚本格式转换

# 项目目录
path=/opt/springboot/

dos2unix ${path}/stock-alert/bin/startup.sh
dos2unix ${path}/stock-alert/bin/shutdown.sh
dos2unix ${path}/stock-alert/start_docker.sh
dos2unix ${path}/stock-alert/shutdown_docker.sh

dos2unix ${path}/stock-alert-schedule/bin/startup.sh
dos2unix ${path}/stock-alert-schedule/bin/shutdown.sh
```

添加脚本执行权限  

```bash
sudo chmod +x ./dos2unix.sh
```



#### 4.2 管理stock-alert-schedule定时任务项目

进入项目目录

```bash
cd /opt/springboot/stock-alert-schedule
```

编译项目镜像

```
docker compose build
```

后台启动容器

```bash
docker compose up -d
```

停止项目

```bash
docker compose down
```

