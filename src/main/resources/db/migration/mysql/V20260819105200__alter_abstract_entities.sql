-- Alter entity tables to add `updated_by` column from common-lib.
ALTER TABLE `annotation_label`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `annotation_schema`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `judgment`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `omop_display_configuration`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `pin`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `pool`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `pool_entry`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `search_index_job`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `topic`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;

ALTER TABLE `topic_set`
  ADD `updated_by` varchar(320) DEFAULT NULL AFTER `updated_at`;