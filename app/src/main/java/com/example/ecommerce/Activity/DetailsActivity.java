package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {


    ImageView Image;
    TextView Name,Price;
    Button Add_to_cart;

    String Name1,Price1;

    private DatabaseReference reference;

    int image;
    String U_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        SharedPreferences prefs1 = getSharedPreferences("PREFS", MODE_PRIVATE);
        U_Id=prefs1.getString("U_Id","none");


        Image = findViewById(R.id.Image);
        Name =findViewById(R.id.Name);
        Price = findViewById(R.id.Price);
        Add_to_cart = findViewById(R.id.Add_to_cart);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            image = bundle.getInt("Image");
            Image.setImageResource(image);
        }

        Name1 = getIntent().getStringExtra("Name");
        Name.setText(Name1);

        Price1 = getIntent().getStringExtra("Price");
        Price.setText(Price1);

        Add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("Carts");

               // String U_Id = reference.push().getKey();

                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("Image",image);
                hashMap.put("Name",Name1);
                hashMap.put("Price",Price1);
                hashMap.put("U_Id",U_Id);

           //     Toast.makeText(DetailsActivity.this, Name1, Toast.LENGTH_SHORT).show();
                reference.child(U_Id).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(DetailsActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();

                        Add_to_cart.setText(R.string.GotoCart);
                        Add_to_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(DetailsActivity.this,CartActivity.class);

                                SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                                editor.putString("U_Id", U_Id);
                                editor.apply();

                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DetailsActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}