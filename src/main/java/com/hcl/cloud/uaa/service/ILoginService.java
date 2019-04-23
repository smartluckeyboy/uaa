package com.hcl.cloud.uaa.service;

import com.hcl.cloud.uaa.bean.User;

public interface ILoginService {
	
    String login(String username, String password);
    
    boolean logout(String token);

    Boolean isValidToken(String token);

    String createNewToken(String token);
    
    }
