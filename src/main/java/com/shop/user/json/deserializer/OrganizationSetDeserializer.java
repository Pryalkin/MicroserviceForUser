package com.shop.user.json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.shop.user.model.user.Organization;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class OrganizationSetDeserializer extends StdDeserializer<Set<Organization>> {

    public OrganizationSetDeserializer() {
        this(null);
    }

    public OrganizationSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<Organization> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return new HashSet<>();
    }
}
