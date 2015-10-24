package ark.com.ibotta.ui.controller.tabs.containers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ark.com.ibotta.R;
import ark.com.ibotta.ui.controller.tabs.TabActivity;

public class CategoryFragment extends Fragment {
    private static final String LOG_TAG = CategoryFragment.class.getSimpleName();
    private TabActivity mActivity = (TabActivity) getActivity();
    private static final String mTAG = "CATEGORY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate()");
        View fragmentView = inflater.inflate(R.layout.fragment_category, container, false);
        TextView textView = (TextView) fragmentView.findViewById(R.id.textview_category);
        textView.setText(mTAG + " Content");
        return fragmentView;
    }
}
