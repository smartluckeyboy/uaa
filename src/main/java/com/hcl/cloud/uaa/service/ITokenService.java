package com.hcl.cloud.uaa.service;

import com.hcl.cloud.uaa.bean.JwtToken;

public interface ITokenService {

	JwtToken getInfoByEmail(String email);

	JwtToken getInfoByToken(String email);

}
