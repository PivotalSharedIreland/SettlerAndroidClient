package com.settler.api;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pivotal on 27/01/2016.
 */
public class ApiBaseTest {

    @NonNull
    public List<Property> getProperties() {
        List<Property> properties = new ArrayList<>(2);
        Property object = buildProperty(1L, "Address 1");
        properties.add(object);
        properties.add(new Property());
        return properties;
    }

    @NonNull
    public Property buildProperty(Long id, String address) {
        Property object = new Property();
        object.setId(id);
        object.setAddress(address);
        return object;
    }

}
