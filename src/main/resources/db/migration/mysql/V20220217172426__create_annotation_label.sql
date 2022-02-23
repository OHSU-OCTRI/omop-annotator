-- Create table for entity AnnotationLabel
DROP TABLE IF EXISTS `annotation_label`;
CREATE TABLE `annotation_label` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`annotation_schema` bigint(20) NOT NULL,
	`display_order` int(11) DEFAULT NULL,
	`accent_color` varchar(255) DEFAULT NULL,
	`display_label` varchar(255) NOT NULL,
	`output_label` varchar(255) DEFAULT NULL,
	primary key(`id`),
	CONSTRAINT annotation_label_annotation_schema_fk FOREIGN KEY (`annotation_schema`) REFERENCES `annotation_schema` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
