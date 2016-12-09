package jnorbury.jonahnorbury_pset6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jonah on 08-Dec-16.
 */

public class PlantAdapter extends ArrayAdapter<Plant>{
    private PlantList plants;
    public PlantAdapter(Context context, int resource, PlantList plants) {
        super(context, resource, plants);
        this.plants = plants;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.plant_list_item_layout, null);
        }
        Plant plantitem = plants.get(position);
        if (plantitem != null) {
            TextView name = (TextView) convertView.findViewById(R.id.nameTV);
            TextView purchdate = (TextView) convertView.findViewById(R.id.purchasedateTV);
            if (name != null && purchdate != null) {
                name.setText(plantitem.getName());
                purchdate.setText(plantitem.getPurchase_date());
            } else {
                return null;
            }
        }
        return convertView;
    }


}