package ark.com.ibotta.ui.controller.tabs.containers;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Store;
import ark.com.ibotta.utils.RebateFinder;

public class MapContainerFragment extends BaseContainerFragment {
    private static final String LOG_TAG = MapContainerFragment.class.getSimpleName();
    static final LatLng DENVER = new LatLng(Configuration.DEFAULT_LATITUDE, Configuration.DEFAULT_LONGITUDE);
    private boolean mIsViewInited;
    private MyMapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private RebateFinder mRebateFinder;

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

    private void initView() {
        Log.i(LOG_TAG, "initView()");
        mMapFragment = new MyMapFragment();
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

    private class StoreLocator extends AsyncTask{

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
