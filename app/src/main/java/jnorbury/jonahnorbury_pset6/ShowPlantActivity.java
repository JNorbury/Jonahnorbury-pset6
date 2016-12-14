package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ShowPlantActivity extends AppCompatActivity {
    private ImageView plantview;
    private Plant mplant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        mplant = (Plant) getIntent().getSerializableExtra("plant");

        plantview = (ImageView) findViewById(R.id.plantIV);

        plantview.getLayoutParams().height = 40;
        plantview.getLayoutParams().width = 40;

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

//        ((EditText) this.findViewById(R.id.nicknameET)).setText(plant.getNick_name());
//
//        ((EditText) this.findViewById(R.id.purchaseET)).setText(plant.getPurchase_date());
//        ((EditText) this.findViewById(R.id.lastwaterET)).setText(plant.getLast_watered());

    }

    public void onClickSavePlant(View view) {
        String plantType = ((TextView) findViewById(R.id.plantnameTV)).getText().toString();
        String nickname = ((EditText) findViewById(R.id.nicknameET)).getText().toString();
        String purchase = ((EditText) findViewById(R.id.purchaseET)).getText().toString();
        String waterDate = ((EditText) findViewById(R.id.lastwaterET)).getText().toString();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy",
                Locale.getDefault()).format(new Date());

        if (plantType.matches("")) {
            plantType = "empty";
        }
        if (nickname.matches("")) {
            nickname = "no nickname :(";
        }
        if (!purchase.matches("")) {
            purchase = currentDate;
        } else {

        }
        if (!waterDate.matches("")) {
            waterDate = currentDate;
        }

        mplant.setType(plantType);
        mplant.setNick_name(nickname);
        mplant.setLast_watered(waterDate);
        mplant.setPurchase_date(purchase);
        finish();
    }
}
