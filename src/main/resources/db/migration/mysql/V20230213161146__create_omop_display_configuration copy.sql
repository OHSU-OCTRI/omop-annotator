-- Create table for entity OmopDisplayConfiguration
DROP TABLE IF EXISTS `omop_display_configuration`;
CREATE TABLE `omop_display_configuration` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`entity_name` varchar(255) DEFAULT NULL,
	`field_name` varchar(255) DEFAULT NULL,
	`column_display` varchar(255) DEFAULT NULL,
	`visible` bit default 1,
	`filter` bit default 0,
	primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

