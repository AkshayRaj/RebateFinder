package ark.com.ibotta.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Offer;
import ark.com.ibotta.utils.RebateFinder;

public class OfferListActivity extends Activity {
    private static final String LOG_TAG = OfferListActivity.class.getSimpleName();
    Context mContext = OfferListActivity.this;
    ListView mOfferListView;
    ArrayList<Offer> offerArrayList = new ArrayList<Offer>();
    RebateFinder mRebateFinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        mRebateFinder = RebateApplication.getRebateFinder();
        mOfferListView = (ListView) findViewById(R.id.list_view_offer);
        // Get the intent to get the query.
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra(MainActivity.CURRENT_LATITUDE, Configuration.DENVER_LATITUDE);
        double longitude = intent.getDoubleExtra(MainActivity.CURRENT_LONGITUDE, Configuration.DENVER_LONGITUDE);
        Location location = new Location(Configuration.NETWORK_PROVIDER);
        new RebateHandler().execute(location);
    }
    /**
     * Updates the View with the results. This is called asynchronously
     * when the results are ready.
     * @param result The results to be presented to the user.
     */
    public void updateViewWithResults(ArrayList<Offer> result) {
        Log.d("updateViewWithResults", result.toString());
        offerArrayList = result;
        //Add results to listView
        mOfferListView.setAdapter(new OfferListAdapter(mContext, offerArrayList));
        //Update Activity to show updated View
        //But first remove any other child views of the parentView
        View view = getCurrentFocus();
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        setContentView(mOfferListView);
    }

    private class RebateHandler extends AsyncTask{
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<Offer> doInBackground(Object... params) {
            return mRebateFinder.getNearbyOffers((Location) params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            updateViewWithResults((ArrayList<Offer>) result);
        };
    }
}
