package com.hcl.cloud.uaa.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.cloud.uaa.bean.AuthTokenResponse;
import com.hcl.cloud.uaa.bean.JwtToken;
import com.hcl.cloud.uaa.security.JwtTokenProvider;
import com.hcl.cloud.uaa.service.ITokenService;

@RestController
@RequestMapping("/decode")
public class TokenDecoderController {

	private static final Logger logger = LoggerFactory.getLogger(TokenDecoderController.class);

	@Autowired
	private ITokenService iTokenService;

	@CrossOrigin("*")
	@GetMapping
	public ResponseEntity<AuthTokenResponse> getCart(@RequestHeader(value = "Authorization") String token) {

		String userId = null;

		JwtToken tokenInfo = iTokenService.getInfoByToken(token);
		HttpHeaders headers = new HttpHeaders();
		List<String> headerlist = new ArrayList<>();
		headerlist.add("Content-Type");
		headerlist.add(" Accept");
		headers.setAccessControlAllowHeaders(headerlist);

		if (null != tokenInfo && !StringUtils.isEmpty(tokenInfo.getUserId())) {

			logger.debug(" UserId: " + tokenInfo.getUserId());
			userId = tokenInfo.getUserId();
		}

		return new ResponseEntity<AuthTokenResponse>(new AuthTokenResponse(userId), headers, HttpStatus.FOUND);
	}

}
