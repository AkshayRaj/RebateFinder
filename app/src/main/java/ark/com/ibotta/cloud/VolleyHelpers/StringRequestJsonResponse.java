package ark.com.ibotta.cloud.VolleyHelpers;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 *
 */
public class StringRequestJsonResponse extends Request<JSONObject> {

    private static final String LOG_TAG = StringRequestJsonResponse.class.getSimpleName();
    private final Response.Listener<JSONObject> mListener;

    public StringRequestJsonResponse(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        Log.d(LOG_TAG, "StringRequestJsonResponse()");
        mListener = listener;
    }

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            Log.d(LOG_TAG, "parseNetworkResponse: " + response.statusCode);
            try {
                String jsonString =
                        new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected void deliverResponse(JSONObject jsonObject) {
            Log.d(LOG_TAG, "deliverResponse: " + jsonObject.toString());
            mListener.onResponse(jsonObject);
        }
}
