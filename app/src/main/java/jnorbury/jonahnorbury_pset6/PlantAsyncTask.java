package jnorbury.jonahnorbury_pset6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jonah on 09-Dec-16.
 */

public class PlantAsyncTask extends AsyncTask<String, Integer, String>{

    private Context mcontext;
    private String searchterm;
    private Activity mActivity;
    private JSONArray plantjson;
    private JSONArray urls;
    private JSONArray names;

    private static final String WIKI_SEARCH_PLANT =
            "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=%s&limit=4";
//            "http://www.omdbapi.com/?s=%s&plot=long&r=json";
    public PlantAsyncTask(Context context, Activity activity) {
        mcontext = context;
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    protected String getWikiResult(String plant_name) {
        searchterm = plant_name;

        try {
            String s = String.format(WIKI_SEARCH_PLANT, URLEncoder.encode(plant_name, "utf-8"));
            URL url = new URL(s);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.connect();

            int rc = connection.getResponseCode();

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

            names = plantjson.getJSONArray(1);
            urls = plantjson.getJSONArray(2);

            int n = names.length();

            final ArrayList<String> list = new ArrayList<String>();

            for (int i = 0; i < n; i++) {
                list.add(names.get(i).toString());
//                list.add(urls.get(i).toString());
            }
//            hits = plantjson.getJSONArray("Search");
//
            final ArrayAdapter adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, list);
            plantSearchLV.setAdapter(adapter);

            plantSearchLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    try {
                        String plant = names.get(position).toString();
                        Toast.makeText(mcontext, "name = " + plant, Toast.LENGTH_SHORT).show();
                        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        Plant current = new Plant(plant, currentDate, urls.get(position).toString(), currentDate);
                        Intent intent = new Intent(mcontext, ShowPlantActivity.class);
                        intent.putExtra("plant", current);
                        mActivity.startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

        } catch (Exception e) {
            Toast.makeText(mcontext, "No Results!", Toast.LENGTH_SHORT).show();
//            Toast.makeText(.this, "No Results!", Toast.LENGTH_SHORT).show();
        }
    }
}