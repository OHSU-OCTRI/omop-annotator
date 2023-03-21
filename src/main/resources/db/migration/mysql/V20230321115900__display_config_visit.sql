SET @now = now();
SET @version = 0;

-- Reorder the visit columns so source value is to the right of dates and require visit type
DELETE FROM omop_display_configuration where entity = 'VISIT';
INSERT INTO omop_display_configuration(version, created_at, updated_at, entity, field_name, column_display, visible, `filter`, editable, filterable)
VALUES
(@version, @now, @now, 'VISIT', 'id', 'Id', 1, 0, 0, 0),
(@version, @now, @now, 'VISIT', 'visitType', 'Visit Type', 1, 1, 0, 1),
(@version, @now, @now, 'VISIT', 'visitStart', 'Start', 1, 0, 0, 0),
(@version, @now, @now, 'VISIT', 'visitEnd', 'End', 1, 0, 0, 0),
(@version, @now, @now, 'VISIT', 'visitSourceValue', 'Source Value', 1, 0, 0, 1),
(@version, @now, @now, 'VISIT', 'providerName', 'Provider', 1, 0, 0, 1),
(@version, @now, @now, 'VISIT', 'careSiteName', 'Care Site', 1, 0, 0, 1),
(@version, @now, @now, 'VISIT', 'visitSource', 'Visit Source', 0, 0, 1, 1),
(@version, @now, @now, 'VISIT', 'admittingSourceValue', 'Admitting Source Value', 0, 0, 1, 1),
(@version, @now, @now, 'VISIT', 'dischargeToSourceValue', 'Discharge To Source Value', 0, 0, 1, 1);