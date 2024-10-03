package dev.max.springAuthentication.services;

import dev.max.springAuthentication.entities.User;
import dev.max.springAuthentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Max
 * @since 10/3/2024
 */

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepo.findAll().forEach(users::add);

        return users;
    }
}
