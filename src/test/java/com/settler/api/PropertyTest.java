package com.settler.api;


import android.os.Parcel;

import com.settler.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class PropertyTest {

    @Test
    public void serializeAndDeserializeProperty(){
        Property property = new Property();
        property.setId(1L);
        property.setAddress("address");

        Parcel parcel = Parcel.obtain();
        property.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);

        Property deserializedProperty = Property.CREATOR.createFromParcel(parcel);

        assertEquals(property, deserializedProperty);
    }

}