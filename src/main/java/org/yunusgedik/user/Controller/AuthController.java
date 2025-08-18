package org.yunusgedik.user.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yunusgedik.user.Model.User.LoginRequest;
import org.yunusgedik.user.Model.User.User;
import org.yunusgedik.user.Model.User.UserDTO;
import org.yunusgedik.user.Security.JwtService;
import org.yunusgedik.user.Service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.email(), loginRequest.password());
        String token = jwtService.generateToken(user.getId(), user.getRoles());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> register(@RequestBody UserDTO userDTO) {
        User user = userService.create(userDTO);
        String token = jwtService.generateToken(user.getId(), user.getRoles());
        return ResponseEntity.ok(Map.of("token", token));
    }
}