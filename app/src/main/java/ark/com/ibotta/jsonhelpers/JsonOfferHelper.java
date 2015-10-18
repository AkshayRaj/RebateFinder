package ark.com.ibotta.jsonhelpers;

import android.content.Context;
import android.util.Log;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ark.com.ibotta.configuration.Configuration;
import ark.com.ibotta.model.Offer;

/**
 * Created by Akshayraj on 10/18/15.
 */
public class JsonOfferHelper {
    private static final String LOG_TAG = JsonOfferHelper.class.getSimpleName();
    private static final String KEY_OFFER_FILE_OBJECT = "offers";
    private static JsonOfferHelper sInstance;
    private static Context mContext; //context required to retrieve json file from assets

    private JsonOfferHelper(Context context){
        mContext = context;
    }

    public static JsonOfferHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new JsonOfferHelper(context);
        }
        return sInstance;
    }

    public List<Offer> getNearbyOffers(Set<Integer> retailerSet)  {
        Log.v(LOG_TAG, "getNearbyOffers()");
        try {
            InputStream inputStream = mContext.getAssets().open("json/Offers.json");
            return readJsonOfferFileForNearbyOffers(inputStream, retailerSet);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
        return null;
    }

    private static List<Offer> readJsonOfferFileForNearbyOffers(InputStream inputStream, Set<Integer> retailerSet) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            return readJsonOfferFileObject(reader, retailerSet);
        } finally {
            reader.close();
        }
    }

    private static List<Offer> readJsonOfferFileObject(JsonReader reader, Set<Integer> retailerSet) throws IOException {
        List<Offer> stores = new ArrayList<Offer>();
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals(KEY_OFFER_FILE_OBJECT)){
                stores = getNearbyOffers(reader, retailerSet);
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return stores;
    }

    private static List<Offer> getNearbyOffers(JsonReader reader, Set<Integer> retailerSet) throws IOException {
        List<Offer> offerList = new ArrayList<Offer>();
        reader.beginArray();
        while (reader.hasNext()) {
            Offer offer = readOffer(reader);
            if(isNearbyOffer(offer, retailerSet)) {
                if(offerList.size() <= 10) {
                    offerList.add(offer);
                }
            }
        }
        reader.endArray();
        return offerList;
    }

    private static Offer readOffer(JsonReader reader) throws IOException {
        int id = Configuration.INVALID;
        List<Integer> retailerList = new ArrayList<Integer>();
        String name = "";
        reader.beginObject();
        while (reader.hasNext()) {
            String objectKey = reader.nextName();
            if (objectKey.equals(Offer.KEY_ID)) {
                id = reader.nextInt();
            } else if(objectKey.equals(Offer.KEY_RETAILER_LIST)){
                retailerList = getRetailerList(reader);
            } else if(objectKey.equals(Offer.KEY_NAME)){
                name = reader.nextString();
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Offer.OfferBuilder()
                .setId(id)
                .setName(name)
                .setRetailerList(retailerList)
                .create();
    }

    private static List<Integer> getRetailerList(JsonReader reader) throws IOException {
        List<Integer> retailerList = new ArrayList<Integer>();
        reader.beginArray();
        while(reader.hasNext()){
            retailerList.add(reader.nextInt());
        }
        reader.endArray();
        return retailerList;
    }

    private static boolean isNearbyOffer(Offer offer, Set<Integer> retailerSet){
        for(int retailerId : offer.getRetailerList()){
            if(retailerSet.contains(retailerId)){
                return true;
            }
        }
        return false;
    }
}
