package com.hcl.cloud.uaa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.exception.CustomException;
import com.hcl.cloud.uaa.repository.JwtTokenRepository;
import com.hcl.cloud.uaa.repository.UserRepository;
import com.hcl.cloud.uaa.security.JwtTokenProvider;
import com.hcl.cloud.uaa.service.ILoginService;

@Service
public class LoginServiceImpl implements ILoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
		
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    password));
            
            User user = userRepository.findByEmail(username);
            if (user == null  || user.getRole() == null || user.getRole().isEmpty()) {
            	logger.debug(" Invalid username or password.");
                throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);               
            }
            
            String token = jwtTokenProvider.createToken(user);
            return token;

        } catch (AuthenticationException e) {
        	logger.debug(" Invalid username or password");
            throw new CustomException("Invalid username or password.", HttpStatus.UNAUTHORIZED);
        }
    }
   
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setJwtTokenRepository(JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenRepository = jwtTokenRepository;
	}

    @Override
    public boolean logout(String token) {
         jwtTokenRepository.delete(new JwtToken(token));
         return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {
        String username = jwtTokenProvider.getUsername(token);
        User user = userRepository.findByEmail(username);
        String newToken =  jwtTokenProvider.createToken(user);
        return newToken;
    }
}
