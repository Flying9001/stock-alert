-- 一定要代码更新之后再执行
-- 删除预警消息旧有字段(待代码更新后执行)
ALTER TABLE `alert_message` DROP COLUMN `phone_send`;
ALTER TABLE `alert_message` DROP COLUMN `email_send`;