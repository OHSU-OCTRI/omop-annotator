package org.octri.omop_annotator.view;

import org.octri.omop_annotator.domain.app.Pin;

/**
 * Representation of a {@link Pin} embedded as JSON in judgment exports. See {@link ExportedJudgmentRow}.
 *
 * @param entity
 *            - name of the pinned OMOP entity type
 * @param entityId
 *            - id of the pinned entity, as a string
 * @param comment
 *            - the pin's comment, or an empty string if there is none
 */
public record PinExport(String entity, String entityId, String comment) {

	/**
	 * Create the export representation of a {@link Pin}
	 *
	 * @param pin
	 * @return the export representation
	 */
	public static PinExport from(Pin pin) {
		return new PinExport(pin.getEntity().name(),
				Long.toString(pin.getEntityId()),
				pin.getComment() == null ? "" : pin.getComment());
	}
}
