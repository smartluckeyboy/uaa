package com.hcl.cloud.uaa.service;

import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.bean.User;

public interface ILoginService {
    String login(String username, String password);
    
    User saveUser(User user);

    boolean logout(String token);

    Boolean isValidToken(String token);

    String createNewToken(String token);
    
    }
