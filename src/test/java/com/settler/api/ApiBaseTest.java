package com.settler.api;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.settler.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by pivotal on 27/01/2016.
 */
public class ApiBaseTest {

    @NonNull
    public List<Property> buildPropertiesList(int numberOfProperties) {
        return Arrays.asList(buildPropertiesArray(numberOfProperties));
    }

    @NonNull
    public Property buildProperty(Long id, String address) {
        Property object = new Property();
        object.setId(id);
        object.setAddress(address);
        return object;
    }

    @NonNull
    public Bundle getMockedBundle(int numberOfProperties) {

        final Property[] properties = buildPropertiesArray(numberOfProperties);

        final Bundle bundle = mock(Bundle.class);
        when(bundle.getParcelableArray(Constants.ExtrasKeys.PROPERTIES_LIST))
                .thenReturn(properties);

        return bundle;
    }

    @NonNull
    public Property[] buildPropertiesArray(int numberOfProperties) {
        final Property[] properties = new Property[numberOfProperties];
        for (int i = 0; i < numberOfProperties; i++) {
            int id = i + 1;
            properties[i] = buildProperty(Long.valueOf(id), "Address " + id);
        }
        return properties;
    }

}
