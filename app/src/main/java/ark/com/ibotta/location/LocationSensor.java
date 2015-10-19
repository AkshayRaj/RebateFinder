package ark.com.ibotta.location;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/*
   LocationSensor class
   - will be used to getCurrentUserLocation
   - currently all json data seems to be from Denver, so hardcoding the values in Configuration.java
 */
public class LocationSensor implements ConnectionCallbacks,
        OnConnectionFailedListener   {

    private static final String LOG_TAG = LocationSensor.class.getSimpleName();
    protected static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    protected static final long FASTEST_INTERVAL = 1000;//millis = 1 sec
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private com.google.android.gms.location.LocationListener mLocationListener;
    private LocationRequest mLocationRequest;
    private Location mLocation;

    public LocationSensor(Context context){
        mContext = context;
        mLocationListener = new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mLocation = location;
            }
        };
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        connect();
    }

    protected void connect(){

        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            return;
        }
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
            mLocationRequest.setInterval(FASTEST_INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mGoogleApiClient.connect();
    }

    protected void disconnect(){
        if(mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
            mGoogleApiClient.disconnect();
        }
    }

    protected void requestLocationUpdates(){
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, mLocationListener);
    }

    @Override
    public void onConnected(Bundle dataBundle) {
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        requestLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution() && mContext instanceof Activity) {
            try {
                connectionResult.startResolutionForResult((Activity) mContext, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
        disconnect();
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location mLocation) {
        this.mLocation = mLocation;
    }
}
