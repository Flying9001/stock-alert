## 股票预警助手(Stock alert helper)  


​    
​    
### 项目简介  

股票预警助手(Stock alert helper)致力于为个人投资者提供实时股票监控，当股价达到合理位置自动预警。  

### 项目架构图  

![StockAlert架构图](https://cdn.jsdelivr.net/gh/Flying9001/images/pic/20210707112248.jpg)

​     

### 项目运行环境  

- JDK 1.8+  
- MySQL 5.7+  
- Maven 3+  
- RabbitMQ 3.7+  
- Redis 5.0+  



​    

### 项目技术栈  

- Spring boot 2.4.2  
- Mybatis plus 3.4.2  
- Swagger 3.0 / OpenApi 3.0  
- RabbitMQ  
- Redis  



​    

### 运行  

#### 可执行文件(Linux/Unix)  

下载项目打包文件  

解压  

修改数据库信息  

修改邮箱信息  

执行启动脚本  

停止项目  



#### 源码运行(Windows/Linux/Unix)  

克隆项目  

修改配置信息(数据库、邮箱等)  

maven 打包  

启动项目  

​    

### 文档  

项目文档目录  

```
./doc
```

项目数据库脚本  

```
./doc/sql/stock_alert_create.sql
./doc/sql/stock_source_init_data.sql
```

[需求分析](./doc/requirement_analysis.md)  

[接口文档使用说明](./doc/api_docs.md "./doc/api_docs.md")  

[新浪股票接口使用说明](./doc/stock_api_sina.md "./doc/stock_api_sina.md")  

[腾讯股票接口使用说明](./doc/stock_api_tencent.md "./doc/stock_api_tencent.md")  

[网易股票接口使用说明](./doc/stock_api_netease.md "./doc/stock_api_netease.md")  







