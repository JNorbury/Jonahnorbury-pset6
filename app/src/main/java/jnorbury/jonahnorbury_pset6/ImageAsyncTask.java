package jnorbury.jonahnorbury_pset6;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by jonah on 11-Dec-16.
 *
 * ImageAsyncTask loads an image from a URL into the ImageView placeholder in ShowPlantActivity.
 *
 */

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmimage;
    private String the_url;

    public ImageAsyncTask(Activity mActivity) {
        this.bmimage = (ImageView) mActivity.findViewById(R.id.plantIV);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap Icon1;
        the_url = urls[0];
        try {
            InputStream is = new java.net.URL(the_url).openStream();
            Icon1 = BitmapFactory.decodeStream(is);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Icon1;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        try{
            bmimage.setImageBitmap(result);
        } catch (Exception e) {
            Log.e(TAG, "onPostExecute: bmi.setimage is no");
        }
    }
}
