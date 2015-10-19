package ark.com.ibotta.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Menu;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.location.LocationSensor;

public class MainActivity extends Activity {

    public static final String CURRENT_LATITUDE = "CURRENT_LATITUDE";
    public static final String CURRENT_LONGITUDE = "CURRENT_LONGITUDE";
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 10011;
    public static double latitude = Configuration.DEFAULT_LATITUDE; //Denver Latitude
    public static double longitude = Configuration.DEFAULT_LONGITUDE; //Denver Longitude
    private Context mContext;
    private LocationSensor mLocationSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Fires an intent to the {@link OfferListActivity} with the query.
     * {@link OfferListActivity} does all the downloading and rendering.
     * @param view
     */
    public void getNearbyOffers(View view) {
        Intent intent = new Intent(this, OfferListActivity.class);

        intent.putExtra(CURRENT_LATITUDE, latitude);
        intent.putExtra(CURRENT_LONGITUDE, longitude);
        startActivity(intent);
    }
}
