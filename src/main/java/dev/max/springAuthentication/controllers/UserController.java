package dev.max.springAuthentication.controllers;

import dev.max.springAuthentication.entities.User;
import dev.max.springAuthentication.services.JwtService;
import dev.max.springAuthentication.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author Max
 * @since 10/3/2024
 */

@RequestMapping("/users")
@Slf4j
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        if(user.getId().equalsIgnoreCase(id)) {
            log.info("Deleting User with following ID: " + id);
            userService.deleteUser(id);
            return ResponseEntity.ok("Successfully deleted User!");
        }

        return ResponseEntity.badRequest().body("You are not allowed to delete this user!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authUser = (User) authentication.getPrincipal();

        if(authUser.getId().equalsIgnoreCase(id)) {
            User user = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(user);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

}
