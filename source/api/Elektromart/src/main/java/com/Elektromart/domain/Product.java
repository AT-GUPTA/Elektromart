package com.elektromart.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String description;
    private String vendor;
    private String urlSlug;
    private String sku;
    private float price;
    private int discountPercent;

    @JsonProperty("isFeatured")
    private boolean isFeatured;

    @JsonProperty("isDelete")
    private boolean isDelete;

    private int quantity;
}
