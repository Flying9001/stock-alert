## Stock Alert 部署文档

Stock Alert 项目部署文档，已 centOS 7(Linux) 为例。



### 1 JDK

JDK8 Linux 64位 备用下载: [jdk-8u212-linux-x64.tar.gz](https://mega.nz/file/rbYHFYYB#eDhYhd7nfxf3UClqUw5Ra6BMDm-sjkDfmSYJnchwLcg "https://mega.nz/file/rbYHFYYB#eDhYhd7nfxf3UClqUw5Ra6BMDm-sjkDfmSYJnchwLcg")  

配置文件路径

```
/etc/profile
```


```
JAVA_HOME=/path/to/jdk/install
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
PATH=$JAVA_HOME/bin:$PATH
export JAVA_HOME CLASSPATH PATH
```

让配置文件生效  

```
source /etc/profile
```



### 2  MySQL

[CentOS 7 安装MySQL 5.7 或安装指定版本MySQL](https://blog.csdn.net/Mrqiang9001/article/details/101377149 "https://blog.csdn.net/Mrqiang9001/article/details/101377149")  

相关指令:  

```bash
sudo yum install -y wget 

wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm

sudo yum localinstall mysql80-community-release-el7-3.noarch.rpm

sudo yum install -y yum-utils

sudo yum install -y mysql-community-server

sudo systemctl start mysqld

sudo systemctl enable mysqld

sudo grep 'temporary password' /var/log/mysqld.log

mysql -u root -p

ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyNewPass4!';

FLUSH PRIVILEGES;
```

**MySQL 8.0 设置远程连接**  

[Mysql 8 remote access](https://stackoverflow.com/questions/50570592/mysql-8-remote-access)  

```
CREATE USER 'root'@'%' IDENTIFIED BY 'your_pass';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
```

[Connection Java-MySql : Public Key Retrieval is not allowed](https://stackoverflow.com/questions/50379839/connection-java-mysql-public-key-retrieval-is-not-allowed)  

设置连接属性  

```
allowPublicKeyRetrieval=true&useSSL=false
```

配置文件

```
/etc/my.cnf
```

设置不区分大小写:  

```
[mysqld]
lower_case_table_names = 1
```

**一定要在安装完成之后就配置**  



### 3 Redis 

[How to Install Latest Redis on CentOS 7](https://syslint.com/blog/tutorial/how-to-install-latest-redis-on-centos-7)  

安装指令:  

```bash
sudo yum -y install http://rpms.remirepo.net/enterprise/remi-release-7.rpm

sudo yum --enablerepo=remi install redis
```

配置文件:  

```
/etc/redis.conf
```

修改配置:  

```
bind 0.0.0.0
requirepass your_pass
protected-mode no
daemonize yes
```

修改完配置后执行以下指令:  

```bash
sudo systemctl restart redis

sudo systemctl enable redis
```



### 4 RabbitMQ

[centOS 7 安装 RabbitMQ 教程](https://blog.csdn.net/Mrqiang9001/article/details/86585482)  

新版 RabbitMQ（3.12+）已经不支持 centOS 7 ，以下指令仅适用于 centOS7  

```bash
sudo yum install -y epel-release

sudo yum install -y wget

wget https://github.com/rabbitmq/erlang-rpm/releases/download/v21.2.3/erlang-21.2.3-1.el7.centos.x86_64.rpm

wget https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.7.11-rc.1/rabbitmq-server-3.7.11.rc.1-1.el7.noarch.rpm

sudo yum install -y erlang-21.2.3-1.el7.centos.x86_64.rpm

sudo yum install -y rabbitmq-server-3.7.10-1.el7.noarch.rpm
```

配置  

```
sudo systemctl start rabbitmq-server.service

sudo systemctl enable rabbitmq-server.service

sudo rabbitmq-plugins enable rabbitmq_management

sudo chown -R rabbitmq:rabbitmq /var/lib/rabbitmq/

sudo rabbitmqctl add_user admin_user_name admin_password
sudo rabbitmqctl set_user_tags admin_user_name administrator
sudo rabbitmqctl set_permissions -p / admin_user_name ".*" ".*" ".*"
```



### 5 Nginx

[Installing Prebuilt RHEL, CentOS, Oracle Linux, AlmaLinux, Rocky Linux Packages](https://docs.nginx.com/nginx/admin-guide/installing-nginx/installing-nginx-open-source/#installing-prebuilt-rhel-centos-oracle-linux-almalinux-rocky-linux-packages)  



安装指令:  

```bash
sudo yum install epel-release

sudo yum install nginx

sudo systemctl enabled nginxd
```

如遇到安装后请求返回 502，可参考:  

[(13: Permission denied) while connecting to upstream:[nginx\]](https://stackoverflow.com/questions/23948527/13-permission-denied-while-connecting-to-upstreamnginx)  

```bash
setsebool -P httpd_can_network_connect 1
```

配置文件路径:  

```
/etc/nginx/nginx.cnf
```

配置文件示例:  

```nginx

#user  nobody;
worker_processes  1;


#error_log  log/error.log;
#error_log  log/error.log  notice;
#error_log  log/error.log  info;

#pid        log/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';

    access_log  /var/log/nginx/access.log  main;

    sendfile        on;
    tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    gzip  on;

    server {
        listen       80;
        server_name  xxx.com;

        return 301 https://$host$request_uri;
        location / {
            proxy_pass http://127.0.0.1:443;
          }
		  
  
    }
	

    # HTTPS server
    server {
        listen       443 ssl;
        server_name  xxx.com;
        
        access_log  /var/log/nginx/host.access.log  main;

        ssl_certificate      /etc/ssl/xxx.pem;
        ssl_certificate_key  /etc/ssl/xxx.key;
        ssl_session_cache    shared:SSL:1m;
        ssl_session_timeout  5m;
        ssl_ciphers  HIGH:!aNULL:!MD5;
        ssl_prefer_server_ciphers  on;

        location / {
            proxy_pass http://127.0.0.1:8089;
            proxy_set_header  Host $host;
            proxy_set_header   X-real-ip $remote_addr;
            proxy_set_header    X-Forwarded-For $proxy_add_x_forwarded_for;
            add_header Access-Control-Allow-Origin *;
            add_header Access-Control-Allow-Methods 'GET, POST,PUT, DELETE, OPTIONS';
            add_header Access-Control-Allow-Headers 'DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization';
            if ($request_method = 'OPTIONS') {
                return 204;
            }
        }

    }
	
	
}
```

### 6 服务器端口开放

```bash
sudo firewall-cmd --add-port={22,80,443,3306,6379,5672,15672}/tcp --zone=public --permanent

sudo firewall-cmd --reload
```



### 7 初始化数据库脚本

脚本路径:  

```bash
./doc/sql/stock_alert_create.sql

./doc/sql/stock_source_init_data.sql
```



### 8 项目配置

需要修改的地方:  

```
数据库

邮箱

rabbitMQ

redis

wxPusher

微信小程序wechat(选填，如果需要支持微信小程序登录可以填写)
```



### 9 运行项目  

将 web 以及 schedule tar 包放到一个目录下  

解压，执行启动脚本

```
mkdir /opt/springboot

cd /opt/springboot

tar -zxvf stock-alert-1.0.0.tar.gz 

tar -zxvf stock-alert-schedule-1.0.0.tar.gz

./dos2unix.sh 

./stock-alert/bin/startup.sh 

./stock-alert-schedule/bin/startup.sh 
```

如果项目是在 windows 系统下编译的，可能需要对脚本进行转码  

安装 dos2unix  

```bash
sudo yum install -y dos2unix 
```

dos2unix.sh  

```bash
#!/bin/sh
# stock-alert 项目执行脚本格式转换

dos2unix /opt/springboot/stock-alert/bin/startup.sh
dos2unix /opt/springboot/stock-alert/bin/shutdown.sh
dos2unix /opt/springboot/stock-alert-schedule/bin/startup.sh
dos2unix /opt/springboot/stock-alert-schedule/bin/shutdown.sh
```

添加脚本执行权限  

```bash
sudo chmod +x ./dos2unix.sh
```



### 10 日志  

日志目录(基于项目目录)  

```
../../logs/stock-alert

../../logs/stock-alert-schedule
```











