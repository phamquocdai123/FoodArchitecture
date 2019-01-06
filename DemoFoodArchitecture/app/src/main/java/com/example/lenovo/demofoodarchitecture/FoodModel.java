package com.example.lenovo.demofoodarchitecture;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FoodModel implements Parcelable {

    int id;
    String name;
    double price;
    String image;
    int discount;

    public FoodModel(int id, String name, double price, String image, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.discount = discount;
    }

    public FoodModel(int id, String name, double price, int discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public FoodModel() {
    }

    protected FoodModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readDouble();
        image = in.readString();
        discount = in.readInt();
    }

    public static final Creator<FoodModel> CREATOR = new Creator<FoodModel>() {
        @Override
        public FoodModel createFromParcel(Parcel in) {
            return new FoodModel(in);
        }

        @Override
        public FoodModel[] newArray(int size) {
            return new FoodModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(price);
        parcel.writeString(image);
        parcel.writeInt(discount);
    }
}
