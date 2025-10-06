package org.yunusgedik.user.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunusgedik.user.Model.User.LoginRequest;
import org.yunusgedik.user.Model.User.UserDTO;
import org.yunusgedik.user.Security.JwtService;
import org.yunusgedik.user.Service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String,String> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.email(), loginRequest.password());
    }

    @PostMapping("/register")
    public Map<String,String> register(@RequestBody UserDTO userDTO) {
        if(JwtService.isCurrentUserAdmin() == false && 
            (userDTO.getRoles() != null && 
            userDTO.getRoles().isEmpty() == false)
        ) {
            throw new RuntimeException("Only admins can assign roles during registration.");
        }

        return authService.register(userDTO);
    }
}