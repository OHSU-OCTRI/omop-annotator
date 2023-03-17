-- Create table for entity SearchIndexJob
DROP TABLE IF EXISTS `search_index_job`;
CREATE TABLE `search_index_job` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`version` int(11) NOT NULL,
	`created_at` datetime NOT NULL,
	`updated_at` datetime NOT NULL,
	`user` bigint(20) NOT NULL,
	`started_at` datetime DEFAULT NULL,
	`ended_at` datetime DEFAULT NULL,
	`description` TEXT,
	`status` varchar(255) DEFAULT NULL,
	`patient_id_count` int(11) DEFAULT NULL,
	primary key(`id`),
	CONSTRAINT search_index_job_user_fk FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

