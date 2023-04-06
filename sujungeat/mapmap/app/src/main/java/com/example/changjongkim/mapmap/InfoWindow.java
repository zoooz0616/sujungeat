package com.example.changjongkim.mapmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class InfoWindow implements GoogleMap.InfoWindowAdapter{
    private Context context;
    public InfoWindow(Context context){
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0){
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_info_window,null);

        LatLng latLng = arg0.getPosition();
        TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
        TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        tvLat.setText(arg0.getTitle());
        tvLng.setText(arg0.getSnippet());
        return v;
    }
}