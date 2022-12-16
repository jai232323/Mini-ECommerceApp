package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerce.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap nMap;
    ArrayList<LatLng> arrayList = new ArrayList<LatLng>();
    LatLng sydney=new LatLng(34,151);
    LatLng Tamworth=new LatLng(31.083332,150.916672);
    LatLng Newcastle=new LatLng(32.916668,151.750000);
    LatLng Brisbane=new LatLng(27.470125,153.021072);
    LatLng Dubbo=new LatLng(32.256943,148.601105);
    // create another arraylist for names for markers
    ArrayList<String>title=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // we will add items to our array list.
        arrayList.add(sydney);
        arrayList.add(Tamworth);
        arrayList.add(Newcastle);
        arrayList.add(Brisbane);
        arrayList.add(Dubbo);
// now we will add title for marker in title arraylist
        title.add("Sydney");
        title.add("Tamworth");
        title.add("Newcastle");
        title.add("Brisbane");
        title.add("Dubbo");
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        nMap = googleMap;
// now we will add markers to locations
        for(int i=0;i<arrayList.size();i++) {
            // this loop is for adding markers
            for (int j = 01; j < title.size(); j++) {
                // this loop for setting title of marker
                nMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(j))));
            }
            nMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }
        nMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {

                String MarkerTitle = marker.getTitle();

                String n,m,e,f;
                n=getIntent().getStringExtra("Name");
                m=getIntent().getStringExtra("MobileNumber");
                e=getIntent().getStringExtra("Email");
                f=getIntent().getStringExtra("FlatNumber");

                Intent intent = new Intent(new Intent(GoogleMapActivity.this,getAddressActivity.class));


                intent.putExtra("Name",n);
                intent.putExtra("MobileNumber",m);
                intent.putExtra("Email",e);
                intent.putExtra("FlatNumber",f);
                intent.putExtra("MarkerTitle",MarkerTitle);

                startActivity(intent);
                return false;
            }
        });
// now we will add on click listner for marker on mapa


//        LatLng sydney = new LatLng(-33.852, 151.211);
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney"));
//        // [START_EXCLUDE silent]
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}