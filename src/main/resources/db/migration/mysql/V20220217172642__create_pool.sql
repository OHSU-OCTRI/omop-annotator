-- Create table for entity Pool
DROP TABLE IF EXISTS `pool`;
CREATE TABLE `pool` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`topic_set` bigint(20) NOT NULL,
	`annotation_schema` bigint(20) NOT NULL,
	`name` varchar(255) NOT NULL,
	`comments` TEXT,
	primary key(`id`),
	CONSTRAINT pool_topic_set_fk FOREIGN KEY (`topic_set`) REFERENCES `topic_set` (`id`),
	CONSTRAINT pool_annotation_schema_fk FOREIGN KEY (`annotation_schema`) REFERENCES `annotation_schema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
