package com.sentia.shopdemo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Products")
public class Product {

    @Id
    private String Id;

    private String label;

    private String type;

    private String color;

    private String hex;

    private Double price;

    private String currency;

    private String src;

    private String code;

    public Product() {
    }

    public Product(String id, String label, String type, String color, String hex,
                   Double price, String currency, String src, String code) {
        this.Id = id;
        this.label = label;
        this.type = type;
        this.color = color;
        this.hex = hex;
        this.price = price;
        this.currency = currency;
        this.src = src;
        this.code = code;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id='" + Id + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", hex='" + hex + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", src='" + src + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
