package com.token.Service;

import com.token.Model.User;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenService {
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public User generateToken(String userName, String password) {
        System.out.println("Generate token called() for "+userName +"  ,password"+password );
        return buildUser(userName,password);
    }

    public User refreshToken(String userName, String password) {
        System.out.println("Token refreshed called() for "+userName +"  ,password"+password );
        return buildUser(userName,password);
    }
    private User buildUser(String userName,String password){
        return new User(1,"Mohit","1234",base64Encoder.encodeToString(userName.getBytes()), base64Encoder.encodeToString(password.getBytes()), base64Encoder.encodeToString("Mohit".getBytes()),false);
    }
}
