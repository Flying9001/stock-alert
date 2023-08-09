drop table if exists message_push_result;

/*==============================================================*/
/* Table: message_push_result                                   */
/*==============================================================*/
create table message_push_result
(
   id                   bigint unsigned not null comment 'id',
   message_id           bigint unsigned comment '消息id',
   push_type            tinyint comment '推送类型,1-短信,2-邮箱,3-pushplus',
   push_result          tinyint comment '推送结果,0-失败,1-成功,2-未推送',
   push_record          varchar(32) comment '推送记录值,部分推送方式可根据记录值查询实际推送结果',
   retry_time           tinyint default 0 comment '消息发送失败重试次数',
   create_time          datetime default current_timestamp comment '创建时间',
   update_time          datetime default current_timestamp on update current_timestamp comment '更新时间',
   primary key (id)
)
engine = innodb default
charset = utf8mb4;

alter table message_push_result comment '消息推送结果';


-- 预警消息添加实际消息推送次数字段
ALTER TABLE `alert_message` ADD COLUMN `push_count` TINYINT COMMENT '实际消息推送次数' NOT NULL DEFAULT 0 AFTER `user_id`; 

-- 预警消息添加总共消息所需推送次数字段
ALTER TABLE `alert_message` ADD COLUMN `push_total` TINYINT COMMENT '总共消息所需推送次数' NOT NULL DEFAULT 0 AFTER `push_count`; 

-- 预警消息旧推送数据兼容
UPDATE `alert_message` SET `push_total` = 1;
UPDATE `alert_message` SET `push_count` = 1 WHERE `push_result` = 1;
UPDATE `alert_message` SET `push_count` = 0 WHERE `push_result` != 1;
