-- Create table for entity Pin
DROP TABLE IF EXISTS `pin`;
CREATE TABLE `pin` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`user` bigint(20) NOT NULL,
	`pool_entry` bigint(20) NOT NULL,
	`entity` varchar(255) DEFAULT NULL,
	`entity_id` bigint(20) DEFAULT NULL,
	`visit_id` bigint(20) DEFAULT NULL,
	primary key(`id`),
	CONSTRAINT pin_user_fk FOREIGN KEY (`user`) REFERENCES `user` (`id`),
	CONSTRAINT pin_pool_entry_fk FOREIGN KEY (`pool_entry`) REFERENCES `pool_entry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

