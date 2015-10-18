package ark.com.ibotta.cloud;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import ark.com.ibotta.cloud.VolleyHelpers.StringRequestJsonResponse;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CloudManager {

    private static final String LOG_TAG = CloudManager.class.getSimpleName();
    private static Context mContext;
    private static CloudManager sInstance;

    public CloudManager(Context context){
        mContext = context;
    }

    public static CloudManager getInstance(Context context){
        mContext = context;
        if(sInstance == null){
            sInstance = new CloudManager(context);
        }
        return sInstance;
    }

    private enum CloudHelpers {
        BASE_URL("http://api.themoviedb.org/3"),
        SEARCH_MOVIE("/search/movie"),
        API_KEY_FIELD("?api_key="),
        QUERY_FIELD("&query="),
        ACCEPT_CONTENT("Accept"),
        APP_JSON("application/json;charset=UTF-8");

        private final String mKey;

        private CloudHelpers(String s) {
            mKey = s;
        }

        public String toString() {
            return mKey;
        }
    }

    public JSONObject searchForMovies(String query, ResponseListener taskHandler){

        Log.d(LOG_TAG, "searchForMovies");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CloudHelpers.BASE_URL.toString());
        stringBuilder.append(CloudHelpers.SEARCH_MOVIE.toString());
        String encodedUrl = null;

        try {
            encodedUrl = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            // Can be safely ignored because UTF-8 is always supported
        }
        stringBuilder.append(CloudHelpers.QUERY_FIELD.toString() + encodedUrl);
        String url = stringBuilder.toString();

        Log.d(LOG_TAG, "url: " + url);

        return syncStringRequest(Request.Method.GET, url, taskHandler);
    }

    /**
       syncStringRequest(int, String, ResponseListener)
       - Uses RequestFuture in Volley to get Synchronous behavior
    */
    private JSONObject syncStringRequest(final int httpMethod, String url, final ResponseListener taskHandler){

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();
        StringRequestJsonResponse request = new StringRequestJsonResponse(httpMethod, url, requestFuture,requestFuture);
        VolleySingleton.getInstance(mContext).addToRequestQueue(request);
        JSONObject response = new JSONObject();
        try {
            response = requestFuture.get();//ensures synchronous behavior
        } catch (Exception e) {
            Log.d(LOG_TAG, "Error occurred while getting a response: " + e.toString());
        }
        return response;
    }

    /**
       asyncStringRequest(int, String, ResponseListener, boolean)
       - Uses volley to create a getRequest
         Response is Asynchronous. So we have to override and use -
       - Response.Listener
       - Response.ErrorListener
     */
    private void asyncStringRequest(final int httpMethod, String url, final ResponseListener taskHandler, boolean nofuture){
        Log.d(LOG_TAG, "syncStringRequest()");
        StringRequestJsonResponse stringRequest = new StringRequestJsonResponse(httpMethod, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d(LOG_TAG, "onResponse()");
                        taskHandler.onResponseReceived(jsonObject);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "onErrorResponse");
                taskHandler.onResponseFailed(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(CloudHelpers.ACCEPT_CONTENT.toString(), CloudHelpers.APP_JSON.toString());

                return headers;
            }
        };
        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }
}