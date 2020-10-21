package com.token.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String userName;
    private String password;
    private String accessToken;
    private String userToken;
    private String sessionToken;
    private boolean isExpiredToken;
}
