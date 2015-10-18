package ark.com.ibotta.configuration;

import android.location.LocationManager;

/**
 * Created by Akshayraj on 10/18/15.
 */
public class Configuration {
    public static final int INVALID = -1;
    public static final int NEARBY_RADIUS = 20;//miles
    public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    public static final String NETWORK_PROVIDER = LocationManager.NETWORK_PROVIDER;
    public static final double DENVER_LATITUDE = 39.7392; //Denver Latitude
    public static final double DENVER_LONGITUDE = -104.9903; //Denver Longitude

    public static int convertMetersToMiles(float v) {
        return (int) (0.000621371 * v);
    }
}
