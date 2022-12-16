package com.example.ecommerce.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerce.Activity.ReceiptTotalActivity;
import com.example.ecommerce.Model.Carts;
import com.example.ecommerce.R;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class OrderedHistroyFragment extends Fragment {


    MaterialCardView ReceiptTotalAmounts;
    TextView ReceiptNumber,TotalNumber;

//    String Total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ordered_histroy, container, false);


        ReceiptTotalAmounts = view.findViewById(R.id.ReceiptTotalAmounts);
        ReceiptNumber = view.findViewById(R.id.ReceiptNumber);
        TotalNumber = view.findViewById(R.id.TotalNumber);




//        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
//        Total=prefs1.getString("Total","none");

        final Random random = new Random();
        String RandomNumber = String.valueOf(random.nextInt(9999));

        ReceiptNumber.setText("Receipt Number : " +RandomNumber);

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

                        TotalNumber.setText("Total Amount : "+Total);
                    }else {
                        Integer cost = Integer.valueOf(data.getTotal());
                        total = total+cost;
                        String Total = total.toString();

                        TotalNumber.setText("Total Amount : "+Total);
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ReceiptTotalAmounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ReceiptTotalActivity.class);

                SharedPreferences.Editor editor = getContext().getSharedPreferences("PREFS",getContext().MODE_PRIVATE).edit();
                editor.putString("ReceiptNumber", RandomNumber);
                editor.apply();

                startActivity(intent);
            }
        });


        return view;
    }
}