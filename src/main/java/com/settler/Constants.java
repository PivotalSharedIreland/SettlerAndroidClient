package com.settler;

public class Constants {

    public class ResultCodes {

        public static final int PROPERTIES_LIST_OBTAINED = 1;

    }

    public class ExtrasKeys {

        public static final String PROPERTIES_LIST = "propertyList";

    }

    //TODO CHECK CONVENTION ON FILTER NAMES
    public class IntentFilterKeys {
        public static final String PROPERTIES_LIST_REQUEST = "settler.intent.action.LIST_PROPERTIES";
        public static final String PROPERTIES_LIST_RESULT = "settler.intent.action.PROPERTY_LIST_OBTAINED";
    }

}
