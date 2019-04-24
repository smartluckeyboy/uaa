package com.hcl.cloud.uaa.security;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.cloud.uaa.exception.ErrorResponse;

import io.jsonwebtoken.JwtException;
public class JwtTokenFilter extends GenericFilterBean {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //logger.info("PreFilter: " + String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        if (token != null) {
            if (!jwtTokenProvider.isTokenPresentInDB(token)) {
            	logger.error(" Token not present in DB");
                throwError(request,response);
				return;
            }
            try {
                jwtTokenProvider.validateToken(token);
                logger.error(" Token Validated");
            } catch (JwtException | IllegalArgumentException e) {
            	logger.error(" Token is not Valid");
            	throwError(request,response);
				return;
            }
            Authentication auth = token != null ? jwtTokenProvider.getAuthentication(token) : null;
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(req, res);

    }
    
    /**
     * 
     * @param response
     * @throws IOException
     */
	private void throwError(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(Instant.now ().toString());
		errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		errorResponse.setMessage("Invalid JWT token");
		errorResponse.setPath(request.getRequestURL().toString());
		errorResponse.setError("UNAUTHORIZED");
		
      
		byte[] responseToSend = restResponseBytes(errorResponse);
		((HttpServletResponse) response).setHeader("Content-Type", "application/json");
		((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
		response.getOutputStream().write(responseToSend);
		//response.
		return;
	}
    
	/**
	 * 
	 * @param eErrorResponse
	 * @return
	 * @throws IOException
	 */
    private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }

	public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}
    
    
}
