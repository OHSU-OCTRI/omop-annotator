-- Insert all columns that can be configured through the admin interface
SET @now = now();
SET @version = 0;

INSERT INTO `omop_display_configuration` (`version`, `created_at`, `updated_at`, `entity_name`, `field_name`, `column_display`, `visible`, `filter`) 
VALUES
(@version, @now, @now, 'Condition', 'id', 'Id', 1, 0),
(@version, @now, @now, 'Condition', 'condition', 'Condition', 1, 1),
(@version, @now, @now, 'Condition', 'conditionType', 'Type', 1, 1),
(@version, @now, @now, 'Condition', 'conditionStart', 'Start', 1, 0),
(@version, @now, @now, 'Condition', 'conditionEnd', 'End', 1, 0),
(@version, @now, @now, 'Observation', 'id', 'Id', 1, 0),
(@version, @now, @now, 'Observation', 'observation', 'Observation', 0, 0),
(@version, @now, @now, 'Observation', 'valueAsConcept', 'Value Concept', 1, 0),
(@version, @now, @now, 'Observation', 'sourceValue', 'Source Value', 0, 0),
(@version, @now, @now, 'Observation', 'date', 'Date/Time', 1, 0),
(@version, @now, @now, 'Observation', 'type', 'Type', 1, 0),
(@version, @now, @now, 'Observation', 'value', 'Value', 1, 0),
(@version, @now, @now, 'Procedure', 'id', 'Id', 1, 0),
(@version, @now, @now, 'Procedure', 'procedure', 'Procedure', 1, 1),
(@version, @now, @now, 'Procedure', 'procedureSource', 'Procedure Source', 0, 0),
(@version, @now, @now, 'Procedure', 'date', 'Date/Time', 1, 0),
(@version, @now, @now, 'Procedure', 'procedureType', 'Type', 1, 1),
(@version, @now, @now, 'Procedure', 'quantity', 'Quantity', 0, 0),
(@version, @now, @now, 'Note', 'id', 'Id', 1, 0),
(@version, @now, @now, 'Note', 'date', 'Date/Time', 1, 0),
(@version, @now, @now, 'Note', 'type', 'Type', 1, 0),
(@version, @now, @now, 'Note', 'title', 'Title', 1, 1),
(@version, @now, @now, 'Note', 'text', 'Text', 1, 0);