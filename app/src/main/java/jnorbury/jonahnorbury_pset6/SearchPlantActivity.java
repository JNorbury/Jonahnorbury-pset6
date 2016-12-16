package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by jonah on 09-Dec-16.
 *
 * Searchplantactivity utilises the LoadPlantFactsAsyncTask to query wikipedia for pages,
 * pages are non specific to plants but can all be used (for lack of better plant database api)
 * Users can then tap on items to view the full detail page.
 *
 */

public class SearchPlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);
    }

    // upon search of an item, find results for query term and load into list.
    public void searchPlant(View view) {
        EditText ET = (EditText) findViewById(R.id.plantqueryET);
        String plant_name = ET.getText().toString();
        LoadPlantFactsAsyncTask pat = new LoadPlantFactsAsyncTask(getBaseContext(), this);
        pat.execute(plant_name);
    }
}
