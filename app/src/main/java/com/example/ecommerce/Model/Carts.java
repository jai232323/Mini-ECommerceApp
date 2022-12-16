package com.example.ecommerce.Model;

public class Carts {
    private String Name,U_Id,Price,Qty,Total;

    private int Image;

    public Carts() {
    }

    public Carts(String name, String u_Id, String price, String qty, String total, int image) {
        Name = name;
        U_Id = u_Id;
        Price = price;
        Qty = qty;
        Total = total;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getU_Id() {
        return U_Id;
    }

    public void setU_Id(String u_Id) {
        U_Id = u_Id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
