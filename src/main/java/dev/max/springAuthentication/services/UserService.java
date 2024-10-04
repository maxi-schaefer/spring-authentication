package dev.max.springAuthentication.services;

import dev.max.springAuthentication.entities.User;
import dev.max.springAuthentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Max
 * @since 10/3/2024
 */

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepo.findAll().forEach(users::add);

        return users;
    }

    public void deleteUser(String id) {
        Optional<User> user = userRepo.findById(id);
        user.ifPresent(userRepo::delete);
    }

    public User updateUser(String id, User updatedUser) {
        Optional<User> dbUser = userRepo.findById(id);

        if(dbUser.isPresent()) {
            User existing = dbUser.get();

            if(!existing.getEmail().equals(updatedUser.getEmail()) && userRepo.findByEmail(updatedUser.getEmail()).isPresent()) {
                throw new RuntimeException("An User with this email already exists");
            }

            if(updatedUser.getPassword() != null) {
                updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }

            BeanUtils.copyProperties(updatedUser, existing, getNullPropertyNames(updatedUser));

            return userRepo.save(existing);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    private String[] getNullPropertyNames(User source) {
        // Use BeanUtils or any utility to get null property names
        return Arrays.stream(BeanUtils.getPropertyDescriptors(User.class))
                .filter(pd -> {
                    try {
                        return pd.getReadMethod().invoke(source) == null;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .map(PropertyDescriptor::getName)
                .toArray(String[]::new);
    }
}
