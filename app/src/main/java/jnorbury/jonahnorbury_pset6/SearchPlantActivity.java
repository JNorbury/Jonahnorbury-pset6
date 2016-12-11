package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchPlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_plant);
    }

    public void searchPlant(View view) {
        EditText ET = (EditText) findViewById(R.id.plantqueryET);
        String plant_name = ET.getText().toString();
        PlantAsyncTask pat = new PlantAsyncTask(getBaseContext(), this);
        pat.execute(plant_name);
    }
}
