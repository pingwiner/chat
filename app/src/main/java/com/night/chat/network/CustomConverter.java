package com.night.chat.network;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.JacksonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by nightrain on 10/18/15.
 */
public class CustomConverter extends JacksonConverter {
    public CustomConverter() {
        super();
    }

    public CustomConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        return super.fromBody(body, type); //TODO: use own deserializer
    }

    @Override
    public TypedOutput toBody(Object object) {
        return super.toBody(object); //TODO: use own serializer
    }
}

