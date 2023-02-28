-- Add configuration for newly mapped fields
SET @now = now();
SET @version = 0;

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Condition', 'conditionSource', 'Condition Source', 0, 0, 1, 1),
(@version, @now, @now, 'Condition', 'conditionSourceValue', 'Condition Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Condition', 'conditionStatus', 'Condition Status', 0, 0, 1, 1),
(@version, @now, @now, 'Condition', 'conditionStatusSourceValue', 'Condition Status Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Condition', 'stopReason', 'Stop Reason', 0, 0, 1, 1);

-- Make field names more consistent with others
UPDATE `omop_display_configuration` set field_name = 'observationSourceValue' where field_name = 'sourceValue' and entity_name = 'Observation';
UPDATE `omop_display_configuration` set field_name = 'observationType' where field_name = 'type' and entity_name = 'Observation';
UPDATE `omop_display_configuration` set field_name = 'valueAsString' where field_name = 'value' and entity_name = 'Observation';

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Observation', 'observationSource', 'Source', 0, 0, 1, 1),
(@version, @now, @now, 'Observation', 'valueAsNumber', 'Value (Number)', 0, 0, 1, 1),
(@version, @now, @now, 'Observation', 'observationQualifier', 'Qualifier', 0, 0, 1, 1),
(@version, @now, @now, 'Observation', 'qualifierSourceValue', 'Qualifier Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Observation', 'observationUnit', 'Unit', 0, 0, 1, 1),
(@version, @now, @now, 'Observation', 'unitSourceValue', 'Unit Source Value', 0, 0, 1, 1);

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Procedure', 'procedureModifier', 'Modifier', 0, 0, 1, 1),
(@version, @now, @now, 'Procedure', 'procedureSourceValue', 'Procedure Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Procedure', 'modifierSourceValue', 'Modifier Source Value', 0, 0, 1, 1);

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Measurement', 'measurementOperator', 'Operator', 0, 0, 1, 1),
(@version, @now, @now, 'Measurement', 'rangeLow', 'Range Low', 0, 0, 1, 1),
(@version, @now, @now, 'Measurement', 'rangeHigh', 'Range High', 0, 0, 1, 1),
(@version, @now, @now, 'Measurement', 'measurementSource', 'Source', 0, 0, 1, 1),
(@version, @now, @now, 'Measurement', 'measurementSourceValue', 'Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Measurement', 'unitSourceValue', 'Unit Source Value', 0, 0, 1, 1);

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Note', 'noteSourceValue', 'Note Source Value', 0, 0, 1, 1);

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`, `editable`, `filterable`) 
VALUES
(@version, @now, @now, 'Drug', 'refills', 'Refills', 0, 0, 1, 1),
(@version, @now, @now, 'Drug', 'drugSource', 'Drug Source', 0, 0, 1, 1),
(@version, @now, @now, 'Drug', 'drugSourceValue', 'Drug Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Drug', 'routeSourceValue', 'Route Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'Drug', 'doseUnitSourceValue', 'Dose UnitSource Value', 0, 0, 1, 1);
