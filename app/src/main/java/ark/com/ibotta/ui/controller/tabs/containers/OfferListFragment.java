package ark.com.ibotta.ui.controller.tabs.containers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ark.com.ibotta.R;
import ark.com.ibotta.ui.controller.tabs.TabActivity;

public class OfferListFragment extends Fragment {
    private static final String LOG_TAG = OfferListFragment.class.getSimpleName();
    TabActivity mActivity = (TabActivity) getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate()");
        View fragmentView = inflater.inflate(R.layout.fragment_offer_list, container, false);
        return fragmentView;
    }
}
