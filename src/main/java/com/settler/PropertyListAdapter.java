package com.settler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.settler.api.Property;

import java.util.List;

/**
 * Created by pivotal on 29/01/2016.
 */
public class PropertyListAdapter extends BaseAdapter {

    private final List<Property> properties;
    private final LayoutInflater inflater;

    public PropertyListAdapter(final Activity activity, final List<Property> properties) {
        this.properties = properties;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return properties.size();
    }

    @Override
    public Object getItem(int position) {
        return properties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return properties.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        PropertyViewHolder viewHolder;

        if (convertView == null) {
            v = inflater.inflate(R.layout.property_list_item, null);
            viewHolder = new PropertyViewHolder((ViewGroup)v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (PropertyViewHolder) v.getTag();
        }

        populateView(position, viewHolder);

        return v;

    }

    private void populateView(int position, PropertyViewHolder viewHolder) {
        final Property property = properties.get(position);

        viewHolder.propertyId.setText(property.getId().toString());
        viewHolder.propertyAddress.setText(property.getAddress());
    }

    class PropertyViewHolder {

        public TextView propertyId;
        public TextView propertyAddress;

        public PropertyViewHolder(final ViewGroup parent) {
            propertyId = (TextView)parent.findViewById(R.id.property_id);
            propertyAddress = (TextView)parent.findViewById(R.id.property_address);
        }

    }

}
