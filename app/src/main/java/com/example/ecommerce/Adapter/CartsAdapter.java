package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Activity.CartActivity;
import com.example.ecommerce.Model.Carts;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.ViewHolder> {


    private Context context;
    private ArrayList<Carts> items;

    public CartsAdapter(Context context, ArrayList<Carts> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_adapter,parent,false);

        return new CartsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Carts data = items.get(position);

        holder.Name.setText(data.getName());
        holder.Price.setText(data.getPrice());
        Glide.with(context).load(data.getImage()).into(holder.Image);

        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure,you want to Delete");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().
                                        child("Carts").child(data.getU_Id());

                                reference1.removeValue();

                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(context, CartActivity.class);
//                        context.startActivity(intent);


                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });


        holder.UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.view_update.setVisibility(View.VISIBLE);

                holder.ProductName.setText(data.getName());
                holder.ProductPrice.setText(data.getPrice());
            }
        });

        holder.UpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String pn,pp,productQty;
                //int ;
                pn=holder.ProductName.getText().toString();
                pp=holder.ProductPrice.getText().toString();
                productQty=holder.ProductQty.getText().toString();



                String id = data.getU_Id();

                if (pn.isEmpty()){
                    holder.ProductName.setError("Empty");
                    holder.ProductName.requestFocus();
                }

                else if (pp.isEmpty()) {
                    holder.ProductPrice.setError("Empty");
                    holder.ProductPrice.requestFocus();
                }else if (productQty.isEmpty()){
                    holder.ProductQty.setError("Empty");
                    holder.ProductQty.requestFocus();
                }
                else {
                    UpdateData(id,holder.view_update,pn,pp,productQty);
                }

            }
        });
        holder.CancelProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.view_update.setVisibility(View.GONE);
            }
        });


    }



    private void UpdateData(String id,LinearLayout view_update,String pn, String pp,String productQty) {

        String U_Id;
        SharedPreferences prefs1 = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        U_Id=prefs1.getString("id","none");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Carts");


        String total = String.valueOf(Integer.parseInt(productQty) * Integer.parseInt(pp));

        String price = String.valueOf(Integer.parseInt(pp));
        String qty = String.valueOf(Integer.parseInt(productQty));

//        //get the edit text

//        //convert value into int
//        int x=Integer.parseInt(t1.getText().toString());
//        int y=Integer.parseInt(t2.getText().toString());
//
//        //sum these two numbers
//        int z=x+y;

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("Name",pn);
        hashMap.put("Price",price);
        hashMap.put("U_Id",id);
        hashMap.put("Qty",qty);
        hashMap.put("Total",total);

        reference.child(id).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Edited Successfully", Toast.LENGTH_SHORT).show();
                //context.startActivity(new Intent(String.valueOf(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)));
                view_update.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView Image;
        private TextView Name,Price;
        private LinearLayout UpdateBtn,DeleteBtn;
        private LinearLayout view_update;
        private EditText ProductName,ProductPrice,ProductQty;
        private Button UpdateProduct,CancelProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Image = itemView.findViewById(R.id.Image);
            Name = itemView.findViewById(R.id.Name);
            Price = itemView.findViewById(R.id.Price);

            UpdateBtn = itemView.findViewById(R.id.UpdateBtn);
            DeleteBtn = itemView.findViewById(R.id.DeleteBtn);

            view_update = itemView.findViewById(R.id.view_update);
            ProductName = itemView.findViewById(R.id.ProductName);
            ProductPrice = itemView.findViewById(R.id.ProductPrice);
            ProductQty = itemView.findViewById(R.id.ProductQty);

            UpdateProduct = itemView.findViewById(R.id.UpdateProduct);
            CancelProduct = itemView.findViewById(R.id.CancelProduct);
        }
    }
}
