package com.settler;

import android.content.Context;
import android.os.Build;
import android.view.ViewGroup;

import com.settler.api.Property;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class PropertyRecyclerViewAdapterTest extends PropertyBaseTest {

    @Test
    public void shouldReturnTheNumberOfItems(){
        assertEquals(new PropertyRecyclerViewAdapter(buildPropertiesList(1)).getItemCount(), 1);
        assertEquals(new PropertyRecyclerViewAdapter(buildPropertiesList(2)).getItemCount(), 2);
        assertEquals(new PropertyRecyclerViewAdapter(buildPropertiesList(10)).getItemCount(), 10);
    }

    @Test
    public void shouldBindViewHolder(){
        List<Property> properties = buildPropertiesList(1);
        PropertyRecyclerViewAdapter adapter = new PropertyRecyclerViewAdapter(properties);
        PropertyViewHolder propertyViewHolder = mock(PropertyViewHolder.class);

        adapter.bindViewHolder(propertyViewHolder, 0);
        verify(propertyViewHolder, times(1)).onBind(properties.get(0));
    }

}