/* OA-80: Enforce Uniqueness */

ALTER TABLE `pool` ADD CONSTRAINT uq_pool_topic_set_name UNIQUE KEY(`topic_set`, `name`);

ALTER TABLE `topic` ADD CONSTRAINT uq_topic_topic_set_topic_number UNIQUE KEY(`topic_set`, `topic_number`);

ALTER TABLE `annotation_schema` ADD CONSTRAINT uq_annotation_schema_name UNIQUE KEY(`name`);

ALTER TABLE `annotation_label` ADD CONSTRAINT uq_annotation_label_annotation_schema_display_order UNIQUE KEY(`annotation_schema`, `display_order`);
ALTER TABLE `annotation_label` ADD CONSTRAINT uq_annotation_label_annotation_schema_accent_color UNIQUE KEY(`annotation_schema`, `accent_color`);
ALTER TABLE `annotation_label` ADD CONSTRAINT uq_annotation_label_annotation_schema_display_label UNIQUE KEY(`annotation_schema`, `display_label`);
ALTER TABLE `annotation_label` ADD CONSTRAINT uq_annotation_label_annotation_schema_output_label UNIQUE KEY(`annotation_schema`, `output_label`);

ALTER TABLE `judgment` ADD CONSTRAINT uq_judgment_pool_entry_user UNIQUE KEY(`pool_entry`, `user`);

-- Assign the correct column size to accent_color
ALTER TABLE annotation_label MODIFY `accent_color` varchar(7) NOT NULL;

