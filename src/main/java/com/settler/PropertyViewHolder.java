package com.settler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.settler.api.Property;

public class PropertyViewHolder extends RecyclerView.ViewHolder {

    protected TextView propertyId;
    protected TextView propertyAddress;

    public PropertyViewHolder(final ViewGroup parent) {
        super(parent);
        propertyId = (TextView) itemView.findViewById(R.id.property_id);
        propertyAddress = (TextView) itemView.findViewById(R.id.property_address);
    }

    public void onBind(Property property) {
        propertyAddress.setText(property.getAddress());
    }
}
