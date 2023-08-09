-- 一定要更新代码之后再执行
-- 清除预警消息推送方式等相关字段
ALTER TABLE `alert_message` DROP COLUMN `push_type`;
ALTER TABLE `alert_message` DROP COLUMN `push_result`;
ALTER TABLE `alert_message` DROP COLUMN `retry_time`;
