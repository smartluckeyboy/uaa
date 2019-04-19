package com.hcl.cloud.uaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.service.ILoginService;

@Controller
@RequestMapping("/userDetails")
public class Save {

	@Autowired
	private ILoginService iLoginService;
	
	@PostMapping("/add")
	@ResponseBody
	public User saveToken(@RequestBody User user) {
		User user1 = iLoginService.saveUser(user);
		return user1;
	}
}
