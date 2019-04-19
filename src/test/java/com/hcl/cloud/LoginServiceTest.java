package com.hcl.cloud;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.authentication.AuthenticationManager;

import com.hcl.cloud.uaa.bean.Role;
import com.hcl.cloud.uaa.bean.User;
import com.hcl.cloud.uaa.repository.UserRepository;
import com.hcl.cloud.uaa.security.JwtTokenProvider;
import com.hcl.cloud.uaa.service.impl.LoginServiceImpl;


@RunWith(PowerMockRunner.class)
public class LoginServiceTest {
	
	private AuthenticationManager authenticationManager; 
	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	
	LoginServiceImpl iloginServiceImpl = new LoginServiceImpl();
	
	String token = "lkdfi.kljdflakj12.jkdlasf12lkjd";
	
	@Before
	public void setupMock() {
		authenticationManager = Mockito.mock(AuthenticationManager.class);
		userRepository = Mockito.mock(UserRepository.class);
		jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);
	}
	
	@Test
	public void testLogin(){/*
		
		User user = new User();
		user.setEmail("anusha@hcl.com");
		user.setActive(1);
		user.setEnabled(true);
		user.setExpired(false);
		user.setId("5cb8b6892f30344fc4477b57");
		user.setLastName("v");
		user.setLoacked(false);
		user.setName("anusha");
		user.setPassword("$2a$12$VNW1nJ5XTdw8nxYlqUs/ZOPwxfP2eHsmNOyn.XzUFNiEL94XyODeS");
		user.setUserId("9355cae5-a082-409d-8aaa-f9556982d767");
		
		Role role = new Role();
		role.setId(1);
		role.setRole("admin");
		
		Set<Role> userRole = new HashSet<>();
		userRole.add(role);
		user.setRole(userRole);
		
		Mockito.when(userRepository.findByEmail("anusha@hcl.com")).thenReturn(user);
		Mockito.when(jwtTokenProvider.createToken("anusha@hcl.com", user.getRole().stream()
                .map((Role role1)-> "ROLE_"+role1.getRole()).filter(Objects::nonNull).collect(Collectors.toList()))).thenReturn(token);
		
		iloginServiceImpl.setAuthenticationManager(authenticationManager);
		iloginServiceImpl.setUserRepository(userRepository);
		iloginServiceImpl.login("anusha@hcl.com", "1234");
		
	*/}
}
