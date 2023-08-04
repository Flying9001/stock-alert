drop table if exists user_push_type;

/*==============================================================*/
/* Table: user_push_type                                        */
/*==============================================================*/
create table user_push_type
(
   id                   bigint not null comment 'id',
   user_id              bigint comment '用户id',
   push_type            tinyint comment '推送方式,1-短信;2-邮件;3-pushplus',
   receive_address      varchar(128) comment '通知推送接收地址',
   enable               tinyint comment '是否启用,0-未启用,1-启用',
   create_time          datetime default current_timestamp comment '创建时间',
   update_time          datetime default current_timestamp on update current_timestamp comment '更新时间',
   primary key (id)
)
engine = innodb default
charset = utf8mb4;

alter table user_push_type comment '用户消息推送方式';

-- 同步推送数据
INSERT INTO `user_push_type`(`id`, `user_id`, `push_type`, `receive_address`, `enable`)
SELECT  CONCAT(date_format(NOW(), '%d%f'), LPAD(FLOOR(RAND()*10000000),7,0)) as idNumber,u.`id`, 1, u.`mobile_phone`, 0 FROM `user_info` AS u WHERE u.mobile_phone IS NOT NULL;

INSERT INTO `user_push_type`(`id`, `user_id`, `push_type`, `receive_address`, `enable`)
SELECT CONCAT(date_format(NOW(), '%d%f'), LPAD(FLOOR(RAND()*10000000),7,0)) as idNumber ,u.`id`, 2, u.`email`, 1 FROM `user_info` AS u WHERE u.email IS NOT NULL;


-- 预警消息添加字段
ALTER TABLE `alert_message` ADD COLUMN `push_type` TINYINT COMMENT '推送类型,1-短信,2-邮箱,3-pushplus' AFTER `user_id`;
ALTER TABLE `alert_message` ADD COLUMN `push_result` TINYINT COMMENT '推送结果,0-失败,1-成功,2-未推送' AFTER `push_type`;

-- 预警消息更新推送类型于结果
UPDATE `alert_message` SET `push_type` = 2;
UPDATE `alert_message` SET `push_result` = 1 WHERE `email_send` = 1;
UPDATE `alert_message` SET `push_result` = 2 WHERE `email_send` != 1;
