package ark.com.ibotta.ui.controller.tabs.containers;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Store;
import ark.com.ibotta.ui.controller.tabs.TabActivity;
import ark.com.ibotta.utils.RebateFinder;

public class MapContainer extends BaseContainerFragment {
    private static final String LOG_TAG = MapContainer.class.getSimpleName();
    static final LatLng DENVER = new LatLng(Configuration.DEFAULT_LATITUDE, Configuration.DEFAULT_LONGITUDE);
    private TabActivity mActivity;
    public static View mView;
    private boolean mIsViewInited;
    private StoreMapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private RebateFinder mRebateFinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(LOG_TAG, "onAttach()");
        mActivity = (TabActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView()");
        mRebateFinder = RebateApplication.getRebateFinder();
        return inflater.inflate(R.layout.container_fragment_map, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG, "onActivityCreated()");
        if (!mIsViewInited) {
            mIsViewInited = true;
            initView();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
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
//=========FRAGMENT DESTROYED=====================
    private void initView() {
        Log.i(LOG_TAG, "initView()");
        mMapFragment = new StoreMapFragment();
        replaceFragment(R.id.container_framelayout_map, mMapFragment, false);
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                if (mGoogleMap != null) {
                    Location location = new Location(Configuration.NETWORK_PROVIDER);
                    location.setLatitude(Configuration.DEFAULT_LATITUDE);
                    location.setLongitude(Configuration.DEFAULT_LONGITUDE);
                    new StoreLocator().execute(location);
                }
            }
        });
    }

    private void updateViewWithResults(ArrayList<Store> result){
        ArrayList<Store> stores = result;
        for(Store store : stores){
            LatLng storelatLng = new LatLng(store.getLatitude(), store.getLongitude());
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(storelatLng)
                    .title(String.valueOf(store.getRetailerId())));
        }
        //Move the camera instantly to denver with a zoom of 15.
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DENVER, 15));
        // Zoom in, animating the camera.
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    public class StoreLocator extends AsyncTask{

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
        protected List<Store> doInBackground(Object[] params) {
            return mRebateFinder.getNearbyStores((Location) params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            updateViewWithResults((ArrayList<Store>) result);
        }
    }
}
