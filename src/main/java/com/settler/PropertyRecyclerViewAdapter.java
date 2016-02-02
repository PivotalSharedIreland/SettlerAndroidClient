package com.settler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.settler.api.Property;

import java.util.Collections;
import java.util.List;

public class PropertyRecyclerViewAdapter extends RecyclerView.Adapter<PropertyViewHolder> {
    private List<Property> properties;

    public PropertyRecyclerViewAdapter(List<Property> properties) {
        this.properties = properties == null ? Collections.<Property>emptyList() : properties;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewGroup itemView = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.property_list_item, parent, false);
        return new PropertyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PropertyViewHolder holder, int position) {
        holder.onBind(properties.get(position));
    }

    @Override
    public int getItemCount() {
        return properties.size();
    }
}
