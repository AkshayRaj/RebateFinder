package ark.com.ibotta.ui.controller.tabs;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Offer;
import ark.com.ibotta.ui.adapter.OfferListAdapter;
import ark.com.ibotta.ui.controller.tabs.containers.CategoryContainerFragment;
import ark.com.ibotta.ui.controller.tabs.containers.MapContainerFragment;
import ark.com.ibotta.ui.controller.tabs.containers.OfferListContainerFragment;
import ark.com.ibotta.utils.RebateFinder;

public class TabActivity extends FragmentActivity {

    private static final String LOG_TAG = TabActivity.class.getSimpleName();
    private static final String TAB1 = Configuration.CATEGORY_TAB;
    private static final String TAB2 = Configuration.MAP_TAB;
    private static final String TAB3 = Configuration.LIST_TAB;
    private Context mContext = TabActivity.this;

    private Bundle mBundle;

    private FragmentTabHost mTabHost;
    private TabHost.OnTabChangeListener mTabChangeListener;

    private ListView mOfferListView;
    private ProgressBar mProgressBar;
    //public ArrayList<Offer> offerArrayList = new ArrayList<Offer>();
    public RebateFinder mRebateFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        mBundle = savedInstanceState;
        mOfferListView = (ListView) findViewById(R.id.list_view_offer);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRebateFinder = RebateApplication.getRebateFinder();
        //setup tabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontainer);
        mTabHost.addTab(mTabHost.newTabSpec(TAB1).setIndicator(TAB1, null),
                CategoryContainerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB2).setIndicator(TAB2, null),
                MapContainerFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB3).setIndicator(TAB3, null),
                OfferListContainerFragment.class, null);
        mTabChangeListener = new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId == TAB3){
                    Location location = new Location(Configuration.NETWORK_PROVIDER);
                    location.setLatitude(Configuration.DEFAULT_LATITUDE);
                    location.setLongitude(Configuration.DEFAULT_LONGITUDE);
                    new RebateHandler().execute(location);
                }else{
                    //do nothing
                }
            }
        };
        mTabHost.setOnTabChangedListener(mTabChangeListener);
    }

    private class RebateHandler extends AsyncTask {
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
        /**
         * Updates the View with the results. This is called asynchronously
         * when the results are ready.
         * @param result The results to be presented to the user.
         */
        public void updateViewWithResults(ArrayList<Offer> result) {
            Log.d(LOG_TAG, "updateViewWithResults()");
            ArrayList<Offer> offerArrayList = result;
            Collections.sort(offerArrayList);
            //Add results to listView
            mOfferListView = (ListView) findViewById(R.id.list_view_offer);
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            mOfferListView.setAdapter(new OfferListAdapter(mContext, offerArrayList));
            mProgressBar.setVisibility(View.GONE);
            //Update Activity to show updated View
            //But first remove any other child views of the parentView
        }
    }

    public ListView getOfferListView() {
        return mOfferListView;
    }
}
