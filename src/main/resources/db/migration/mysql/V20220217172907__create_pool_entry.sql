-- Create table for entity PoolEntry
DROP TABLE IF EXISTS `pool_entry`;
CREATE TABLE `pool_entry` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`pool` bigint(20) NOT NULL,
	`topic` bigint(20) NOT NULL,
	`sort_order` int(11) DEFAULT NULL,
	`document_id` int(11) DEFAULT NULL,
	`frequency` int(11) DEFAULT NULL,
	primary key(`id`),
	CONSTRAINT pool_entry_pool_fk FOREIGN KEY (`pool`) REFERENCES `pool` (`id`),
	CONSTRAINT pool_entry_topic_fk FOREIGN KEY (`topic`) REFERENCES `topic` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
