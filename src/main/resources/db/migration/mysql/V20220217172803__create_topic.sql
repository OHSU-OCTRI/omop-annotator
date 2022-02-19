-- Create table for entity Topic
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`topic_set` bigint(20) NOT NULL,
	`topic_number` int(11) NOT NULL,
	`narrative` TEXT,
	primary key(`id`),
	CONSTRAINT topic_topic_set_fk FOREIGN KEY (`topic_set`) REFERENCES `topic_set` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
