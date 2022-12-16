package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserDetailsActivity extends AppCompatActivity {


    String name,mobil_no,email,flatno,address;
    private EditText Name,Email,MobileNumber,FlatNo,Address;
    private Button UserBtn;

    TextView AddressMap;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);



        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("UserDetails");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        MobileNumber = findViewById(R.id.MobileNumber);
        FlatNo = findViewById(R.id.FlatNo);
        Address = findViewById(R.id.Address);
        UserBtn = findViewById(R.id.UserBtn);

        AddressMap = findViewById(R.id.AddressMap);


        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        UserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = Name.getText().toString();
                mobil_no = MobileNumber.getText().toString();
                email = Email.getText().toString();
                flatno = FlatNo.getText().toString();
                address = AddressMap.getText().toString();

                //Name
                if (name.isEmpty()){
                    Name.setError("Required Name");
                    Name.requestFocus();
                }else  if (name.length()<3){
                    Name.setError("Name too short");
                    Name.requestFocus();
                }else  if (name.length()>50){
                    Name.setError("Name too long");
                    Name.requestFocus();
                }
                //MobileNumber
                else  if (mobil_no.isEmpty()){
                    MobileNumber.setError("Required MobileNumber");
                    MobileNumber.requestFocus();
                }else  if (mobil_no.length()<10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }else  if (mobil_no.length()>10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }
                //Email
                else if (email.isEmpty()){
                    Email.setError("Required Email");
                    Email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Required Correct Email");
                    Email.requestFocus();
                }
                //Flat Number
                else if (flatno.isEmpty()){
                    FlatNo.setError("Required Flat Number");
                    FlatNo.requestFocus();
                }else if (address.equals("Address")){
                    Toast.makeText(UserDetailsActivity.this, "Please Click Address", Toast.LENGTH_SHORT).show();
                }
                else {
                  //  UploadUserData();
                }

            }
        });
        AddressMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = Name.getText().toString();
                mobil_no = MobileNumber.getText().toString();
                email = Email.getText().toString();
                flatno = FlatNo.getText().toString();
              //  address = Address.getText().toString();

                //Name
                if (name.isEmpty()){
                    Name.setError("Required Name");
                    Name.requestFocus();
                }else  if (name.length()<3){
                    Name.setError("Name too short");
                    Name.requestFocus();
                }else  if (name.length()>50){
                    Name.setError("Name too long");
                    Name.requestFocus();
                }
                //MobileNumber
                else  if (mobil_no.isEmpty()){
                    MobileNumber.setError("Required MobileNumber");
                    MobileNumber.requestFocus();
                }else  if (mobil_no.length()<10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }else  if (mobil_no.length()>10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }
                //Email
                else if (email.isEmpty()){
                    Email.setError("Required Email");
                    Email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Required Correct Email");
                    Email.requestFocus();
                }
                //Flat Number
                else if (flatno.isEmpty()){
                    FlatNo.setError("Required Flat Number");
                    FlatNo.requestFocus();
                }
                else {

                    Intent intent= new Intent(UserDetailsActivity.this,GoogleMapActivity.class);
                    intent.putExtra("Name",name);
                    intent.putExtra("MobileNumber",mobil_no);
                    intent.putExtra("Email",email);
                    intent.putExtra("FlatNumber",flatno);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }



}