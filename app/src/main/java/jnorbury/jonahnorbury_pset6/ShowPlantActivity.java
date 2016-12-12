package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowPlantActivity extends AppCompatActivity {
    private ImageView plantview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        Plant mplant = (Plant) getIntent().getSerializableExtra("plant");

        plantview = (ImageView) findViewById(R.id.plantIV);

        plantview.getLayoutParams().height = 20;
        plantview.getLayoutParams().width = 20;

        if (mplant.getName() != null) {
            ((TextView) this.findViewById(R.id.plantnameTV)).setText(mplant.getName());
        }
        if (mplant.getNick_name() != null) {
            ((TextView) this.findViewById(R.id.nicknameET)).setText(mplant.getNick_name());
        }
        if (mplant.getPurchase_date() != null) {
            ((TextView) this.findViewById(R.id.purchaseET)).setText(mplant.getPurchase_date());
        }
        if (mplant.getLast_watered() != null) {
            ((TextView) this.findViewById(R.id.lastwaterET)).setText(mplant.getLast_watered());
        }
        if (mplant.getDescription() != null) {
            ((TextView) this.findViewById(R.id.WikipediaDesTV)).setText(mplant.getDescription());
        }



//        ((EditText) this.findViewById(R.id.nicknameET)).setText(plant.getNick_name());
//
//        ((EditText) this.findViewById(R.id.purchaseET)).setText(plant.getPurchase_date());
//        ((EditText) this.findViewById(R.id.lastwaterET)).setText(plant.getLast_watered());

    }
}
