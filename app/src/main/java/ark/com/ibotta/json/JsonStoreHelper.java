package ark.com.ibotta.json;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.data.Store;
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

    public List<Store> getNearbyStores(Location currentLocation)  {
        Log.v(LOG_TAG, "getNearbyStores()");
        try {
            InputStream inputStream = mContext.getAssets().open("json/Stores.json");
            return readJsonStoreFileForNearbyStores(inputStream, currentLocation);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
        return null;
    }

    private static List<Store> readJsonStoreFileForNearbyStores(InputStream inputStream, Location currentLocation) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            return readJsonStoreFileObject(reader, currentLocation);
        } finally {
            reader.close();
        }
    }

    private static List<Store> readJsonStoreFileObject(JsonReader reader, Location currentLocation) throws IOException {
        List<Store> stores = new ArrayList<Store>();
        reader.beginObject();
        while(reader.hasNext()){
            String objectKey = reader.nextName();
            if(objectKey.equals(KEY_STORE_FILE_OBJECT)){
                stores = getNearbyStores(reader, currentLocation);
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return stores;
    }

    private static List<Store> getNearbyStores(JsonReader reader, Location currentLocation) throws IOException {
        List<Store> storeList = new ArrayList<Store>();
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
        int distance = Configuration.convertMetersToMiles(currentLocation.distanceTo(mStoreLocation)) ;
        if(distance <= Configuration.NEARBY_RADIUS){
            return true;
        }else {
            return false;
        }
    }
}
