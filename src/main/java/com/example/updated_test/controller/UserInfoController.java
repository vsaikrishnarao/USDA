package com.example.updated_test.controller;

import com.example.updated_test.model.UserInfo;
import com.example.updated_test.repository.UserInfoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserInfoController {

    private final UserInfoRepository userInfoRepository;

    public UserInfoController(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @PostMapping("/userlogin")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();

        UserInfo user = userInfoRepository.findById(username).orElse(null);

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

    @PostMapping("/newusers")
    public Map<String, Object> addUser(@RequestBody UserInfo newUser) {
        Map<String, Object> response = new HashMap<>();

        // Check for duplicate username
        if (userInfoRepository.existsByUsername(newUser.getUsername())) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return response;
        }

        // Check for duplicate email
        if (userInfoRepository.findByEmail(newUser.getEmail()) != null) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return response;
        }

        userInfoRepository.save(newUser);
        response.put("success", true);
        response.put("message", "User registered successfully");
        return response;
    }

    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<UserInfo> users = userInfoRepository.findAll();

        if (users.isEmpty()) {
            response.put("success", false);
            response.put("message", "No users found");
        } else {
            response.put("success", true);
            response.put("users", users);
        }

        return response;
    }

    @DeleteMapping("/users/{username}")
    public Map<String, Object> deleteUser(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();

        if (!userInfoRepository.existsById(username)) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        userInfoRepository.deleteById(username);
        response.put("success", true);
        response.put("message", "User deleted successfully");
        return response;
    }
}
