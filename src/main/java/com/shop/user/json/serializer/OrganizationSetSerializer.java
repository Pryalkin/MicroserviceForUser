package com.shop.user.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.shop.user.model.user.Organization;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganizationSetSerializer extends StdSerializer<Set<Organization>> {

    public OrganizationSetSerializer() {
        this(null);
    }

    public OrganizationSetSerializer(Class<Set<Organization>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Organization> organizations, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Set<Organization> newOrgs = new HashSet<>();
        for (Organization org : organizations) {
            Organization organization = new Organization();
//            organization.setId(org.getId());
            organization.setName(org.getName());
            organization.setDescription(org.getDescription());
            organization.setLogo(org.getLogo());
            organization.setActivity(org.getActivity());
            newOrgs.add(organization);
        }
        gen.writeObject(newOrgs);
    }
}
