package ark.com.ibotta.ui.controller.tabs.containers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ark.com.ibotta.R;

public class CategoryContainerFragment extends BaseContainerFragment {
    private static final String LOG_TAG = CategoryContainerFragment.class.getSimpleName();
    private boolean mIsViewInited;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView()");
        return inflater.inflate(R.layout.container_fragment_category, null);
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
        replaceFragment(R.id.container_framelayout_category,new CategoryFragment(), false);
    }

}
