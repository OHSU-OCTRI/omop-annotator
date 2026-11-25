package org.octri.omop_annotator.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.octri.omop_annotator.SpringConfiguredJsonMapper;
import org.octri.omop_annotator.domain.app.OmopEntity;
import org.octri.omop_annotator.domain.app.Pin;
import org.springframework.beans.factory.annotation.Autowired;

import tools.jackson.databind.json.JsonMapper;

/**
 * Unit tests for {@link PinExport}. These lock in the JSON format of the pins column in judgment exports.
 */
@SpringConfiguredJsonMapper
public class PinExportTest {

	@Autowired
	private JsonMapper mapper;

	private static Pin pin(OmopEntity entity, Long entityId, String comment) {
		var pin = new Pin();
		pin.setEntity(entity);
		pin.setEntityId(entityId);
		pin.setVisitId(99L);
		pin.setComment(comment);
		return pin;
	}

	@Test
	void testFrom() {
		var export = PinExport.from(pin(OmopEntity.NOTE, 12345L, "Relevant note"));
		assertEquals(new PinExport("NOTE", "12345", "Relevant note"), export);
	}

	@Test
	void testFromNullCommentBecomesEmpty() {
		var export = PinExport.from(pin(OmopEntity.CONDITION, 7L, null));
		assertEquals("", export.comment());
	}

	@Test
	void testSerializedFormat() {
		var pins = List.of(pin(OmopEntity.NOTE, 12345L, "Relevant note"), pin(OmopEntity.DRUG, 7L, null));
		var json = mapper.writeValueAsString(pins.stream().map(PinExport::from).toList());
		assertEquals("[{\"entity\":\"NOTE\",\"entityId\":\"12345\",\"comment\":\"Relevant note\"},"
				+ "{\"entity\":\"DRUG\",\"entityId\":\"7\",\"comment\":\"\"}]", json);
	}

}
