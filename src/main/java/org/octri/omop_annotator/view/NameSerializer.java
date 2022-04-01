package org.octri.omop_annotator.view;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * JsonSerializer that serializes a {@link Named} entity as just its name.
 */
public class NameSerializer extends JsonSerializer<Named> {

	@Override
	public void serialize(Named value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

		if (value != null) {
			gen.writeString(value.getName());
		} else {
			gen.writeNull();
		}
	}

}
