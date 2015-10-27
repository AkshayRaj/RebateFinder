package ark.com.ibotta.ui.controller.tabs;
;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.widget.TabHost;

import ark.com.ibotta.R;
import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.ui.controller.tabs.containers.MapContainer;
import ark.com.ibotta.ui.controller.tabs.containers.Offer_CategoryContainer;
import ark.com.ibotta.utils.RebateFinder;

public class TabActivity extends FragmentActivity {

    private static final String LOG_TAG = TabActivity.class.getSimpleName();
    private static final String TAB1 = Configuration.CATEGORY_TAB;
    private static final String TAB2 = Configuration.MAP_TAB;
    private static final String TAB3 = Configuration.LIST_TAB;
    private static final int TAB_ON_LAUNCH = 1;
    private Context mContext = TabActivity.this;
    private FragmentManager mFragmentManager;

    private Bundle mBundle;
    private FragmentTabHost mTabHost;
    private TabHost.OnTabChangeListener mTabChangeListener;
    public RebateFinder mRebateFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_tab);
        mBundle = savedInstanceState;
        mFragmentManager = getSupportFragmentManager();
        mRebateFinder = RebateApplication.getRebateFinder();
        //setup tabHost
        mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mTabHost.setup(this, mFragmentManager, R.id.tabcontainer);
        mTabHost.addTab(mTabHost.newTabSpec(TAB2).setIndicator(TAB2, null),
                MapContainer.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB3).setIndicator(TAB3, null),
                Offer_CategoryContainer.class, null);
        mTabHost.setCurrentTab(TAB_ON_LAUNCH - 1);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume()");
    }
//================ ACTIVITY RUNNING ======================

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy()");
    }
}
