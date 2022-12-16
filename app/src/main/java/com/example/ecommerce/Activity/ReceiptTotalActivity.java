package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.ecommerce.Adapter.CartsAdapter;
import com.example.ecommerce.Adapter.CartsAdapter1;
import com.example.ecommerce.Model.Carts;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceiptTotalActivity extends AppCompatActivity {


    //Initialize Variables
    private RecyclerView CartsRecyclerView;
    private LinearLayout CartsNOData;
    private Button TotalBtn;

    private ArrayList<Carts> list;
    private CartsAdapter1 adapter;

    String Total,ReceiptNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_total);

//        SharedPreferences prefs1 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
//        Total=prefs1.getString("Total","none");

        SharedPreferences prefs2 = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        ReceiptNumber=prefs2.getString("ReceiptNumber","none");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Receipt Number : "+ReceiptNumber);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        //Assign Variables
        CartsRecyclerView = findViewById(R.id.CartsRecyclerView);
        CartsNOData = findViewById(R.id.CartsNOData);
        TotalBtn = findViewById(R.id.TotalBtn);

        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(ReceiptTotalActivity.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        CartsRecyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new CartsAdapter1(ReceiptTotalActivity.this,list);
        CartsRecyclerView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Carts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer total = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Carts data = dataSnapshot.getValue(Carts.class);
                    if (data!=null){
                        Integer cost = Integer.valueOf(data.getPrice());
                        total = total+cost;
                        String Total = total.toString();

                        TotalBtn.setText("Total Amount : "+Total);
                    }else {
                        Integer cost = Integer.valueOf(data.getTotal());
                        total = total+cost;
                        String Total = total.toString();

                        TotalBtn.setText("Total Amount : "+Total);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        getCarts();

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
                }else {
                    CartsNOData.setVisibility(View.GONE);
                    CartsRecyclerView.setVisibility(View.VISIBLE);

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