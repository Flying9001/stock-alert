/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/3/26 16:27:00                           */
/*==============================================================*/


DROP TABLE IF EXISTS ALERT_MESSAGE;

DROP TABLE IF EXISTS STOCK_SOURCE;

DROP TABLE IF EXISTS USER_INFO;

DROP TABLE IF EXISTS USER_STOCK;

/*==============================================================*/
/* Table: ALERT_MESSAGE                                         */
/*==============================================================*/
CREATE TABLE ALERT_MESSAGE
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   USER_ID              BIGINT COMMENT '用户信息',
   PHONE_SEND           TINYINT COMMENT '手机发送,1-发送成功,2-发送失败,3-未发送',
   EMAIL_SEND           TINYINT COMMENT '邮箱发送,1-发送成功,2-发送失败,3-未发送',
   STOCK_ID             BIGINT COMMENT '股票 id',
   CONTENT              VARCHAR(256) COMMENT '消息内容',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8;

ALTER TABLE ALERT_MESSAGE COMMENT '预警消息';

/*==============================================================*/
/* Table: STOCK_SOURCE                                          */
/*==============================================================*/
CREATE TABLE STOCK_SOURCE
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   MARKET_TYPE          TINYINT COMMENT '市场类型,1-上海,2-深圳,3-香港,4-美国',
   STOCK_CODE           VARCHAR(10) COMMENT '股票代码',
   COMPANY_NAME         VARCHAR(64) COMMENT '公司名称',
   TODAY_START_PRICE    DECIMAL(10,2) COMMENT '今日开盘价',
   YESTERDAY_END_PRICE  DECIMAL(10,2) COMMENT '昨日收盘价',
   CURRENT_PRICE        DECIMAL(10,2) COMMENT '当前股价',
   INCREASE             DECIMAL(10,2) COMMENT '涨跌额',
   INCREASE_PER         DECIMAL(8,2) COMMENT '涨跌百分比',
   TODAY_MAX_PRICE      DECIMAL(10,2) COMMENT '今日最高价',
   TODAY_MIN_PRICE      DECIMAL(10,2) COMMENT '今日最低价',
   TRADE_NUMBER         INT COMMENT '交易量',
   TRADE_AMOUNT         DECIMAL(12,2) COMMENT '交易金额',
   DATE                 VARCHAR(20) COMMENT '日期',
   TIME                 VARCHAR(20) COMMENT '时间',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8;

ALTER TABLE STOCK_SOURCE COMMENT '股票源';

/*==============================================================*/
/* Table: USER_INFO                                             */
/*==============================================================*/
CREATE TABLE USER_INFO
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   ACCOUNT              VARCHAR(20) COMMENT '账户',
   NICK_NAME            VARCHAR(20) COMMENT '昵称',
   PASSCODE             VARCHAR(128) COMMENT '密码',
   MOBILE_PHONE         VARCHAR(15) COMMENT '手机号',
   EMAIL                VARCHAR(64) COMMENT '邮箱',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8;

ALTER TABLE USER_INFO COMMENT '用户信息';

/*==============================================================*/
/* Table: USER_STOCK                                            */
/*==============================================================*/
CREATE TABLE USER_STOCK
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   STOCK_ID             BIGINT COMMENT '股票 id',
   USER_ID              BIGINT COMMENT '用户 id',
   MAX_PRICE            DECIMAL(10,2) COMMENT '股价预警最高价',
   MIN_PRICE            DECIMAL(10,2) COMMENT '股价预警最低价',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8;

ALTER TABLE USER_STOCK COMMENT '用户股票';

