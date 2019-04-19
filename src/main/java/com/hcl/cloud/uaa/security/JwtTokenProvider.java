package com.hcl.cloud.uaa.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.bean.MongoUserDetails;
import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.exception.CustomException;
import com.hcl.cloud.uaa.repository.JwtTokenRepository;
import com.hcl.cloud.uaa.service.ILoginService;
import com.hcl.cloud.uaa.service.ITokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
    private static final String AUTH="auth";
    private static final String AUTHORIZATION="Authorization";
    private String secretKey="secret-key";
    private long validityInMilliseconds = 60000; // 1m

    @Autowired
    private JwtTokenRepository jwtTokenRepository;
    
    @Autowired
    private ILoginService iLoginService;
    
    @Autowired
    private ITokenService iTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(User user, List<String> roles) {

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(AUTH,roles);
        JwtToken jwtToken = new JwtToken();
        
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        String token =  Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
       
        JwtToken tokenInfo = iTokenService.getInfoByEmail(user.getEmail());
        
        if (null != tokenInfo) {
        	tokenInfo.setToken(token);
        	jwtTokenRepository.save(tokenInfo);
        } else {
        	jwtToken.setToken(token);
            jwtToken.setEmail(user.getEmail());
            jwtToken.setUserId(user.getUserId());
            jwtTokenRepository.save(jwtToken);
		}
        return token;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        /*if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }*/
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
        return jwtTokenRepository.findById(token).isPresent();
    }
    //user details with out database hit
    public UserDetails getUserDetails(String token) {
        String userName =  getUsername(token);
        List<String> roleList = getRoleList(token);
        UserDetails userDetails = new MongoUserDetails(userName,roleList.toArray(new String[roleList.size()]));
        return userDetails;
    }
    public List<String> getRoleList(String token) {
        return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).
                getBody().get(AUTH);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public Authentication getAuthentication(String token) {
        //using data base: uncomment when you want to fetch data from data base
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        //from token take user value. comment below line for changing it taking from data base
        //UserDetails userDetails = getUserDetails(token);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

	public JwtTokenRepository getJwtTokenRepository() {
		return jwtTokenRepository;
	}

	public void setJwtTokenRepository(JwtTokenRepository jwtTokenRepository) {
		this.jwtTokenRepository = jwtTokenRepository;
	}

}
