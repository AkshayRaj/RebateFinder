package ark.com.ibotta.ui.controller.tabs.containers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import ark.com.ibotta.R;

public abstract class BaseContainerFragment extends Fragment {
    private final static String LOG_TAG = BaseContainerFragment.class.getSimpleName();

    public void replaceFragment(int resId, Fragment fragment, boolean addToBackStack) {
        Log.i(LOG_TAG, "replaceFragment(): " + getChildFragmentManager().getBackStackEntryCount());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(resId, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        Log.i(LOG_TAG, "popFragment(): " + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }

    public void addFragment(int containerViewId, Fragment childFragment, boolean addToBackStack){
        Log.i(LOG_TAG, "addFragment(): " + getChildFragmentManager().getBackStackEntryCount());
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.add(containerViewId, childFragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

}
