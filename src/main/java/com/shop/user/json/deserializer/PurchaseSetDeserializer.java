package com.shop.user.json.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.shop.user.model.Purchase;
import com.shop.user.model.user.Organization;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PurchaseSetDeserializer extends StdDeserializer<Set<Purchase>> {

    public PurchaseSetDeserializer() {
        this(null);
    }

    public PurchaseSetDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Set<Purchase> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return new HashSet<>();
    }
}
