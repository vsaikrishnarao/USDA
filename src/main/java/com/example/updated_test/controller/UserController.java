package com.example.updated_test.controller;

import com.example.updated_test.model.User;
import com.example.updated_test.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByUsername(username);

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        if (!user.getPassword().equals(password)) {
            response.put("success", false);
            response.put("message", "Incorrect password");
            return response;
        }

        response.put("success", true);
        response.put("isAdmin", user.getIsAdmin());
        return response;
    }






    @PostMapping("/users")
    public Map<String, Object> addUser(@RequestBody User newUser) {
        Map<String, Object> response = new HashMap<>();

        // Check if user already exists
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return response;
        }

        // Save the new user
        userRepository.save(newUser);
        response.put("success", true);
        response.put("message", "User registered successfully");
        return response;
    }

}
