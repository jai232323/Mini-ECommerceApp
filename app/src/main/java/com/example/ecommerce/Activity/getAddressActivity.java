package com.example.ecommerce.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class getAddressActivity extends AppCompatActivity {


    TextView markerTitle;


    String name,mobil_no,email,flatno,MarkerTitle;
    String name1,mobil_no1,email1,flatno1,MarkerTitle1;
    private EditText Name,Email,MobileNumber,FlatNo;
    private Button UserBtn;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_address);

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        MobileNumber = findViewById(R.id.MobileNumber);
        FlatNo = findViewById(R.id.FlatNo);
        markerTitle=findViewById(R.id.AddressMap);
        UserBtn = findViewById(R.id.UserBtn);


        name = getIntent().getStringExtra("Name");
        mobil_no = getIntent().getStringExtra("MobileNumber");
        email = getIntent().getStringExtra("Email");
        flatno = getIntent().getStringExtra("FlatNumber");
        MarkerTitle = getIntent().getStringExtra("MarkerTitle");

        Name.setText(name);
        MobileNumber.setText(mobil_no);
        Email.setText(email);
        FlatNo.setText(flatno);
        markerTitle.setText(MarkerTitle);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        UserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = Name.getText().toString();
                mobil_no1 = MobileNumber.getText().toString();
                email1 = Email.getText().toString();
                flatno1 = FlatNo.getText().toString();
                MarkerTitle1=MarkerTitle;

                //Name
                if (name1.isEmpty()){
                    Name.setError("Required Name");
                    Name.requestFocus();
                }else  if (name1.length()<3){
                    Name.setError("Name too short");
                    Name.requestFocus();
                }else  if (name1.length()>50){
                    Name.setError("Name too long");
                    Name.requestFocus();
                }
                //MobileNumber
                else  if (mobil_no1.isEmpty()){
                    MobileNumber.setError("Required MobileNumber");
                    MobileNumber.requestFocus();
                }else  if (mobil_no1.length()<10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }else  if (mobil_no1.length()>10){
                    MobileNumber.setError("10 Numbers Only");
                    MobileNumber.requestFocus();
                }
                //Email
                else if (email1.isEmpty()){
                    Email.setError("Required Email");
                    Email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
                    Email.setError("Required Correct Email");
                    Email.requestFocus();
                }
                //Flat Number
                else if (flatno1.isEmpty()){
                    FlatNo.setError("Required Flat Number");
                    FlatNo.requestFocus();
                }
                else {
                      UploadUserData();
                }

            }
        });


    }
        private void UploadUserData() {

        String User_Uid = reference.push().getKey().toString();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Name",name1);
        hashMap.put("MobileNumber",mobil_no1);
        hashMap.put("Email",email1);
        hashMap.put("FlatNumber",flatno1);
        hashMap.put("Address",MarkerTitle1);

        reference.child(User_Uid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getAddressActivity.this,GmailAuthenticationActivity.class);
                startActivity(intent);
//                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getAddressActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}