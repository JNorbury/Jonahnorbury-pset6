package jnorbury.jonahnorbury_pset6;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * Created by jonah on 09-Dec-16.
 *
 * LoadPlantFactsAsyncTask receives a search term,
 * uses the OpenSearch api from wikipedia to generate results,
 * then displays the results in a listview in SearchPlantActivity.
 *
 */

public class LoadPlantFactsAsyncTask extends AsyncTask<String, Integer, String>{

    private Context mcontext;
    private String searchterm;
    private Activity mActivity;
    private JSONArray urls;
    private JSONArray names;
    private JSONArray descs;
    private ProgressDialog progDailog;

    private static final String WIKI_SEARCH_PLANT =
            "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=%s&limit=4";

    public LoadPlantFactsAsyncTask(Context context, Activity activity) {
        mcontext = context;
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        onProgressInitiate();
    }


    protected String getWikiResult(String plant_name) {
        searchterm = plant_name;

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

            String stringstring = jstring.toString();
            return stringstring;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected String doInBackground(String... params) {
        return getWikiResult(params[0]);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONArray plantjson = new JSONArray(result);
            ListView plantSearchLV = (ListView) mActivity.findViewById(R.id.searchresultsLV);

            // wacky Wiki api returns lists of lists, JSONArray(1) is the list of wiki page names,
            // JSONArray(2) for their respective descriptions and JSONArray(3) for the urls.
            names = plantjson.getJSONArray(1);
            descs = plantjson.getJSONArray(2);
            urls = plantjson.getJSONArray(3);

            int n = names.length();

            if (n == 0) {
                Toast.makeText(mcontext, "No results for " + searchterm +" :(", Toast.LENGTH_SHORT).show();
            }

            final ArrayList<String> list = new ArrayList<String>();

            for (int i = 0; i < n; i++) {
                list.add(names.get(i).toString());
            }

            final ArrayAdapter adapter = new ArrayAdapter(mActivity,
                    android.R.layout.simple_list_item_1, list);
            plantSearchLV.setAdapter(adapter);

            // OnClickItemListener for listview in SearchPlantActivity
            // Passes Object (of type Plant) onto
            plantSearchLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    try {
                        String planttype = names.get(position).toString();
                        String description = descs.get(position).toString();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy",
                                Locale.getDefault()).format(new Date());

                        Plant newplant = createPlantWithParms(planttype, currentDate,
                                position, currentDate, description);

                        if (newplant != null) {
                            Intent intent = new Intent(mcontext, ShowPlantActivity.class);
                            intent.putExtra("plant", newplant);
                            mActivity.startActivity(intent);

                            mActivity.finish();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

        } catch (Exception e) {
            progDailog.dismiss();
            Toast.makeText(mcontext, "No Results!", Toast.LENGTH_SHORT).show();
        }
        progDailog.dismiss();
    }

    private Plant createPlantWithParms(String type, String purchdate,
                                       int position, String waterdate, String desc) {
        Plant curpla = new Plant();

        try {
            curpla.setWiki_url(urls.get(position).toString());
            curpla.setDescription(descs.get(position).toString());
            curpla.setType(type);
            curpla.setPurchase_date(purchdate);
            curpla.setLast_watered(waterdate);
            curpla.setDescription(desc);
            return curpla;
        } catch (Exception e) {
            Log.e(TAG, "createPlantWithParms: failed upon sets");
        }
        return null;
    }

    private void onProgressInitiate () {
        progDailog = new ProgressDialog(mActivity);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }
}
