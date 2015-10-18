package ark.com.ibotta.jsonhelpers;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Store;
import ark.com.ibotta.location.StoreLocation;

public class JsonStoreHelper {
    private static final String LOG_TAG = JsonStoreHelper.class.getSimpleName();
    private static JsonStoreHelper sInstance;
    private static Context mContext;
    private static StoreLocation mStoreLocation = new StoreLocation(Configuration.NETWORK_PROVIDER);

    private static final String KEY_STORE_FILE_OBJECT = "stores";

    private JsonStoreHelper(Context context){
        mContext = context;
    }

    public static JsonStoreHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new JsonStoreHelper(context);
        }
        return sInstance;
    }

    public ArrayList<Store> getNearbyStores(Location currentLocation)  {
        Log.v(LOG_TAG, "getNearbyStores()");
        try {
            InputStream inputStream = mContext.getAssets().open("json/Stores.json");
            return readJsonStoreFileForNearbyStores(inputStream, currentLocation);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
        return null;
    }

    private static ArrayList<Store> readJsonStoreFileForNearbyStores(InputStream inputStream, Location currentLocation) throws IOException {
        Log.v(LOG_TAG, "readJsonStoreFileForNearbyStores()");
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            ArrayList<Store> storeArrayList = readJsonStoreFileObject(reader, currentLocation);
            Log.v(LOG_TAG, reader.getPath());
            return storeArrayList;
        } finally {
            reader.close();
        }
    }

    private static ArrayList<Store> readJsonStoreFileObject(JsonReader reader, Location currentLocation) throws IOException {
        ArrayList<Store> stores = new ArrayList<Store>();
        reader.beginObject();
        while(reader.hasNext()){
            String objectKey = reader.nextName();
            if(objectKey.equals(KEY_STORE_FILE_OBJECT) && reader.peek() != JsonToken.NULL){
                stores = readStoreArray(reader, currentLocation);
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return stores;
    }

    private static ArrayList<Store> readStoreArray(JsonReader reader, Location currentLocation) throws IOException {
        ArrayList<Store> storeList = new ArrayList<Store>();
        reader.beginArray();
        while (reader.hasNext()) {
            Store store = readStore(reader);
            if(isNearbyStore(store, currentLocation)) {
                storeList.add(store);
            }
        }
        reader.endArray();
        return storeList;
    }

    private static Store readStore(JsonReader reader) throws IOException {
        int id = Configuration.INVALID;
        int retailer_id = Configuration.INVALID;
        double latitude = Configuration.INVALID;
        double longitude = Configuration.INVALID;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals(Store.KEY_ID)) {
                id = reader.nextInt();
            } else if (name.equals(Store.KEY_RETAILER_ID)) {
                retailer_id = reader.nextInt();
            } else if (name.equals(Store.KEY_LATITUDE)){
                latitude = reader.nextDouble();
            } else if (name.equals(Store.KEY_LONGITUDE)){
                longitude = reader.nextDouble();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Store.StoreBuilder()
                .setId(id)
                .setRetailerId(retailer_id)
                .setLatitude(latitude)
                .setLongitude(longitude)
                .create();
    }

    private static boolean isNearbyStore(Store store, Location currentLocation){
        mStoreLocation.setLatitude(store.getLatitude());
        mStoreLocation.setLongitude(store.getLongitude());
        Log.v(LOG_TAG, "currentLocation_Latitude: " + currentLocation.getLatitude());
        Log.v(LOG_TAG, "mStoreLocation_Latitude: " + mStoreLocation.getLatitude());
        Log.v(LOG_TAG, "currentLocation_Longitude: " + currentLocation.getLongitude());
        Log.v(LOG_TAG, "mStoreLocation_Longitude: " + mStoreLocation.getLongitude());
        float distanceInMeters = currentLocation.distanceTo(mStoreLocation);
        Log.v(LOG_TAG, "distanceInMeters: " + distanceInMeters);
        int distanceInMiles = Configuration.convertMetersToMiles(distanceInMeters) ;
        Log.v(LOG_TAG, "distanceInMiles: " + distanceInMiles);
        if(distanceInMiles <= Configuration.NEARBY_RADIUS){
            return true;
        }else {
            return false;
        }
    }
}
