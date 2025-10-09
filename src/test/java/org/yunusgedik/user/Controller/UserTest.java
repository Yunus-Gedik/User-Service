package org.yunusgedik.user.Controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.yunusgedik.user.Security.JwtAuthenticationFilter;
import org.yunusgedik.user.Security.JwtKeyProvider;
import org.yunusgedik.user.Security.JwtService;
import org.yunusgedik.user.Security.SecurityConfig;

@WebMvcTest(UserController.class)
@Import({JwtService.class, JwtKeyProvider.class, JwtAuthenticationFilter.class, SecurityConfig.class})
public class UserTest{

}