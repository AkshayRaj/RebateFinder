package ark.com.ibotta;

import android.app.Application;

/**
 * Created by Akshayraj on 10/17/15.
 */
public class IbottaApplication extends Application {
    private static IbottaApplication sInstance;

    public IbottaApplication getInstance(){
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initialize();
    }

    private void initialize(){

    }
}
