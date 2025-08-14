package org.yunusgedik.user.Controller;

import org.springframework.web.bind.annotation.*;
import org.yunusgedik.user.Model.User.User;
import org.yunusgedik.user.Model.User.UserDTO;
import org.yunusgedik.user.Service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.get(id);
    }

    @GetMapping
    public User getUserByRequestParam(@RequestParam(name = "id") Long id) {
        return userService.get(id);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping("/new")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.create(userDTO);
    }

    @PatchMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
