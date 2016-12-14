package jnorbury.jonahnorbury_pset6;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.ContentValues.TAG;

/**
 * Created by jonah on 09-Dec-16.
 *
 * ImgURLGetterAsyncTask receives a page title,
 * uses a query Wiki API to find the URL of the thumbnail,
 * Then passes it on to ImageAsyncTask.
 *
 */

public class ImgURLGetterAsyncTask extends AsyncTask<String, Integer, String>{

    private Context mcontext;
    private Activity mActivity;
    private JSONArray plantjsonarr;
    private Plant mplant;

    private static final String WIKI_SEARCH_PLANT =
            "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&titles=%s";

    public ImgURLGetterAsyncTask(Context context, Activity activity, Plant plant) {
        mcontext = context;
        mActivity = activity;
        mplant = plant;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    private String passURLtoImageAST(String plant_name) {

        try {
            String s = String.format(WIKI_SEARCH_PLANT, URLEncoder.encode(plant_name, "utf-8"));
            URL url = new URL(s);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.connect();

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader reader = new BufferedReader(isr);

            StringBuffer jstring = new StringBuffer(10240);
            String tmp;
            while ((tmp = reader.readLine()) != null)
                jstring.append(tmp);
            reader.close();

            JSONObject job = new JSONObject(jstring.toString());
            return job.toString();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected String doInBackground(String... params) {
        return passURLtoImageAST(params[0]);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject plantobj;
            try {
                plantobj = new JSONObject(result);
                JSONObject currentJObj = plantobj.getJSONObject("query").getJSONObject("pages");
                String myKey = currentJObj.keys().next();
                String imgUrl = currentJObj.getJSONObject(myKey)
                        .getJSONObject("thumbnail").getString("source");
                mplant.setImg_url(imgUrl);

                ImageAsyncTask iat = new ImageAsyncTask(mActivity);
                iat.execute(mplant.getImg_url());
            } catch (Exception e) {
                Log.e(TAG, "failed to get plant");
            }
            plantjsonarr = new JSONArray(result);
        } catch (Exception e) {
        }
    }
}
