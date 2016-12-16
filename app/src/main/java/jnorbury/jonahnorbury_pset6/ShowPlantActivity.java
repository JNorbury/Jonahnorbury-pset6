package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
        String nickname = ((EditText) findViewById(R.id.nicknameET)).getText().toString();
        String purchase = ((EditText) findViewById(R.id.purchaseET)).getText().toString();
        String waterDate = ((EditText) findViewById(R.id.lastwaterET)).getText().toString();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(new Date());

        // figure out plant details from views
        if (plantType.matches("")) {
            plantType = "empty";
        }
        if (nickname.matches("")) {
            nickname = "no nickname :(";
        }

        // plants without a given purchase/last water date are defaulted to today.
        if (purchase.matches("")) {
            if (mplant.getPurchase_date().matches("")) {
                purchase = currentDate;
            } else { // unless they already have one.
                waterDate = mplant.getPurchase_date();
            }
        }
        if (waterDate.matches("")) {
            if (mplant.getLast_watered().matches("")) {
                waterDate = currentDate;
            } else {
                waterDate = mplant.getLast_watered();
            }
        }

        mplant.setType(plantType);
        mplant.setNick_name(nickname);
        mplant.setLast_watered(waterDate);
        mplant.setPurchase_date(purchase);

        DatabaseReference pushRef = myRef.child("users").child(cUser.getUid()).push();

        String plant_id = mplant.get_id();

        // if plant doesn't exist, create a new entry in database and mark plant with id
        if (plant_id == null) {
            mplant.set_id((pushRef.getKey()));
            pushRef.setValue(mplant);
        } else { // else overwrite old plant
            myRef.child("users").child(cUser.getUid()).child(plant_id).removeValue();
            myRef.child("users").child(cUser.getUid()).child(plant_id).push().setValue(mplant);
        }
        finish();
    }
}
