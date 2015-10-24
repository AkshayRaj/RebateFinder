package ark.com.ibotta.ui.controller.tabs.containers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import ark.com.ibotta.R;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.ui.controller.tabs.TabActivity;

/**
 * Created by Akshayraj on 10/23/15.
 */
public class MyMapFragment extends SupportMapFragment {
    private static final String LOG_TAG = MyMapFragment.class.getSimpleName();
    private static final String mTAG = "MAP";
    private GoogleMap mGoogleMap;
    private TabActivity mActivity = (TabActivity) getActivity();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        Log.i(LOG_TAG, "onCreate()");
//        View fragmentView = inflater.inflate(R.layout.fragment_map, container, false);
//        mGoogleMap = ((SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map)).getMap();
//        if (mGoogleMap !=null) {
//            Marker denver = mGoogleMap.addMarker(new MarkerOptions()
//                    .position(DENVER)
//                    .title("Denver"));
//            //Move the camera instantly to hamburg with a zoom of 15.
//            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DENVER, 15));
//            // Zoom in, animating the camera.
//            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//        }
//        return fragmentView;
//    }

}
