package org.yunusgedik.user.Service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yunusgedik.user.Model.User.User;
import org.yunusgedik.user.Model.User.UserDTO;
import org.yunusgedik.user.Repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public User get(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User create(UserDTO userDTO) {
        validateUserDTO(userDTO);

        User user = new User();
        modelMapper.map(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        return userRepository.save(user);
    }

    private void validateUserDTO(UserDTO userDTO) {
        if (!isFilled(userDTO.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }
        if (!isFilled(userDTO.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }
        if (!isFilled(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (!isFilled(userDTO.getPassword()) || userDTO.getPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required and must be at least 6 characters");
        }
    }

    public User update(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (isFilled(userDTO.getFirstName())) existingUser.setFirstName(userDTO.getFirstName());
        if (isFilled(userDTO.getLastName())) existingUser.setLastName(userDTO.getLastName());
        if (isFilled(userDTO.getEmail())) existingUser.setEmail(userDTO.getEmail());
        if (isFilled(userDTO.getPhoneNumber())) existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if (isFilled(userDTO.getPassword())) {
            if (userDTO.getPassword().length() < 6) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 6 characters");
            }
            existingUser.setPassword(userDTO.getPassword());
        }
        if (userDTO.getRoles() != null) {
            existingUser.setRoles(userDTO.getRoles());
        }

        return userRepository.save(existingUser);
    }

    private boolean isFilled(String value) {
        return value != null && !value.isEmpty();
    }

    public void delete(Long id) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(existingUser);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}