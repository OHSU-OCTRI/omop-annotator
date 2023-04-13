package org.octri.omop_annotator.view;

import java.io.IOException;

import org.octri.omop_annotator.domain.app.Pin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
    public void serialize(Pin pin, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("entity", pin.getEntity().name());
        jsonGenerator.writeStringField("entityId", Long.toString(pin.getEntityId()));
        jsonGenerator.writeStringField("comment", pin.getComment() == null ? "" : pin.getComment());
        jsonGenerator.writeEndObject();
    }

    /**
     * Provide an ObjectMapper that can serialize the Pin in the desired format for export
     * 
     * @return
     */
    public static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("PinExportSerializer");
        module.addSerializer(Pin.class, new PinExportSerializer());
        mapper.registerModule(module);
        return mapper;
    }
}
