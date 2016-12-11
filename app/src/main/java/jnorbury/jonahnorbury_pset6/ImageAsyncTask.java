package jnorbury.jonahnorbury_pset6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jonah on 11-Dec-16.
 */

public class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmimage;
    private String the_url;
    private static final String IMG_URL = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&titles=%s";

    public void setBmimage(ImageView bmimage, String the_url) {
        this.bmimage = bmimage;
        this.the_url = the_url;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Bitmap Icon1 = null;
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
        bmimage.setImageBitmap(result);
    }
}
