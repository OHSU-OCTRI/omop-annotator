/* OA-96: Add field requirements */

ALTER TABLE annotation_label MODIFY `display_order` int(11) NOT NULL;
ALTER TABLE annotation_label MODIFY `accent_color` varchar(255) NOT NULL;
ALTER TABLE annotation_label MODIFY `output_label` varchar(255) NOT NULL;

ALTER TABLE topic MODIFY `narrative` TEXT NOT NULL;

ALTER TABLE pool_entry MODIFY `document_id` int(11) NOT NULL;