package com.settler.api.client;


import android.os.IBinder;

import com.settler.api.Property;

import java.io.IOException;
import java.util.List;

public interface APIClient extends IBinder {

    List<Property> listProperties() throws IOException;

}
