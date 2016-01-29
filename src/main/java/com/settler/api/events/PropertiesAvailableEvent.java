package com.settler.api.events;

import com.settler.api.Property;

import java.util.List;

public class PropertiesAvailableEvent {

    private final List<Property> properties;

    public PropertiesAvailableEvent(List<Property> properties) {
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }

}
