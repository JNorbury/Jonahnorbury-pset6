package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ShowPlantActivity extends AppCompatActivity {
    private ImageView plantview;
    private Plant mplant;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseUser cUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        mplant = (Plant) getIntent().getSerializableExtra("plant");

        ImgURLGetterAsyncTask iug = new ImgURLGetterAsyncTask(getBaseContext(),
                this, mplant);
        iug.execute(mplant.getType());

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        cUser = mAuth.getCurrentUser();

        if (mplant.getType() != null) {
            ((TextView) this.findViewById(R.id.plantnameTV)).setText(mplant.getType());
        }
        if (mplant.getNick_name() != null) {
            ((EditText) this.findViewById(R.id.nicknameET)).setHint(mplant.getNick_name());
        }
        if (mplant.getPurchase_date() != null) {
            ((EditText) this.findViewById(R.id.purchaseET)).setHint(mplant.getPurchase_date());
        }
        if (mplant.getLast_watered() != null) {
            ((EditText) this.findViewById(R.id.lastwaterET)).setHint(mplant.getLast_watered());
        }
        if (mplant.getDescription() != null) {
            ((TextView) this.findViewById(R.id.WikipediaDesTV)).setText(mplant.getDescription());
        }
    }

    public void onClickSavePlant(View view) {

        String plantType = ((TextView) findViewById(R.id.plantnameTV)).getText().toString();
//        String currentDate = new SimpleDateFormat("dd-MM-yyyy",
//                Locale.getDefault()).format(new Date());

        if (((EditText) findViewById(R.id.nicknameET)).getText().toString().matches("")) {
            mplant.setNick_name(((EditText) findViewById(R.id.nicknameET)).getHint().toString());
        } else {
            mplant.setNick_name(((EditText) findViewById(R.id.nicknameET)).getText().toString());
        }

        if (((EditText) findViewById(R.id.purchaseET)).getText().toString().matches("")) {
            mplant.setPurchase_date(((EditText) findViewById(R.id.purchaseET)).getHint().toString());
        } else {
            mplant.setPurchase_date(((EditText) findViewById(R.id.purchaseET)).getText().toString());
        }

        if (((EditText) findViewById(R.id.lastwaterET)).getText().toString().matches("")) {
            mplant.setLast_watered(((EditText) findViewById(R.id.lastwaterET)).getHint().toString());
        } else {
            mplant.setLast_watered(((EditText) findViewById(R.id.lastwaterET)).getText().toString());
        }

        if (plantType.matches("")) {
            mplant.setType("no type");
        }

        DatabaseReference pushRef = myRef.child("users").child(cUser.getUid()).push();

        String plant_id = mplant.get_id();

        // if plant doesn't exist, create a new entry in database and mark plant with id
        if (plant_id == null) {
            mplant.set_id((pushRef.getKey()));
            pushRef.setValue(mplant);
        } else { // else overwrite old plant
            myRef.child("users").child(cUser.getUid()).child(plant_id).setValue(mplant);
        }
        finish();
    }

    public void onClickKillPlant(View view) {
        if (mplant.get_id() != null) {
            myRef.child("users").child(cUser.getUid()).child(mplant.get_id()).removeValue();
            finish();
        } else {
            Toast.makeText(this, "You don't own this plant yet.", Toast.LENGTH_SHORT).show();
        }
    }
}
