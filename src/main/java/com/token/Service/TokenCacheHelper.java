package com.token.Service;

import com.token.Model.User;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenCacheHelper {

    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public User getUserDetails(String userName) {
        User user = new User(1, "Mohit", "1234", base64Encoder.encodeToString(userName.getBytes()), base64Encoder.encodeToString("1234".getBytes()), base64Encoder.encodeToString("Mohit".getBytes()), false);
        System.out.println("get user details (): " + user);
        return user;
    }

    public void removeToken(User userDetails) {
        System.out.println("remove Token (): " + userDetails);
    }

    public void cacheToken(User userDetails) {
        System.out.println("cache Token (): " + userDetails);
    }
}
