package jnorbury.jonahnorbury_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView plv;
    private ArrayAdapter aa;
    private PlantList plants;

    private static final String TAG = "Mainactivity";

    private FirebaseAuth mAuth;
    private FirebaseUser cUser;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();

        // check login state
        if (cUser == null) {
            Toast.makeText(this, "Not logged in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginFireActivity.class);
            startActivity(intent);
            finish();
        }

        // make a testplant
//        Plant testplant = new Plant();
//        testplant.setNick_name("Meurie");
//        testplant.setLast_watered("12-12-2016");
//        testplant.setType("Rose");
//        testplant.setImg_url("http://www.w3schools.com/css/trolltunga.jpg");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                try {
                    String value = dataSnapshot.getValue(String.class);
                    Log.d(TAG, "Value is: " + value);
                } catch (Exception e){
                    Log.d(TAG, "Value is: null");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



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

    @Override
    protected void onResume() {
        super.onResume();

        loadPlants();
    }

    private void loadPlants() {
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance().getReference().getDatabase();


        plants = new PlantList();
        plv = (ListView) findViewById(R.id.plantsLV);
        aa = new PlantAdapter(this, R.layout.plant_list_item_layout, plants);
        plv.setAdapter(aa);
        aa.notifyDataSetChanged();
    }

    public void addFav(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkboxfav:
                if (checked) {

                }
        }


    }
}
