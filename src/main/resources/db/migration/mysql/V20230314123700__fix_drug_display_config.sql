-- Set all fields on Drug except the grouped field to not be filterable
update omop_display_configuration set `filterable` = 0 where entity_name = 'Drug' and field_name <> 'drug';