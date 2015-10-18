package ark.com.ibotta.cloud.VolleyHelpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Akshayraj on 9/21/15.
 */
public class PosterLoader extends AsyncTask {

    private static final String LOG_TAG = PosterLoader.class.getSimpleName();
    ImageView mMoviePoster;

    public PosterLoader(ImageView imageView){
        mMoviePoster = imageView;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        try {
            return getImageBitmap((String) params[0]);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object result) {
        mMoviePoster.setImageBitmap((Bitmap) result);
    }


    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}

