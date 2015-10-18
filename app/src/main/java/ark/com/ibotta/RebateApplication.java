package ark.com.ibotta;

import android.app.Application;
import android.content.Context;

import ark.com.ibotta.utils.RebateFinder;

/**
 * Created by Akshayraj on 10/17/15.
 */
public class RebateApplication extends Application {
    private static RebateApplication sInstance;
    private static RebateFinder mRebateFinder;

    public RebateApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initialize();
    }

    private void initialize(){
        mRebateFinder = new RebateFinder(this);
    }

    public static RebateFinder getRebateFinder() {
        return mRebateFinder;
    }


}
