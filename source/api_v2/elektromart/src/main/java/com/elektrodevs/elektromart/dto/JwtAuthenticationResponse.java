package com.elektrodevs.elektromart.dto;

import com.elektrodevs.elektromart.domain.User;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtAuthenticationResponse {
    String token;
    User user;
}