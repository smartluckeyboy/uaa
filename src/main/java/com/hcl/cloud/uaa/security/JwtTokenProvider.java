package com.hcl.cloud.uaa.security;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.hcl.cloud.constant.UaaConstant;
import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.repository.JwtTokenRepository;
import com.hcl.cloud.uaa.service.ITokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private static final String AUTH="auth";
    private static final String AUTHORIZATION="Authorization";
    private String secretKey="secret-key";

    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    
    @Autowired
    private ITokenService iTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(AUTH,user.getRole());
        JwtToken jwtToken = new JwtToken();
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + UaaConstant.TOKEN_EXP_VALIDITY);

        String token =  Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
        
        logger.debug(" Token Created Successfully");
       
        JwtToken tokenInfo = iTokenService.getInfoByEmail(user.getEmail());
        
        if (null != tokenInfo) {
        	tokenInfo.setToken(token);
        	jwtTokenRepository.save(tokenInfo);
        	logger.debug(" Existing User token details saved successfully");
        } else {
        	jwtToken.setToken(token);
            jwtToken.setEmail(user.getEmail());
            jwtToken.setUserId(user.getUserId());
            jwtTokenRepository.save(jwtToken);
            logger.debug(" New User token details saved successfully");
		}
        return token;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        if (bearerToken != null ) {
            return bearerToken;
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtException,IllegalArgumentException{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
    }
    public boolean isTokenPresentInDB (String token) {
    	boolean validToken = false;
        JwtToken jwtToken = jwtTokenRepository.findBytoken(token);
        if (null != jwtToken && token.equals(jwtToken.getToken())) {
        	validToken = true;
        }
        return validToken;
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

	public JwtTokenRepository getJwtTokenRepository() {
		return jwtTokenRepository;
	}

	public void setJwtTokenRepository(JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenRepository = jwtTokenRepository;
	}

}
