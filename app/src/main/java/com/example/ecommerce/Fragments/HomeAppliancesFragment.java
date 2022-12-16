package com.example.ecommerce.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.text.Html;
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


public class HomeAppliancesFragment extends Fragment {


    LinearLayout item1, item2, item3, item4, item5, item6;
    ImageView blender_img, CoffeeMaker_img, MixerGrinder_img, PressureCooker_Img, RiceCooker_Img, Toaster_Img;
    TextView blender_name, blender_price, CoffeeMaker_name, CoffeeMaker_price, MixerGrinder_name, MixerGrinder_price,
            PressureCooker_Name, PressureCooker_Price, RiceCooker_Name, RiceCooker_Price, Toaster_Name, Toaster_Price;
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
                              //  Toast.makeText(getContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
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
        View view = inflater.inflate(R.layout.fragment_home_appliances, container, false);

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


        //Blender
        item1 = view.findViewById(R.id.item1);
        blender_img = view.findViewById(R.id.blender_img);
        blender_name = view.findViewById(R.id.blender_name);
        blender_price = view.findViewById(R.id.blender_price);

        //CoffeeMaker
        item2 = view.findViewById(R.id.item2);
        CoffeeMaker_img = view.findViewById(R.id.CoffeeMaker_img);
        CoffeeMaker_name = view.findViewById(R.id.CoffeeMaker_name);
        CoffeeMaker_price = view.findViewById(R.id.CoffeeMaker_price);

        //MixerGrinder
        item3 = view.findViewById(R.id.item3);
        MixerGrinder_img = view.findViewById(R.id.MixerGrinder_img);
        MixerGrinder_name = view.findViewById(R.id.MixerGrinder_name);
        MixerGrinder_price = view.findViewById(R.id.MixerGrinder_price);

        //PressureCooker
        item4 = view.findViewById(R.id.item4);
        PressureCooker_Img = view.findViewById(R.id.PressureCooker_Img);
        PressureCooker_Name = view.findViewById(R.id.PressureCooker_Name);
        PressureCooker_Price = view.findViewById(R.id.PressureCooker_Price);

        //RiceCooker
        item5 = view.findViewById(R.id.item5);
        RiceCooker_Img = view.findViewById(R.id.RiceCooker_Img);
        RiceCooker_Name = view.findViewById(R.id.RiceCooker_Name);
        RiceCooker_Price = view.findViewById(R.id.RiceCooker_Price);

        //Toaster
        item6 = view.findViewById(R.id.item6);
        Toaster_Img = view.findViewById(R.id.Toaster_Img);
        Toaster_Name = view.findViewById(R.id.Toaster_Name);
        Toaster_Price = view.findViewById(R.id.Toaster_Price);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.blender_2490;
                String Name = blender_name.getText().toString();
                String Price = blender_price.getText().toString();



           //     int price = Integer.parseInt("2490");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Image", Image);
                hashMap.put("Name", "Blender");
                hashMap.put("Price", "2490");
                hashMap.put("U_Id", U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image", R.drawable.blender_2490);
                intent.putExtra("Name", "Blender");
                intent.putExtra("Price", "2490");
                intent.putExtra("U_Id", U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id", U_Id);
                editor.apply();

                startActivity(intent);

            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.coffee_maker_2449;
                String Name = CoffeeMaker_name.getText().toString();
                String Price = CoffeeMaker_price.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Image", Image);
                hashMap.put("Name", "Coffee Maker");
                hashMap.put("Price", "2449");
                hashMap.put("U_Id", U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image", R.drawable.coffee_maker_2449);
                intent.putExtra("Name", "Coffee Maker");
                intent.putExtra("Price", "2449");
                intent.putExtra("U_Id", U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id", U_Id);
                editor.apply();

                startActivity(intent);

            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.mixer_grinder_6899;
                String Name = MixerGrinder_name.getText().toString();
                String Price = MixerGrinder_price.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Image", Image);
                hashMap.put("Name", "Mixer Grinder");
                hashMap.put("Price", "6899");
                hashMap.put("U_Id", U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image", R.drawable.mixer_grinder_6899);
                intent.putExtra("Name", "Mixer Grinder");
                intent.putExtra("Price", "6899");
                intent.putExtra("U_Id", U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id", U_Id);
                editor.apply();

                startActivity(intent);

            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.pressure_cooker_2643;
                String Name = PressureCooker_Name.getText().toString();
                String Price = PressureCooker_Price.getText().toString();


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Image", Image);
                hashMap.put("Name", "Pressure Cooker");
                hashMap.put("Price", "2643");
                hashMap.put("U_Id", U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image", R.drawable.pressure_cooker_2643);
                intent.putExtra("Name", "Pressure Cooker");
                intent.putExtra("Price", "2643");
                intent.putExtra("U_Id", U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id", U_Id);
                editor.apply();

                startActivity(intent);

            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.rice_cooker_1799;
                String Name = RiceCooker_Name.getText().toString();
                String Price = RiceCooker_Price.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Image", Image);
                hashMap.put("Name", "Rice Cooker");
                hashMap.put("Price", "1799");
                hashMap.put("U_Id", U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image", R.drawable.rice_cooker_1799);
                intent.putExtra("Name", "Rice Cooker");
                intent.putExtra("Price", "1799");
                intent.putExtra("U_Id", U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id", U_Id);
                editor.apply();

                startActivity(intent);

            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String U_Id = reference.push().getKey().toString();

                int Image = R.drawable.toaster_332;
                String Name = Toaster_Name.getText().toString();
                String Price = Toaster_Price.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("Image", Image);
                hashMap.put("Name", "Toaster");
                hashMap.put("Price", "332");
                hashMap.put("U_Id", U_Id);

                reference.child(U_Id).setValue(hashMap);

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("Image", R.drawable.toaster_332);
                intent.putExtra("Name", "Toaster");
                intent.putExtra("Price",  "332");
                intent.putExtra("U_Id", U_Id);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS", getContext().MODE_PRIVATE).edit();
                editor.putString("U_Id", U_Id);
                editor.apply();

                startActivity(intent);

            }
        });

        return view;
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                             //   Toast.makeText(getContext(), addresses.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
                                Location.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }
}