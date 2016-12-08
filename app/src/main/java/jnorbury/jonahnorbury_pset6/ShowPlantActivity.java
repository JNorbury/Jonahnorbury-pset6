package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowPlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        Plant plant = (Plant) getIntent().getSerializableExtra("plant");

        ((TextView) this.findViewById(R.id.plantnameTV)).setText(plant.getName());
        ((TextView) this.findViewById(R.id.purchaseTV)).setText( "purchased on     : " + plant.getPurchase_date());
        ((TextView) this.findViewById(R.id.lastwaterTV)).setText("last time watered: " + plant.getLast_watered());

    }
}
