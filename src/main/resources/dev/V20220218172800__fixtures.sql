INSERT INTO `annotation_schema` (`id`, `version`, `created_at`, `updated_at`, `name`)
VALUES
	(1, 0, NOW(), NOW(), 'Simple Relevance');
	
INSERT INTO `annotation_label` (`id`, `version`, `created_at`, `updated_at`, `annotation_schema`, `display_order`, `accent_color`, `display_label`, `output_label`)
VALUES
	(1, 0, NOW(), NOW(), 1, 1, '#EEFFEE', 'Relevant', '2'),
	(2, 0, NOW(), NOW(), 1, 2, '#EEEEFF', 'Partially Relevant', '1'),
	(3, 0, NOW(), NOW(), 1, 3, '#FFEEEE', 'Not Relevant', '0');
