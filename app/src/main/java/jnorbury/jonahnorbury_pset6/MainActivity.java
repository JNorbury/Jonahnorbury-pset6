package jnorbury.jonahnorbury_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView plv;
    private ArrayAdapter aa;
    private PlantList plants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plants = new PlantList();
        plants.add(new Plant("Bonsai", "12-02-2015", "https://en.wikipedia.org/wiki/Bonsai", "12-01-2015", "fat, chubby piece of shit."));
        plants.add(new Plant("Larch", "12-01-2015", "https://en.wikipedia.org/wiki/Larch", "12-11-1612", "fat, chubby piece of shit."));
        plants.add(new Plant("Beech", "12-02-2001", "https://en.wikipedia.org/wiki/Beech", "16-12-2015", "fat, chubby piece of shit."));
        plants.add(new Plant("Rose", "12-02-1925", "https://en.wikipedia.org/wiki/Rose", "16-12-1612", "fat, chubby piece of shit."));

        plv = (ListView) findViewById(R.id.plantsLV);
        aa = new PlantAdapter(this, R.layout.plant_list_item_layout, plants);
        plv.setAdapter(aa);
        aa.notifyDataSetChanged();

        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Plant current = (Plant) aa.getItem(position);
                Intent intent = new Intent(getBaseContext(), ShowPlantActivity.class);
                intent.putExtra("plant", current);
                startActivity(intent);
            }
        });
    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchPlantActivity.class);
        startActivity(intent);
    }
}
