package com.hcl.cloud.uaa.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctc.wstx.util.StringUtil;
import com.hcl.cloud.uaa.bean.AuthResponse;
import com.hcl.cloud.uaa.bean.AuthTokenResponse;
import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.repository.UserRepository;
import com.hcl.cloud.uaa.service.ILoginService;
import com.hcl.cloud.uaa.service.ITokenService;

@RestController
@RequestMapping("/decode")
public class TokenDecoderController {
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ILoginService iLoginService;
    
    @Autowired
    private ITokenService iTokenService;
	
	@CrossOrigin("*")
	@GetMapping
	public ResponseEntity<AuthTokenResponse> getCart(@RequestHeader(value="Authorization") String token) {
		
		String userId = null;
	
		JwtToken tokenInfo = iTokenService.getInfoByToken(token);
		HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headers.setAccessControlAllowHeaders(headerlist);
		
		if(null != tokenInfo && !StringUtils.isEmpty(tokenInfo.getUserId())){
			userId = tokenInfo.getUserId();
		}
		
		return new ResponseEntity<AuthTokenResponse>(new AuthTokenResponse(userId), headers, HttpStatus.FOUND);
	}

}
