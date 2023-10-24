package com.elektromart.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private String description;
    private String vendor;
    private String urlSlug;
    private String sku;
    private float price;
    private int discountPercent;
    private String isFeatured;
    private String isDelete;
}
