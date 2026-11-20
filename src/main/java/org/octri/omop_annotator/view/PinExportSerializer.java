package org.octri.omop_annotator.view;

import org.octri.omop_annotator.domain.app.Pin;
import tools.jackson.databind.SerializationContext;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Serializer for exporting pins matched to judgments
 */
public class PinExportSerializer extends StdSerializer<Pin> {

    public PinExportSerializer() {
        this(null);
    }

    public PinExportSerializer(Class<Pin> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(Pin pin, JsonGenerator jsonGenerator, SerializationContext serializer) {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringProperty("entity", pin.getEntity().name());
        jsonGenerator.writeStringProperty("entityId", Long.toString(pin.getEntityId()));
        jsonGenerator.writeStringProperty("comment", pin.getComment() == null ? "" : pin.getComment());
        jsonGenerator.writeEndObject();
    }

    /**
     * Provide an JsonMapper that can serialize the Pin in the desired format for export
     * 
     * @return
     */
    public static JsonMapper getMapper() {
        SimpleModule module = new SimpleModule("PinExportSerializer");
        module.addSerializer(Pin.class, new PinExportSerializer());
        return JsonMapper.builder()
				.addModule(module)
				.build();
    }
}
