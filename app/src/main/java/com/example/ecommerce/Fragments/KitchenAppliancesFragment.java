package com.example.ecommerce.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Activity.DetailsActivity;
import com.example.ecommerce.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class KitchenAppliancesFragment extends Fragment {


    LinearLayout item1,item2,item3,item4,item5,item6;
    ImageView AirFryer_Img,blender_img,CoffeeMaker_img,Dishwasher_Img,Microwave_Img,HandMaker_Img;
    TextView AirFryer_Name,AirFryer_Price,blender_Name,blender_Price,CoffeeMaker_name,CoffeeMaker_price,
            Dishwasher_Name,Dishwasher_Price,Microwave_Name,Microwave_Price,HandMaker_Name,HandMaker_Price;
    DatabaseReference reference;


    Button Location;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    public void onStart() {
        super.onStart();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }else {
            getLocation();
        }
        fusedLocationProviderClient.getLastLocation().
                addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                    @Override
                    public void onComplete(@NonNull Task<android.location.Location> task) {

                        //Initialize location
                        Location location = task.getResult();
                        if (location != null) {
                            //Initialize geoCoder
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            //Initialize address list
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1);
                            //    Toast.makeText(getContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                                Location.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kitchen_appliances, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("Items");

        Location = view.findViewById(R.id.Location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //When Permission granted
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.
                            ACCESS_FINE_LOCATION}, 44);
                    getLocation();
                    // Toast.makeText(getContext(), "Something Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //AirFryer
        item1 = view.findViewById(R.id.item1);
        AirFryer_Img = view.findViewById(R.id.AirFryer_Img);
        AirFryer_Name = view.findViewById(R.id.AirFryer_Name);
        AirFryer_Price = view.findViewById(R.id.AirFryer_Price);

        //blender
        item2 = view.findViewById(R.id.item2);
        blender_img = view.findViewById(R.id.blender_img);
        blender_Name = view.findViewById(R.id.blender_Name);
        blender_Price = view.findViewById(R.id.blender_Price);

        //CoffeeMaker
        item3 = view.findViewById(R.id.item3);
        CoffeeMaker_img = view.findViewById(R.id.CoffeeMaker_img);
        CoffeeMaker_name = view.findViewById(R.id.CoffeeMaker_name);
        CoffeeMaker_price = view.findViewById(R.id.CoffeeMaker_price);

        //Dishwasher
        item4 = view.findViewById(R.id.item4);
        Dishwasher_Img = view.findViewById(R.id.Dishwasher_Img);
        Dishwasher_Name = view.findViewById(R.id.Dishwasher_Name);
        Dishwasher_Price = view.findViewById(R.id.Dishwasher_Price);

        //RiceCooker
        item5 = view.findViewById(R.id.item5);
        Microwave_Img = view.findViewById(R.id.Microwave_Img);
        Microwave_Name = view.findViewById(R.id.Microwave_Name);
        Microwave_Price = view.findViewById(R.id.Microwave_Price);

        //Toaster
        item6 = view.findViewById(R.id.item6);
        HandMaker_Img = view.findViewById(R.id.HandMaker_Img);
        HandMaker_Name = view.findViewById(R.id.HandMaker_Name);
        HandMaker_Price = view.findViewById(R.id.HandMaker_Price);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.air_fryer_1299;
                String Name = AirFryer_Name.getText().toString();
                String Price = AirFryer_Price.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",Image);
                hashMap.put("Name","Air Fryer");
                hashMap.put("Price","1299");
                hashMap.put("U_Id",U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image",R.drawable.air_fryer_1299);
                intent.putExtra("Name","Air Fryer");
                intent.putExtra("Price","1299");
                intent.putExtra("U_Id",U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id",U_Id );
                editor.apply();

                startActivity(intent);

            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.blender_2490;
                String Name = blender_Name.getText().toString();
                String Price = blender_Price.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",Image);
                hashMap.put("Name","Blender");
                hashMap.put("Price","2490");
                hashMap.put("U_Id",U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image",R.drawable.blender_2490);
                intent.putExtra("Name","Blender");
                intent.putExtra("Price","2490");
                intent.putExtra("U_Id",U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id",U_Id );
                editor.apply();

                startActivity(intent);

            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.coffee_maker_2449;
                String Name = CoffeeMaker_name.getText().toString();
                String Price = CoffeeMaker_price.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",Image);
                hashMap.put("Name","Coffee Maker");
                hashMap.put("Price","2449");
                hashMap.put("U_Id",U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image",R.drawable.coffee_maker_2449);
                intent.putExtra("Name","Coffee Maker");
                intent.putExtra("Price","2449");
                intent.putExtra("U_Id",U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id",U_Id );
                editor.apply();

                startActivity(intent);

            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.dishwasher_1999;
                String Name = Dishwasher_Name.getText().toString();
                String Price = Dishwasher_Price.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",Image);
                hashMap.put("Name","Dishwasher");
                hashMap.put("Price","1999");
                hashMap.put("U_Id",U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image",R.drawable.dishwasher_1999);
                intent.putExtra("Name","Dishwasher");
                intent.putExtra("Price","1999");
                intent.putExtra("U_Id",U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id",U_Id );
                editor.apply();

                startActivity(intent);

            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.microwave_7299;
                String Name = Microwave_Name.getText().toString();
                String Price = Microwave_Price.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",Image);
                hashMap.put("Name","Microwave");
                hashMap.put("Price","7299");
                hashMap.put("U_Id",U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image",R.drawable.microwave_7299);
                intent.putExtra("Name","Microwave");
                intent.putExtra("Price","7299");
                intent.putExtra("U_Id",U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id",U_Id );
                editor.apply();

                startActivity(intent);

            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.hand_blender_383;
                String Name = HandMaker_Name.getText().toString();
                String Price = HandMaker_Price.getText().toString();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",Image);
                hashMap.put("Name","Hand Blender");
                hashMap.put("Price","383");
                hashMap.put("U_Id",U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image",R.drawable.hand_blender_383);
                intent.putExtra("Name","Hand Blender");
                intent.putExtra("Price","383");
                intent.putExtra("U_Id",U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id",U_Id );
                editor.apply();

                startActivity(intent);

            }
        });
        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().
                addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                    @Override
                    public void onComplete(@NonNull Task<android.location.Location> task) {

                        //Initialize location
                        Location location = task.getResult();
                        if (location != null) {
                            //Initialize geoCoder
                            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                            //Initialize address list
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1);
                               // Toast.makeText(getContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                                Location.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
}