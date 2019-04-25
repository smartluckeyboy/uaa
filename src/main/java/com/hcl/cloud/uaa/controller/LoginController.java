package com.hcl.cloud.uaa.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hcl.cloud.uaa.bean.AuthResponse;
import com.hcl.cloud.uaa.bean.LoginRequest;
import com.hcl.cloud.uaa.service.ILoginService;

@Controller
@RequestMapping("/uaa")
public class LoginController {
	public static final Logger logger=LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private ILoginService iLoginService;

    @CrossOrigin("*")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = iLoginService.login(loginRequest.getUsername(),loginRequest.getPassword());
        logger.debug(" Access Token : " + token);
        HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headerlist.add("X-Requested-With");
        headerlist.add("accessToken");
        headers.setAccessControlAllowHeaders(headerlist);
        exposeList.add("accessToken");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("accessToken", token);
        return new ResponseEntity<AuthResponse>(new AuthResponse(token), headers, HttpStatus.CREATED);
    }
    
    /**
     *
     * @param token
     * @return boolean.
     * if request reach here it means it is a valid token.
     */
    @PostMapping("/valid/token")
    @ResponseBody
    public Boolean isValidToken (@RequestHeader(value="accessToken") String token) {
        return true;
    }

	public ILoginService getiLoginService() {
		return iLoginService;
	}


	public void setiLoginService(ILoginService iLoginService) {
		this.iLoginService = iLoginService;
	}
    
}
