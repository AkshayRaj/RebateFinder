package ark.com.ibotta.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ark.com.ibotta.R;
import ark.com.ibotta.data.Offer;

public class OfferListActivity extends Activity {
    private static final String LOG_TAG = OfferListActivity.class.getSimpleName();
    Context mContext = OfferListActivity.this;
    ListView mOfferListView;
    ArrayList<Offer> movieArrayList = new ArrayList<Offer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mOfferListView = (ListView) findViewById(R.id.list_view_movie);
        // Get the intent to get the query.
        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);

        // Check if the NetworkConnection is active and connected.
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        mCloudManager = CloudManager.getInstance(this);
        if (networkInfo != null && networkInfo.isConnected()) {
            new TmdbHandler().execute(query);
        } else {
            TextView textView = new TextView(this);
            textView.setText("No network connection.");
            setContentView(textView);
        }

    }
}
