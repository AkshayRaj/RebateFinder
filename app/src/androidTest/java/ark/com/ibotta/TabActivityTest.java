package ark.com.ibotta;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.pm.ActivityInfo;
import android.support.test.espresso.UiController;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.GeneralSwipeAction;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Swipe;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.base.UiControllerImpl_Factory;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.ui.controller.tabs.TabActivity;
import ark.com.ibotta.uiactionhelpers.OrientationChangeAction;

@RunWith(AndroidJUnit4.class)
public class TabActivityTest {
    @Rule
    public ActivityTestRule<TabActivity> mActivityRule = new ActivityTestRule<>(TabActivity.class);

    @Test
    public void onTabSelected(){
        onOffersTabSelect();
        onMapTabSelect();
        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onOffersTabSelect();
        onMapTabSelect();
    }

    public void onMapTabSelect(){
        onView(withText(Configuration.MAP_TAB)).perform(click());
    }

    public void onOffersTabSelect(){
        onView(withText(Configuration.LIST_TAB)).perform(click());
        onView(withId(R.id.drawer_listview)).perform(swipeDown());
        onView(withId(R.id.drawer_listview)).perform(swipeUp());
        onView(withId(R.id.drawer_listview_container)).perform(swipeLeft());
//        the following action fails as swipeRight() starts the swipe from the center
//        of the screen, and not on the edge
//        onView(withId(R.id.drawer_listview_container)).perform(swipeRight());
    }
}
