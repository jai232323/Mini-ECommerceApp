package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Carts;
import com.example.ecommerce.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CartsAdapter1 extends RecyclerView.Adapter<CartsAdapter1.ViewHolder> {


    private Context context;
    private ArrayList<Carts> items;

    public CartsAdapter1(Context context, ArrayList<Carts> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_adapter1,parent,false);

        return new CartsAdapter1.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carts data = items.get(position);

        holder.Name.setText(data.getName());
        holder.Price.setText(data.getPrice());
        holder.Qty.setText(data.getQty());
        holder.Total.setText(data.getTotal());
        Glide.with(context).load(data.getImage()).into(holder.Image);



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{



        private ImageView Image;
        private TextView Name,Price,Qty,Total;

        TextView Pricetxt,Plus;
        MaterialButton Minus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            Image = itemView.findViewById(R.id.Image);
            Name = itemView.findViewById(R.id.Name);
            Price = itemView.findViewById(R.id.Price);

            Qty = itemView.findViewById(R.id.Qty);
            Total = itemView.findViewById(R.id.Total);

        }
    }
}
