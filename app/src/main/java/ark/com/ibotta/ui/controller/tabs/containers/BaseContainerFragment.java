package ark.com.ibotta.ui.controller.tabs.containers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import ark.com.ibotta.R;

public class BaseContainerFragment extends Fragment {

    public void replaceFragment(int resId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.replace(resId, fragment);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        Log.e("test", "pop fragment: " + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }

}
