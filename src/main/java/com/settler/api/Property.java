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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        if (id != null ? !id.equals(property.id) : property.id != null) return false;
        return !(address != null ? !address.equals(property.address) : property.address != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
