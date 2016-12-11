package jnorbury.jonahnorbury_pset6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ShowPlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_plant);
        Plant plant = (Plant) getIntent().getSerializableExtra("plant");

        ImageView plantview = (ImageView) findViewById(R.id.plantIV);

        ImageAsyncTask iat = new ImageAsyncTask();
        iat.setBmimage(plantview, plant.getImg_url());

        if (plant.getWiki_url() != null) {
//            iat.execute(plant.getWiki_url());
            iat.execute("https://scontent-amt2-1.cdninstagram.com/t51.2885-15/e35/14591932_337346603304821_5160760702041849856_n.jpg?ig_cache_key=MTQwMTYyNzg5Njc4MzQ3NzQ0NA%3D%3D.2");
        } else {
            Toast.makeText(this, "No Image for " + plant.getName(), Toast.LENGTH_SHORT).show();
        }

        plantview.getLayoutParams().height = 20;
        plantview.getLayoutParams().width = 20;

        if (plant.getName() != null) {
            ((TextView) this.findViewById(R.id.plantnameTV)).setText(plant.getName());
        }
        if (plant.getNick_name() != null) {
            ((TextView) this.findViewById(R.id.nicknameET)).setText(plant.getNick_name());
        }
        if (plant.getPurchase_date() != null) {
            ((TextView) this.findViewById(R.id.purchaseET)).setText(plant.getPurchase_date());
        }
        if (plant.getLast_watered() != null) {
            ((TextView) this.findViewById(R.id.lastwaterET)).setText(plant.getLast_watered());
        }
        if (plant.getDescription() != null) {
            ((TextView) this.findViewById(R.id.WikipediaDesTV)).setText(plant.getDescription());
        }



//        ((EditText) this.findViewById(R.id.nicknameET)).setText(plant.getNick_name());
//
//        ((EditText) this.findViewById(R.id.purchaseET)).setText(plant.getPurchase_date());
//        ((EditText) this.findViewById(R.id.lastwaterET)).setText(plant.getLast_watered());

    }
}
