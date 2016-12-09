package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


public class ShowPlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        Plant plant = (Plant) getIntent().getSerializableExtra("plant");

        ((TextView) this.findViewById(R.id.plantnameTV)).setText(plant.getName());

        ((EditText) this.findViewById(R.id.purchaseET)).setText(plant.getPurchase_date());
        ((EditText) this.findViewById(R.id.lastwaterET)).setText(plant.getLast_watered());

    }
}
