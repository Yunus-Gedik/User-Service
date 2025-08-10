package org.yunusgedik.user.Service;

import org.springframework.stereotype.Service;
import org.yunusgedik.user.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
