package ark.com.ibotta.utils;

import android.content.Context;
import android.location.Location;

import java.util.List;
import java.util.Set;

import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.data.Retailer;
import ark.com.ibotta.json.JsonOfferHelper;
import ark.com.ibotta.json.JsonStoreHelper;
import ark.com.ibotta.data.Offer;
import ark.com.ibotta.data.Store;
import ark.com.ibotta.location.StoreLocation;

public class RebateFinder {
    private Context mContext;
    private JsonStoreHelper mJsonStoreHelper;
    private JsonOfferHelper mJsonOfferHelper;
    private List<Store> mNearbyStoreList; //use List so we can order Stores based on distance
    private Set<Integer> mNearbyRetailerSet; //use Set to avoid duplicates of Retailers; do not see importance for ordering here
    private List<Offer> mNearbyOfferList;  //use List so we can order offers based on other flags; eg. new_flag, amount, etc.


    public RebateFinder(Context context){
        mContext = context;
        mJsonStoreHelper = JsonStoreHelper.getInstance(mContext);
        mJsonOfferHelper = JsonOfferHelper.getInstance(mContext);
    }

    public List<Offer> getNearbyOffers(Location pCurrentLocation){
        Location currentLocation = pCurrentLocation;
        mNearbyStoreList = mJsonStoreHelper.getNearbyStores(currentLocation);
        if(mNearbyStoreList == null){
            return null;
        }
        for(Store store : mNearbyStoreList){
            mNearbyRetailerSet.add(store.getRetailerId());
        }
        mNearbyOfferList = mJsonOfferHelper.getNearbyOffers(mNearbyRetailerSet);
        return mNearbyOfferList;
    }
}
