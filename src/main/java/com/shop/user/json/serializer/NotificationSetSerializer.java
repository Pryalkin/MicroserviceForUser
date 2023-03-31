package com.shop.user.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.shop.user.model.Notification;
import com.shop.user.model.user.Organization;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NotificationSetSerializer  extends StdSerializer<Set<Notification>> {

    public NotificationSetSerializer() {
        this(null);
    }

    public NotificationSetSerializer(Class<Set<Notification>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Notification> notifications, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Set<Notification> newNots = new HashSet<>();
        for (Notification not : notifications) {
            Notification notification = new Notification();
            notification.setHeader(not.getHeader());
            notification.setNotification(not.getNotification());
            notification.setDateOfCreation(not.getDateOfCreation());
            newNots.add(notification);
        }
        gen.writeObject(newNots);
    }
}