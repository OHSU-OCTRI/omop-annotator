-- Set the entity name to upper case for use with new enumeration
UPDATE omop_display_configuration SET entity_name = UPPER(entity_name);

-- Change column name for consistency with Pin
ALTER TABLE omop_display_configuration CHANGE entity_name entity varchar(255);