package gohv.github.com.babyoffers.model;

import android.graphics.Bitmap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgy on 7/27/2016.
 */
public class Offer implements Serializable {


    private double oldPrice;
    private double newPrice;
    private String shopName;
    private String productPhoto;
    private String productName;
    private String category;
    private String brand;
    private String linkToItem;
    private Bitmap productImage;

    @Override
    public String toString() {
        return "Offer{" +
                "oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", shopName='" + shopName + '\'' +
                ", productPhoto='" + productPhoto + '\'' +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", linkToItem='" + linkToItem + '\'' +
                ", productImage=" + productImage +
                '}';
    }

    public String getLinkToItem() {
        return linkToItem;
    }

    public void setLinkToItem(String linkToItem) {
        this.linkToItem = linkToItem;
    }

    public void setProductImage(Bitmap productImage) {
        this.productImage = productImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Bitmap getProductImage() {
        return productImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Offer() {

    }

    public long getDiscount() {
        double p = (1 - (newPrice / oldPrice)) * 100;

        return Math.round(p);
    }


    public String getDiscountColor(long percent) {
        if (percent < 10) return "#ffe0b2";
        if (percent < 20) return "#FFCC80";
        if (percent < 30) return "#ffb74d";
        if (percent < 40) return "#ffa726";
        if (percent < 50) return "#FF9800";
        if (percent < 60) return "#fb8c00";
        if (percent < 70) return "#f57c00";
        if (percent < 80) return "#Ef6c00";
        if (percent < 90) return "#e65100";

        return "#ffa726";


    }
}
