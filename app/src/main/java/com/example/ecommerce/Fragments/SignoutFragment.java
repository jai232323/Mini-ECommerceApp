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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Activity.MainActivity;
import com.example.ecommerce.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignoutFragment extends Fragment {


    ImageView Image;
    TextView Name,Email;
    Button Logout;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient googleSignInClient;

    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signout, container, false);

        Image = view.findViewById(R.id.Image);
        Name = view.findViewById(R.id.Name);
        Email = view.findViewById(R.id.Email);
        Logout = view.findViewById(R.id.Logout);


        SharedPreferences prefs1 = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        id=prefs1.getString("id","none");

        //Initialize variable
        firebaseAuth=FirebaseAuth.getInstance();
        //Initialize firebase user
        firebaseUser=firebaseAuth.getCurrentUser();

        googleSignInClient= GoogleSignIn.getClient(getContext(),
                GoogleSignInOptions.DEFAULT_SIGN_IN);

        GoogleSignInAccount signInAccount1 = GoogleSignIn.getLastSignedInAccount(getContext());
        if (signInAccount1!=null){
            Name.setText(signInAccount1.getDisplayName());
            Email.setText(signInAccount1.getEmail());
            Glide.with(getContext())
                    .load(firebaseUser.getPhotoUrl())
                    .into(Image);

        }




        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            //When task is successful
                            //Sign out form firebase


                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GmailUsers")
                                    .child(id);

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    reference.removeValue();
                                    firebaseAuth.signOut();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);

                                    Toast.makeText(getContext(),
                                            "Logout Successfully",Toast.LENGTH_SHORT).show();
                                    //Finish activity
                                    getActivity().finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                    }
                });
            }
        });




        return view;
    }
}