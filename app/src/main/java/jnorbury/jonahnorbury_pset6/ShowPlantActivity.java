package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowPlantActivity extends AppCompatActivity {
    private ImageView plantview;
    private Plant mplant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        mplant = (Plant) getIntent().getSerializableExtra("plant");

        plantview = (ImageView) findViewById(R.id.plantIV);

        plantview.getLayoutParams().height = 20;
        plantview.getLayoutParams().width = 20;

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
        String realname = ((TextView) findViewById(R.id.plantnameTV)).getText().toString();
        String nickname = ((EditText) findViewById(R.id.nicknameET)).getText().toString();
        String purchase = ((EditText) findViewById(R.id.purchaseET)).getText().toString();
        String water = ((EditText) findViewById(R.id.lastwaterET)).getText().toString();

        if (realname.matches("")) {
            realname = "empty";
        }
        if (nickname.matches("")) {
            realname = "empty";
        }
        if (purchase.matches("")) {
            realname = "empty";
        }
        if (water.matches("")) {
            realname = "empty";
        }

        Plant newplant = new Plant(realname, purchase, mplant.getWiki_url(), water, mplant.getDescription());
        finish();
    }
}
