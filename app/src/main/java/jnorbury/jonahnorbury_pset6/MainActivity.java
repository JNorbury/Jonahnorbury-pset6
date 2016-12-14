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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView plv;
    private ArrayAdapter aa;
    private PlantList plants;
    private static final String TAG = "Mainactivity";
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser cUser = mAuth.getCurrentUser();

        if (cUser == null) {
            Toast.makeText(this, "Not logged in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginFireActivity.class);
            startActivity(intent);
            finish();
        }

        // make a testplant
        Plant testplant = new Plant();
        testplant.setNick_name("Meurie");
        testplant.setLast_watered("12-12-2016");
        testplant.setType("Rose");
        testplant.setImg_url("http://www.w3schools.com/css/trolltunga.jpg");

        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.child("users").child(cUser.getUid()).push().setValue(testplant);

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
