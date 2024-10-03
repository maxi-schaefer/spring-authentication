package dev.max.springAuthentication.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Max
 * @since 10/3/2024
 */

@Getter
@Setter
public class LoginResponse {

    private String token;
    private long expiresIn;

}
