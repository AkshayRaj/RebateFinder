package ark.com.ibotta.location;


import android.location.Location;

/**
 * Created by Akshayraj on 10/18/15.
 */
public class StoreLocation extends Location{
    /**
     * Construct a new Location with a named provider.
     * <p/>
     * <p>By default time, latitude and longitude are 0, and the location
     * has no bearing, altitude, speed, accuracy or extras.
     *
     * @param provider the name of the provider that generated this location
     */
    public StoreLocation(String provider) {
        super(provider);
    }
}
