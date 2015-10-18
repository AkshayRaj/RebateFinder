package ark.com.ibotta.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import ark.com.ibotta.R;

public class MainActivity extends Activity {

    public static final String CURRENT_LATITUDE = "CURRENT_LATITUDE";
    public static final String CURRENT_LONGITUDE = "CURRENT_LONGITUDE";
    public static double latitude = 39.7392; //Denver Latitude
    public static double longitude = -104.9903; //Denver Longitude

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
