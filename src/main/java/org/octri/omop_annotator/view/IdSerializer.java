package org.octri.omop_annotator.view;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * JsonSerializer that serializes an Identified entity as just its id.
 */
public class IdSerializer extends JsonSerializer<Identified> {

	@Override
	public void serialize(Identified value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		if (value != null) {
			gen.writeNumber(value.getId());
		} else {
			gen.writeNull();
		}
	}

}
