-- Create table for entity Judgment
DROP TABLE IF EXISTS `judgment`;
CREATE TABLE `judgment` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`user` bigint(20) NOT NULL,
	`pool_entry` bigint(20) NOT NULL,
	`annotation_label` bigint(20) DEFAULT NULL,
	primary key(`id`),
	CONSTRAINT judgment_user_fk FOREIGN KEY (`user`) REFERENCES `user` (`id`),
	CONSTRAINT judgment_pool_entry_fk FOREIGN KEY (`pool_entry`) REFERENCES `pool_entry` (`id`),
	CONSTRAINT judgment_annotation_label_fk FOREIGN KEY (`annotation_label`) REFERENCES `annotation_label` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

