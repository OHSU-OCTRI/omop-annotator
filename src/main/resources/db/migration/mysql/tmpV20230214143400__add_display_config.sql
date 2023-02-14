-- Insert all columns that can be configured through the admin interface
SET @now = now();
SET @version = 0;

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`) 
VALUES
(@version, @now, @now, 'Condition', 'id', 'Id', 1, 0),
(@version, @now, @now, 'Condition', 'condition', 'Condition', 1, 1),
(@version, @now, @now, 'Condition', 'conditionType', 'Type', 1, 1),
(@version, @now, @now, 'Condition', 'conditionStart', 'Start', 1, 0),
(@version, @now, @now, 'Condition', 'conditionEnd', 'End', 1, 0);