package com.elektromart.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long userId;
    private String username;
    private String password;
    private String email;
    private Long roleId;
    private String status;
    private boolean authorized;

}
