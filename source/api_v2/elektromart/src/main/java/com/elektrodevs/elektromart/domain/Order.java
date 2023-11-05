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
    private ShippingStatus shippingStatus;
    private String shippingAddress;
    private Long shippingId;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    public enum ShippingStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }

}
