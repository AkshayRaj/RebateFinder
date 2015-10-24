package ark.com.ibotta.ui.controller.tabs.containers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ark.com.ibotta.R;
import ark.com.ibotta.ui.controller.tabs.TabActivity;

public class CategoryContainerFragment extends BaseContainerFragment {
    private static final String LOG_TAG = CategoryContainerFragment.class.getSimpleName();
    private boolean mIsViewInited;
    private TabActivity mActivity;

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
        replaceFragment(R.id.container_framelayout_category,new CategoryFragment(), false);
    }

}
