package com.elektrodevs.elektromart.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private Long orderId;
    private Long userId;
    private String cartId;
    private Date createdDate;
    private String shippingStatus;
    private String shippingAddress;
    private Long shippingId;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    public Order(String cartId, String address, String userId) {
        this.cartId = cartId;
        this.shippingAddress = address;
        this.createdDate = new Date();
        this.shippingStatus = ShippingStatus.PENDING.name().toUpperCase();
        this.userId = userId != null ? Long.parseLong(userId) : null;
        this.paymentMethod = "Cash on delivery";
        this.shippingId = null;
    }

    public enum ShippingStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }
}
