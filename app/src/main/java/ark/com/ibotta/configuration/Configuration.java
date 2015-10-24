package ark.com.ibotta.configuration;

import android.location.LocationManager;

/**
 * Created by Akshayraj on 10/18/15.
 */
public class Configuration {
    //tabhost labels
    public static final String CATEGORY_TAB = "FILTER";
    public static final String MAP_TAB = "STORES";
    public static final String LIST_TAB = "OFFERS";
    //default configuration helpers
    public static final int INVALID = -1;
    public static final int NEARBY_RADIUS = 20;//miles
    public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    public static final String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;
    public static final double DEFAULT_LATITUDE = 39.7392; //Denver Latitude
    public static final double DEFAULT_LONGITUDE = -104.9903; //Denver Longitude

    public static int convertMetersToMiles(float v) {
        return (int) (0.000621371 * v);
    }
}
