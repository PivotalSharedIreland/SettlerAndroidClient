package com.settler;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.settler.api.Property;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class PropertyListAdapterTest extends PropertyBaseTest {

    MainActivity mainActivity;
    PropertyListAdapter adapter;

    @Before
    public void setup() {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create().start();
        mainActivity = activityController.get();
    }

    @Test
    public void shouldGetCount() {
        List<Property> propertyList= buildPropertiesList(3);
        adapter = new PropertyListAdapter(mainActivity, propertyList);

        assertEquals(propertyList.size(), adapter.getCount());
    }

    @Test
    public void shouldGetItem() {
        List<Property> propertyList= buildPropertiesList(3);
        adapter = new PropertyListAdapter(mainActivity, propertyList);
        int position = 1;

        assertEquals(propertyList.get(position), adapter.getItem(position));
    }

    @Test
    public void shouldGetItemId() {
        List<Property> propertyList= buildPropertiesList(3);
        adapter = new PropertyListAdapter(mainActivity, propertyList);
        int position = 1;

        assertEquals(propertyList.get(position).getId().longValue(), adapter.getItemId(position));
    }

    @Test
    public void shouldGetViewWithoutConvertView() {

        List<Property> propertyList = new ArrayList<Property>() {{
                add(buildProperty(20L, "50 Larkhill Road"));
                add(buildProperty(23L, "10 O'Connell Street"));
                add(buildProperty(116L, "9 Some address"));
            }
        };

        int position = 0;

        adapter = new PropertyListAdapter(mainActivity, propertyList);
        View view = adapter.getView(position, null, null);

        assertEquals(((TextView) view.findViewById(R.id.property_id)).getText(), "20");
        assertEquals(((TextView) view.findViewById(R.id.property_address)).getText(), "50 Larkhill Road");

    }

    @Test
    public void shouldRecycleView() {

        List<Property> propertyList = new ArrayList<Property>() {{
            add(buildProperty(20L, "50 Larkhill Road"));
            add(buildProperty(23L, "10 O'Connell Street"));
            add(buildProperty(116L, "9 Some address"));
        }
        };

        adapter = new PropertyListAdapter(mainActivity, propertyList);
        View view = adapter.getView(1, null, null);

        assertEquals(((TextView) view.findViewById(R.id.property_id)).getText(), "23");
        assertEquals(((TextView) view.findViewById(R.id.property_address)).getText(), "10 O'Connell Street");

        View reclycledView = adapter.getView(2, view, null);

        assertEquals(((TextView) reclycledView.findViewById(R.id.property_id)).getText(), "116");
        assertEquals(((TextView) reclycledView.findViewById(R.id.property_address)).getText(), "9 Some address");

        assertTrue(reclycledView == view);

    }
}
