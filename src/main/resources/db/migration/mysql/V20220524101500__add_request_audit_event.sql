DROP TABLE IF EXISTS `request_audit_event`;

CREATE TABLE `request_audit_event` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`requested_at` datetime NOT NULL,
	`username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
	`ip_address` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
	`service_class` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
	`service_method` text COLLATE utf8_unicode_ci NOT NULL,
	`request_type` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
	`params` text COLLATE utf8_unicode_ci,
	`version` int(11) DEFAULT '0',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
