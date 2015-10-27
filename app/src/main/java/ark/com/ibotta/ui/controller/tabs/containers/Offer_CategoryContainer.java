package ark.com.ibotta.ui.controller.tabs.containers;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Offer;
import ark.com.ibotta.ui.adapter.OfferListAdapter;
import ark.com.ibotta.ui.controller.tabs.TabActivity;
import ark.com.ibotta.utils.RebateFinder;

public class Offer_CategoryContainer extends BaseContainerFragment {
    private static final String LOG_TAG = Offer_CategoryContainer.class.getSimpleName();
    private boolean mIsViewInited;
    private TabActivity mActivity;
    //OfferList
    private ListView mOfferListView;
    private ProgressBar mProgressBar;
    private OfferListFragment mOfferListFragment;
    private CategoryDrawerFragment mCategoryDrawerFragment;
    private RebateFinder mRebateFinder;
    private FragmentManager mRetainedChildFragmentManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(LOG_TAG, "onAttach()");
        mActivity = (TabActivity) activity;
        mRebateFinder = RebateApplication.getRebateFinder();
        if (mRetainedChildFragmentManager != null) {
            //restore the last retained child fragment manager to the new
            //created fragment
            try {
                Field childFMField = Fragment.class.getDeclaredField("mChildFragmentManager");
                childFMField.setAccessible(true);
                childFMField.set(this, mRetainedChildFragmentManager);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView()");
        return inflater.inflate(R.layout.container_offer_category, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG, "onActivityCreated()");
        if (savedInstanceState != null) {
            //Restore fragment state in onActivityCreated()
            onViewStateRestored(savedInstanceState);
        }
        if (!mIsViewInited) {
            mIsViewInited = true;
            initView();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
//===========FRAGMENT ACTIVE =================
    @Override
    public void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(LOG_TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LOG_TAG, "onDetach");
    }
//===========FRAGMENT DESTROYED======================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(LOG_TAG, "onSaveInstanceState()");
        childFragmentManager().putFragment(outState, "OFFERLISTFRAGMENT", mOfferListFragment);
        childFragmentManager().putFragment(outState, "CATEGORYDRAWERFRAGMENT", mCategoryDrawerFragment);
    }

    private FragmentManager childFragmentManager() {
    //!!!Use this instead of getFragmentManager, support library from 20+,
    // has a bug that doesn't retain instance of nested fragments!!!!
        if(mRetainedChildFragmentManager == null) {
        mRetainedChildFragmentManager = getChildFragmentManager();
        }
        return mRetainedChildFragmentManager;
    }

    private void initView() {
        Log.i(LOG_TAG, "initView()");
        Location location = new Location(Configuration.NETWORK_PROVIDER);
        location.setLatitude(Configuration.DEFAULT_LATITUDE);
        location.setLongitude(Configuration.DEFAULT_LONGITUDE);
        new OfferRetrivalTask().execute(location);
        mOfferListFragment = new OfferListFragment();
        mCategoryDrawerFragment = new CategoryDrawerFragment();
        replaceFragment(R.id.container_framelayout_offer_list, mOfferListFragment, false);
        addFragment(R.id.container_framelayout_offer_list, mCategoryDrawerFragment, false);
    }

    private class OfferRetrivalTask extends AsyncTask {
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
            Log.i(LOG_TAG, "doInBackground()");
            return mRebateFinder.getNearbyOffers((Location) params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            Log.i(LOG_TAG, "onPostExecute()");
            updateViewWithResults((ArrayList<Offer>) result);
        }
        /**
         * Updates the View with the results. This is called asynchronously
         * when the results are ready.
         * @param result The results to be presented to the user.
         */
        public void updateViewWithResults(ArrayList<Offer> result) {
            Log.i(LOG_TAG, "updateViewWithResults()");
            ArrayList<Offer> offerArrayList = result;
            Collections.sort(offerArrayList);
            //Add results to listView
            mOfferListView = (ListView) mActivity.findViewById(R.id.list_view_offer);
            mOfferListView.setAdapter(new OfferListAdapter(mActivity, offerArrayList));
            mOfferListFragment.setListView(mOfferListView);
            mProgressBar = (ProgressBar) mActivity.findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.GONE);
        }
    }

}
