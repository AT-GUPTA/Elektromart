package com.elektrodevs.elektromart.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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

    public enum ShippingStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }

    public Order(String cartId, String address, String userId) {
        this.cartId = cartId;
        this.shippingAddress = address;
        this.createdDate = new Date();
        this.shippingStatus = ShippingStatus.PENDING.name();
        this.userId = Long.parseLong(userId);
    }

    public Order() {}
}
