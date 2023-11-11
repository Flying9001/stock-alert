/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2023/8/9 11:00:38                            */
/*==============================================================*/


DROP TABLE IF EXISTS ALERT_MESSAGE;

DROP TABLE IF EXISTS STOCK_SOURCE;

DROP TABLE IF EXISTS USER_INFO;

DROP TABLE IF EXISTS USER_STOCK;

DROP TABLE IF EXISTS ADMIN_USER;

DROP TABLE IF EXISTS MESSAGE_PUSH_RESULT;

DROP TABLE IF EXISTS STOCK_GROUP_STOCK;

DROP TABLE IF EXISTS USER_OAUTH;

DROP TABLE IF EXISTS USER_PUSH_TYPE;

DROP TABLE IF EXISTS USER_STOCK_GROUP;

/*==============================================================*/
/* Table: ALERT_MESSAGE                                         */
/*==============================================================*/
CREATE TABLE ALERT_MESSAGE
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   USER_ID              BIGINT COMMENT '用户信息',
   PUSH_COUNT           TINYINT NOT NULL DEFAULT 0 COMMENT '实际消息推送次数',
   PUSH_TOTAL           TINYINT NOT NULL DEFAULT 0 COMMENT '总共消息所需推送次数',
   ALERT_TYPE           TINYINT COMMENT '提醒类型,1-股价提醒;2-单日涨跌幅提醒',
   STOCK_ID             BIGINT COMMENT '股票 id',
   TITLE                VARCHAR(64) COMMENT '消息标题',
   CONTENT              VARCHAR(256) COMMENT '消息内容',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

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
   TRADE_NUMBER         BIGINT COMMENT '交易量',
   TRADE_AMOUNT         DECIMAL(20,2) COMMENT '交易金额',
   DATE                 VARCHAR(20) COMMENT '日期',
   TIME                 VARCHAR(20) COMMENT '时间',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE STOCK_SOURCE COMMENT '股票源';

/*==============================================================*/
/* Table: USER_INFO                                             */
/*==============================================================*/
CREATE TABLE USER_INFO
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   ACCOUNT              VARCHAR(64) COMMENT '账户',
   NICK_NAME            VARCHAR(20) COMMENT '昵称',
   PASSCODE             VARCHAR(128) COMMENT '密码',
   MOBILE_PHONE         VARCHAR(15) COMMENT '手机号',
   EMAIL                VARCHAR(64) COMMENT '邮箱',
   HEAD_URL             VARCHAR(128) COMMENT '用户头像地址',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

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
   MAX_INCREASE_PER     INT DEFAULT -1 COMMENT '单日股价最大涨幅(值为正,最少为1,即1%),默认-1(A股标准涨停)',
   MAX_DECREASE_PER     INT DEFAULT -1 COMMENT '单日股价最大跌幅(值为正,最少为1,即1%),默认-1(A股标准跌停)',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE USER_STOCK COMMENT '用户股票';

/*==============================================================*/
/* Table: ADMIN_USER                                            */
/*==============================================================*/
CREATE TABLE ADMIN_USER
(
   ID                   BIGINT UNSIGNED NOT NULL COMMENT 'id',
   ACCOUNT              VARCHAR(32) COMMENT '账号',
   PASSCODE             VARCHAR(128) COMMENT '密码',
   ENABLE               TINYINT COMMENT '是否启用,0-禁用,1-启用',
   NICK_NAME            VARCHAR(32) COMMENT '昵称',
   HEAD_URL             VARCHAR(128) COMMENT '头像链接',
   MOBILE_PHONE         VARCHAR(15) COMMENT '手机号',
   EMAIL                VARCHAR(64) COMMENT '邮箱',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE ADMIN_USER COMMENT '管理员用户';

/*==============================================================*/
/* Table: MESSAGE_PUSH_RESULT                                   */
/*==============================================================*/
CREATE TABLE MESSAGE_PUSH_RESULT
(
   ID                   BIGINT UNSIGNED NOT NULL COMMENT 'id',
   MESSAGE_ID           BIGINT UNSIGNED COMMENT '消息id',
   PUSH_TYPE            TINYINT COMMENT '推送类型,1-短信,2-邮箱,3-pushplus',
   PUSH_RESULT          TINYINT COMMENT '推送结果,0-失败,1-成功,2-未推送',
   PUSH_RECORD          VARCHAR(32) COMMENT '推送记录值,部分推送方式可根据记录值查询实际推送结果',
   RETRY_TIME           TINYINT DEFAULT 0 COMMENT '消息发送失败重试次数',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE MESSAGE_PUSH_RESULT COMMENT '消息推送结果';

/*==============================================================*/
/* Table: STOCK_GROUP_STOCK                                     */
/*==============================================================*/
CREATE TABLE STOCK_GROUP_STOCK
(
   ID                   BIGINT NOT NULL COMMENT 'id,主键',
   STOCK_GROUP_ID       BIGINT COMMENT '分组 id',
   STOCK_ID             BIGINT COMMENT '股票 id',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE STOCK_GROUP_STOCK COMMENT '用户股票分组关联股票';

/*==============================================================*/
/* Table: USER_OAUTH                                            */
/*==============================================================*/
CREATE TABLE USER_OAUTH
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   USER_ID              BIGINT COMMENT '用户id',
   ACCESS_ID            VARCHAR(64) COMMENT '第三方接入id',
   LOGIN_TYPE           VARCHAR(10) COMMENT '第三方登录类型',
   ENABLE               TINYINT COMMENT '是否启用,0-不启用,1-启用',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE USER_OAUTH COMMENT '用户第三方登录信息';

/*==============================================================*/
/* Table: USER_PUSH_TYPE                                        */
/*==============================================================*/
CREATE TABLE USER_PUSH_TYPE
(
   ID                   BIGINT NOT NULL COMMENT 'id',
   USER_ID              BIGINT COMMENT '用户id',
   PUSH_TYPE            TINYINT COMMENT '推送方式,1-短信;2-邮件;3-pushplus',
   RECEIVE_ADDRESS      VARCHAR(128) COMMENT '通知推送接收地址',
   ENABLE               TINYINT COMMENT '是否启用,0-未启用,1-启用',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 ;

ALTER TABLE USER_PUSH_TYPE COMMENT '用户消息推送方式';

/*==============================================================*/
/* Table: USER_STOCK_GROUP                                      */
/*==============================================================*/
CREATE TABLE USER_STOCK_GROUP
(
   ID                   BIGINT NOT NULL COMMENT 'id,主键',
   GROUP_NAME           VARCHAR(32) COMMENT '分组名称',
   USER_ID              BIGINT COMMENT '用户 id',
   CREATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UPDATE_TIME          DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (ID)
)
ENGINE = INNODB DEFAULT
CHARSET = UTF8MB4 COLLATE=utf8mb4_general_ci;

ALTER TABLE USER_STOCK_GROUP COMMENT '用户股票分组';

