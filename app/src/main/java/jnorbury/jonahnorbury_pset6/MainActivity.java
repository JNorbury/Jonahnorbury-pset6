package jnorbury.jonahnorbury_pset6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonah on 09-Dec-16.
 *
 * Mainactivity shows the user all its plants in the firebase database,
 * allows users to make plants favourites and check water dates, purchase dates.
 *
 */

public class MainActivity extends AppCompatActivity {

    private ListView plv;
    private ArrayAdapter aa;
    private PlantList plants;

    private static final String TAG = "Mainactivity";

    private FirebaseAuth mAuth;
    private FirebaseUser cUser;
    private DatabaseReference myRef;

    private SharedPreferences plantprefs;
    private SharedPreferences.Editor prefEditor;
    private ProgressDialog currentprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        plantprefs = getSharedPreferences("favs", 0);
        prefEditor = plantprefs.edit();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogInitiator();
        currentprogress.show();

        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference();
        plants = new PlantList();

        plv = (ListView) findViewById(R.id.plantsLV);

        // check login state, if invalid go back to login screen
        if (cUser == null) {
            Toast.makeText(this, "Not logged in.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginFireActivity.class);
            startActivity(intent);
            finish();
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                plants = new PlantList();
                try {
                    // add all plants from username key in firebase database to list
                    for (DataSnapshot plantSnapShot: dataSnapshot.child("users").
                            child(cUser.getUid()).getChildren()) {
                        Plant tempPlant = plantSnapShot.getValue(Plant.class);
                        plants.add(tempPlant);
                    }

                    // initiate adapter with correct layout format, set adapter to listview
                    aa = new PlantAdapter(MainActivity.this,
                            R.layout.plant_list_item_layout, plants);
                    plv.setAdapter(aa);
                    aa.notifyDataSetChanged();
                    currentprogress.dismiss();
                    listenToClick();

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

    }

    // simple spinning circle during loading, for better UI experience
    public void dialogInitiator() {
        currentprogress = new ProgressDialog(this);

        currentprogress.setMessage("Loading...");
        currentprogress.setIndeterminate(false);
        currentprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        currentprogress.setCancelable(true);
    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(this, SearchPlantActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void listenToClick() {
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

    // checkbox function for adding favourite to both shareprefs and database (synchronised)
    public void addFav(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        Plant checkPlant = (Plant) (plv.getItemAtPosition(plv.getPositionForView(view)));
        String plantid  = checkPlant.get_id();

        switch (view.getId()) {
            case R.id.checkboxfav:
                // sets bool favourite depending on state of checkbox
                checkPlant.setFavourite(checked);
                myRef.child("users").child(cUser.getUid()).child(plantid).
                        child("favourite").setValue(checked);

                // puts plant in sharedprefs with its id as key
                if (!checked) {
                    prefEditor.remove(checkPlant.get_id());
                } else {
                    prefEditor.putBoolean(checkPlant.get_id(), true);
                }
                prefEditor.commit();
        }
    }
}
