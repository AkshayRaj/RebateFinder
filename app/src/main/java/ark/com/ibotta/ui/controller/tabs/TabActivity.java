package ark.com.ibotta.ui.controller.tabs;
;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
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
}
