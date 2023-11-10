## 股票预警助手(Stock alert helper)  


​    
​    
### 项目简介  

股票预警助手(Stock alert helper)致力于为个人投资者提供实时股票监控，当股价达到合理位置自动预警。  

### 项目架构图  

![StockAlert架构图](https://cdn.jsdelivr.net/gh/Flying9001/images/pic/20210707112248.jpg)

​     

### 项目模块划分  

| 模块名称 | 说明                                                         | 依赖关系                 | 是否可执行 |
| -------- | ------------------------------------------------------------ | ------------------------ | ---------- |
| common   | 公共模块，主要包含工具类、常量、配置类、接口返回结果封装、异常定义等 |                          | 否         |
| model    | 数据模型模块，包含各类POJO类，包括数据库模型(实体类entity)、请求参数对象(param)、页面展示结果对象(vo) |                          | 否         |
| dao      | 数据持久层模块，包括 DAO 接口以及 Mybatis mapper 文件        | model                    | 否         |
| service  | 业务层模块，包括业务接口、业务实现类以及一些和业务相关的工具组件 | model,dao,common         | 否         |
| web      | 控制层模块，包括web接口以及拦截器功能                        | model,dao,service,common | 是         |
| schedule | 任务调度模块，包含股价定时刷新与实时监控功能                 | model,dao,service,common | 是         |

​    

### 消息推送模块架构设计

![multi-type-message-push-v1.0](https://cdn.jsdelivr.net/gh/Flying9001/images/pic2023/multi-type-message-push-v1.0.jpg)



### 项目运行环境  

- JDK 1.8+  
- MySQL 5.7+  
- Maven 3+  
- RabbitMQ 3.7+  
- Redis 5.0+  



### 项目技术栈  

- Spring boot 2.4.2  
- Mybatis plus 3.4.2  
- Swagger 3.0 / OpenApi 3.0  
- RabbitMQ  
- Redis  

## 部署

[Stock Alert 部署文档](./doc/stock_alert_deploy_doc.md) 

   

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

**扫码体验微信小程序(用户端)**

<img src="https://cdn.jsdelivr.net/gh/Flying9001/images/pic2023/stock_alert_mini_program_qrcode_30.jpg" alt="stock_alert_mini_program_qrcode_30" style="zoom:30%;" />





