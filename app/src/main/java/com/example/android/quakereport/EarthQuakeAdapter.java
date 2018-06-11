package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class EarthQuakeAdapter extends ArrayAdapter<EqData> {


    public EarthQuakeAdapter(@NonNull Context context,  @NonNull List<EqData> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_listview,parent,false);

        EqData currentData = (EqData) getItem(position);


        TextView magnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        magnitude.setText(currentData.getMagnitude());

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentData.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);


        TextView place1 = (TextView) listItemView.findViewById(R.id.firstPlace);
        place1.setText(currentData.getPlace1());

        TextView place2 = (TextView) listItemView.findViewById(R.id.secondPlace);
        place2.setText(currentData.getPlace2());

        TextView date = (TextView) listItemView.findViewById(R.id.onDate);
        date.setText(currentData.getDate());

        TextView time = (TextView) listItemView.findViewById(R.id.onTime);
        time.setText(currentData.getTime());


        return listItemView;
    }

    private int getMagnitudeColor(String mag){
        int mg = (int) Math.floor(Float.parseFloat(mag));
        int magnitudeColorResourceId;

        switch(mg){
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;

        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
