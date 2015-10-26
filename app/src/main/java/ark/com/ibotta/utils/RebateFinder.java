package ark.com.ibotta.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ark.com.ibotta.jsonhelpers.JsonCategoryHelper;
import ark.com.ibotta.jsonhelpers.JsonOfferHelper;
import ark.com.ibotta.jsonhelpers.JsonStoreHelper;
import ark.com.ibotta.model.Offer;
import ark.com.ibotta.model.Store;

public class RebateFinder {
    private static final String LOG_TAG = RebateFinder.class.getSimpleName();
    private Context mContext;
    private JsonStoreHelper mJsonStoreHelper;
    private JsonOfferHelper mJsonOfferHelper;
    private JsonCategoryHelper mJsonCategoryHelper;
    private List<Store> mNearbyStoreList; //use List so we can order Stores based on distance
    private Set<Integer> mNearbyRetailerSet; //use Set to avoid duplicates of Retailers; do not see importance for ordering here
    private List<Offer> mNearbyOfferList;  //use List so we can order offers based on other flags; eg. new_flag, amount, etc.


    public RebateFinder(Context context){
        mContext = context;
        mJsonStoreHelper = JsonStoreHelper.getInstance(mContext);
        mJsonOfferHelper = JsonOfferHelper.getInstance(mContext);
        mJsonCategoryHelper = JsonCategoryHelper.getInstance(mContext);
        mNearbyStoreList = new ArrayList<Store>();
        mNearbyRetailerSet = new HashSet<Integer>();
        mNearbyOfferList = new ArrayList<Offer>();
        loadCategoryList();
    }

    public List<Offer> getNearbyOffers(Location pCurrentLocation){
        Log.v(LOG_TAG, "getNearbyOffers()");
        getNearbyStores(pCurrentLocation);
        for(Store store : mNearbyStoreList){
            mNearbyRetailerSet.add(store.getRetailerId());
        }
        mNearbyOfferList = mJsonOfferHelper.getNearbyOffers(mNearbyRetailerSet);
        return mNearbyOfferList;
    }

    public List<Store> getNearbyStores(Location pCurrentLocation){
        Log.v(LOG_TAG, "getNearbyStores()");
        Location currentLocation = pCurrentLocation;
        List<Store> stores = mJsonStoreHelper.getNearbyStores(currentLocation);
        mNearbyStoreList = stores;
        if(stores == null){
            return null;
        }
        return mNearbyStoreList;
    }

    public void loadCategoryList(){
        mJsonCategoryHelper.execute();
//        Log.i(LOG_TAG, "mCategoryList.size(): " + mCategoryList.size());
    }
}
