package com.settler.api.client;


import com.settler.api.Property;

import java.io.IOException;
import java.util.List;

public interface APIClient {

    List<Property> listProperties() throws IOException;

}
