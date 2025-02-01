package com.example.gestion_user.controller;

import com.example.gestion_user.model.User;
import com.example.gestion_user.model.User.Role;
import com.example.gestion_user.repository.UserRepository;
import com.example.gestion_user.security.JwtUtil;
import com.example.gestion_user.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        // Authenticate the user
        User user = userRepository.findByEmail(email);
                

        // Verify the password
        if (passwordEncoder.matches(password, user.getPassword())) {
            Role role = user.getRole(); 
            // Generate JWT token
            String token = jwtUtil.generateToken(email);
              Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role.toString());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(4).body("Invalid credentials");
        }
    }
  
    // Méthode d'inscription d'un utilisateur
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            newUser.setRole(Role.USER);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
