package org.yunusgedik.user.Service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yunusgedik.user.Model.User.User;
import org.yunusgedik.user.Model.User.UserDTO;
import org.yunusgedik.user.Security.JwtService;

import java.util.Map;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthService(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public Map<String, String> login(String email, String password) {
        User user = userService.getByEmail(email);

        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getId(), user.getRoles());
        return Map.of("token", token);
    }

    public Map<String, String> register(UserDTO userDTO) {
        User user = userService.create(userDTO);

        String token = jwtService.generateToken(user.getId(), user.getRoles());
        return Map.of("token", token);
    }
}
