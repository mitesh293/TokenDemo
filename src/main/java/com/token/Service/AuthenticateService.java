package com.token.Service;

import com.token.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateService {
    private TokenCacheHelper tokenCacheHelper;
    private TokenService tokenService;

    @Autowired
    public AuthenticateService(TokenCacheHelper tokenCacheHelper, TokenService tokenService) {
        System.out.println("AuthenticateService Constructor injected ::");
        this.tokenCacheHelper = tokenCacheHelper;
        this.tokenService = tokenService;
    }

    public String authenticateUser(String userName,String password){
        User userDetails= this.tokenCacheHelper.getUserDetails(userName);
        userDetails = validateUserDetail(userName,password,userDetails);
        return userDetails.getAccessToken();
    }

    private User validateUserDetail(String userName, String password, User userDetails) {
        if(null == userDetails){
            userDetails = tokenService.generateToken(userName,password);
        }
        else{
            if(userDetails.isExpiredToken()){
                this.tokenCacheHelper.removeToken(userDetails);
                userDetails = tokenService.refreshToken(userName,password);
                this.tokenCacheHelper.cacheToken(userDetails);
            }
        }
        return userDetails;
    }
}
