package jnorbury.jonahnorbury_pset6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ListView plv;
    private ArrayAdapter aa;
    private PlantList plants;
    private static final String TAG = "Mainactivity";
    private DatabaseReference myRef;
//    private FirebaseDatabase myBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myRef.setValue("Hello, World");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        if (mAuth == null) {
            Toast.makeText(this, "Not logged in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginFireActivity.class);
            startActivity(intent);
            finish();
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        plants = new PlantList();

//        plants.add(new Plant("Bonsai", "12-02-2015", "https://en.wikipedia.org/wiki/Bonsai", "12-01-2015", "fat, chubby piece of shit."));
//        plants.add(new Plant("Larch", "12-01-2015", "https://en.wikipedia.org/wiki/Larch", "12-11-1612", "fat, chubby piece of shit."));
//        plants.add(new Plant("Beech", "12-02-2001", "https://en.wikipedia.org/wiki/Beech", "16-12-2015", "fat, chubby piece of shit."));
//        plants.add(new Plant("Rose", "12-02-1925", "https://en.wikipedia.org/wiki/Rose", "16-12-1612", "fat, chubby piece of shit."));

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

    // Read from the database


    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchPlantActivity.class);
        startActivity(intent);
    }
}
