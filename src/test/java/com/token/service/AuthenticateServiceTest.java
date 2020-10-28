package com.token.service;

import com.token.Model.User;
import com.token.Service.AuthenticateService;
import com.token.Service.TokenCacheHelper;
import com.token.Service.TokenService;
import com.token.TokenDemoApplication;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TokenDemoApplication.class)
@FixMethodOrder(MethodSorters.JVM)
public class AuthenticateServiceTest {

    @InjectMocks
    private AuthenticateService authenticateService;

    @Mock
    private TokenCacheHelper tokenCacheHelper;
    @Mock
    private TokenService tokenService;

    @Mock
    TokenDemoApplication tokenDemoApplication;
    @Mock
    ReflectiveInvocationContext mockContext;

    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void generateTokenTest() {
        User user = buildUser();

        when(tokenCacheHelper.getUserDetails(anyString())).thenReturn(user);
        doNothing().when(tokenCacheHelper).removeToken(user);
        when(tokenService.refreshToken(anyString(), anyString())).thenReturn(user);
        doNothing().when(tokenCacheHelper).cacheToken(user);

        String validToken = authenticateService.authenticateUser("Mohit", "1234");

        assertEquals(validToken, user.getAccessToken());
    }

    @Test
    public void generateTokenUserNullTest() {
        User user = null;
        User realUser = buildUser();

        when(tokenCacheHelper.getUserDetails(anyString())).thenReturn(user);
        doNothing().when(tokenCacheHelper).removeToken(user);
        when(tokenService.refreshToken(anyString(), anyString())).thenReturn(user);
        doNothing().when(tokenCacheHelper).cacheToken(user);
        when(tokenService.generateToken(anyString(), anyString())).thenReturn(realUser);

        String validToken = authenticateService.authenticateUser("Mohit", "1234");

        assertEquals(validToken, realUser.getAccessToken());
    }

    @Test
    public void expiredTokenGenerateTest() {
        User user = buildUser();
        user.setExpiredToken(true);

        when(tokenCacheHelper.getUserDetails(anyString())).thenReturn(user);
        doNothing().when(tokenCacheHelper).removeToken(user);
        when(tokenService.refreshToken(anyString(), anyString())).thenReturn(user);
        doNothing().when(tokenCacheHelper).cacheToken(user);
        when(tokenService.generateToken(anyString(), anyString())).thenReturn(user);

        String validToken = authenticateService.authenticateUser("Mohit", "1234");

        assertEquals(validToken, user.getAccessToken());
    }

    private User buildUser() {
        return new User(1, "Mohit", "1234", base64Encoder.encodeToString("Mohit".getBytes()), base64Encoder.encodeToString("1234".getBytes()), base64Encoder.encodeToString("Mohit".getBytes()), false);
    }

    @Test
    public void privateSchedulerTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method testMethod = TokenDemoApplication.class.getDeclaredMethod("cronJobSch",TokenDemoApplication.class);
        testMethod.setAccessible(true);
        testMethod.invoke(tokenDemoApplication,null);
        //when(mockContext.getMethod()).thenReturn(testMethod);
    }
}