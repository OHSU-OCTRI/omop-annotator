-- Add configuration for newly mapped fields
SET @now = now();
SET @version = 0;

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Visit', 'id', 'Id', 1, 0, 0, 0),
(@version, @now, @now, 'Visit', 'visitType', 'Visit Type', 1, 1, 1, 1),
(@version, @now, @now, 'Visit', 'visitSourceValue', 'Source Value', 1, 0, 1, 1),
(@version, @now, @now, 'Visit', 'visitStart', 'Start', 1, 0, 0, 0),
(@version, @now, @now, 'Visit', 'visitEnd', 'End', 1, 0, 0, 0),
(@version, @now, @now, 'Visit', 'providerName', 'Provider', 1, 0, 1, 1),
(@version, @now, @now, 'Visit', 'careSiteName', 'Care Site', 1, 0, 1, 1);

-- Ensure any fields that are indexed for search are visible and uneditable and have a filter when appropriate
update omop_display_configuration set visible = 1, editable = 0 where entity_name = 'Visit' and field_name = 'providerName';
update omop_display_configuration set visible = 1, editable = 0 where entity_name = 'Visit' and field_name = 'careSiteName';
update omop_display_configuration set visible = 1, editable = 0 where entity_name = 'Visit' and field_name = 'visitSourceValue';
update omop_display_configuration set visible = 1, `filter` = 1, editable = 0 where entity_name = 'Condition' and field_name = 'condition';
update omop_display_configuration set visible = 1, `filter` = 1, editable = 0 where entity_name = 'Observation' and field_name = 'observation';
update omop_display_configuration set visible = 1, `filter` = 1, editable = 0 where entity_name = 'Procedure' and field_name = 'procedure';
update omop_display_configuration set visible = 1, `filter` = 1, editable = 0 where entity_name = 'Measurement' and field_name = 'measurement';
update omop_display_configuration set visible = 1, editable = 0 where entity_name = 'Note' and field_name = 'text';
update omop_display_configuration set visible = 1, `filter` = 1, editable = 0 where entity_name = 'Drug' and field_name = 'drug';

-- Additional fields not previously mapped in query
INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Visit', 'visitSource', 'Visit Source', 0, 0, 1, 1),
(@version, @now, @now, 'Visit', 'admittingSourceValue', 'Admitting Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Visit', 'dischargeToSourceValue', 'Discharge To Source Value', 0, 0, 1, 1);