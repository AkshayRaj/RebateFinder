package ark.com.ibotta.ui.controller.tabs.containers;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ark.com.ibotta.R;
import ark.com.ibotta.jsonhelpers.JsonCategoryHelper;
import ark.com.ibotta.ui.adapter.CategoryListAdapter;
import ark.com.ibotta.ui.controller.tabs.TabActivity;

public class CategoryDrawerFragment extends Fragment {
    private static final String LOG_TAG = CategoryDrawerFragment.class.getSimpleName();
    private static final String mTAG = "CATEGORY";
    private TabActivity mActivity;
    private DrawerLayout mDrawerLayout;
    //CategoryDrawer
    private ListView mCategoryListView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(LOG_TAG, "onAttach()");
        mActivity = (TabActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreateView()");
        View fragmentView = inflater.inflate(R.layout.drawer_category, container, false);
        mDrawerLayout = (DrawerLayout) fragmentView.findViewById(R.id.drawer_layout);
        if(mDrawerLayout != null){
            mDrawerLayout.openDrawer(GravityCompat.START);
            mCategoryListView = (ListView) fragmentView.findViewById(R.id.drawer_listview);
            mCategoryListView.setAdapter(new CategoryListAdapter(mActivity, JsonCategoryHelper.getCategories()));
        }
        return fragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(LOG_TAG, "onActivityCreated()");
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
}
