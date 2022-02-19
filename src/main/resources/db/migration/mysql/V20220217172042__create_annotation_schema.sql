-- Create table for entity AnnotationSchema
DROP TABLE IF EXISTS `annotation_schema`;
CREATE TABLE `annotation_schema` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`name` varchar(255) NOT NULL,
	primary key(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
