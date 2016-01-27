package com.settler.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Property implements Parcelable {
    private Long id;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

        @Override
        public int describeContents() {
            return 0;
        }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(address);
    }

    public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() {

        public Property createFromParcel(Parcel in) {
            final Property property = new Property();
            property.setId(in.readLong());
            property.setAddress(in.readString());

            return property;
        }

        public Property[] newArray(int size) {
            return new Property[size];
        }
    };


}
