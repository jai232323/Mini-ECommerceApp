package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ecommerce.Adapter.CartsAdapter;
import com.example.ecommerce.Model.Carts;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {


    //Initialize Variables
    private RecyclerView CartsRecyclerView;
    private LinearLayout CartsNOData;

    private ArrayList<Carts> list;
    private CartsAdapter adapter;

    Button Processed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Carts");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //Assign Variables
        CartsRecyclerView = findViewById(R.id.CartsRecyclerView);
        CartsNOData = findViewById(R.id.CartsNOData);
        Processed = findViewById(R.id.Processed);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(CartActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        CartsRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new CartsAdapter(CartActivity.this,list);
        CartsRecyclerView.setAdapter(adapter);

        getCarts();

        Processed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Carts");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                         Integer total = 0;

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Carts data = dataSnapshot.getValue(Carts.class);
                            assert data != null;
                            Integer cost = Integer.valueOf(data.getPrice());
                            total = total+cost;
                            String Total = total.toString();

                       //     Processed.setText("Total Price : "+Total);

                            Intent intent = new Intent(CartActivity.this,UserDetailsActivity.class);


                            SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                            editor.putString("Total", Total);
                            editor.apply();

                            startActivity(intent);
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    private void getCarts() {
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Carts");
        reference1.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                if (!snapshot.exists()){
                    CartsNOData.setVisibility(View.VISIBLE);
                    CartsRecyclerView.setVisibility(View.GONE);
                    Processed.setVisibility(View.GONE);
                }else {
                    CartsNOData.setVisibility(View.GONE);
                    CartsRecyclerView.setVisibility(View.VISIBLE);
                    Processed.setVisibility(View.VISIBLE);

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Carts data = dataSnapshot.getValue(Carts.class);
                        list.add(0, data);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}