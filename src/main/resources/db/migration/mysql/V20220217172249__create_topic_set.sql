-- Create table for entity TopicSet
DROP TABLE IF EXISTS `topic_set`;
CREATE TABLE `topic_set` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`name` varchar(255) NOT NULL UNIQUE,
	primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
