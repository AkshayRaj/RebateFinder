package ark.com.ibotta.jsonhelpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ark.com.ibotta.RebateApplication;
import ark.com.ibotta.model.Offer;

/**
 * Created by Akshayraj on 10/25/15.
 */
public class JsonCategoryHelper extends AsyncTask{
    private static final String LOG_TAG = JsonOfferHelper.class.getSimpleName();
    private static final String KEY_OFFER_FILE_OBJECT = "offers";//base json object which as all data
    private static JsonCategoryHelper sInstance;
    private static Context mContext; //context required to retrieve json file from assets
    private static Set<String> mCategories;
    private static List<String> mCategoryList;

    private JsonCategoryHelper(Context context){
        mContext = context;
        mCategories = new HashSet<String>();
        mCategoryList = new ArrayList<>();
    }

    public static JsonCategoryHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new JsonCategoryHelper(context);
        }
        return sInstance;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        openOfferJsonFileForParsing();
        Log.i(LOG_TAG, "mCategories.size(): " + mCategories.size());
        mCategoryList = new ArrayList<>(mCategories);
        return null;
    }

    public void openOfferJsonFileForParsing()  {
        Log.v(LOG_TAG, "openOfferJsonFileForParsing()");
        try {
            InputStream inputStream = mContext.getAssets().open("json/Offers.json");
            readJsonOfferFile(inputStream);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception: " + e.getLocalizedMessage());
        }
    }

    private static void readJsonOfferFile(InputStream inputStream) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));
        try {
            readJsonOfferFileObject(reader);
        } finally {
            reader.close();
        }
    }

    private static void readJsonOfferFileObject(JsonReader reader) throws IOException {
        reader.beginObject();
        while(reader.hasNext()){
            String name = reader.nextName();
            if(name.equals(KEY_OFFER_FILE_OBJECT)){
                readOffersArray(reader);
            } else{
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private static void readOffersArray(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            readObjectsInArray(reader);
        }
        reader.endArray();
    }

    private static void readObjectsInArray(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String objectKey = reader.nextName();
            if (objectKey.equals(Offer.KEY_CATEGORY)) {
                readCategories(reader);
            }  else{
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    private static void readCategories(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            readCategoryObjectInArray(reader);
        }
        reader.endArray();
    }

    private static void readCategoryObjectInArray(JsonReader reader) throws IOException {
        reader.beginObject();
        while(reader.hasNext()){
            String objectKey = reader.nextName();
            if (objectKey.equals(Offer.KEY_CATEGORY_NAME)) {
                mCategories.add(reader.nextString());
            }  else{
                reader.skipValue();
            }
        }
        reader.endObject();
    }

    public static List<String> getCategories() {
        return mCategoryList;
    }
}
