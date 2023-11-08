package com.example.microservices.user.controller;

import com.example.microservices.configurations.MyComponent;
import com.example.microservices.configurations.IP;
import com.example.microservices.configurations.MyService;
import com.example.microservices.user.service.UserService;
import com.example.microservices.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    MyComponent myComponent;
    @Autowired
    MyService myService;
    @Autowired
    private Environment env;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/testValue")
    public String  getValue() {
        String appName = env.getProperty("spring.datasource.url");
        String appVersion = env.getProperty("app.version");
        myComponent.printAppInfo();
        return appName;
    }
    @GetMapping("/testValue1")
    public String  getValue1() {
        myService.printAppInfo();
        return myService.printAppInfo();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @PostMapping("/ip")
    public ResponseEntity<String> receiveIp(@RequestBody IP ipAddress) {
        // Logic to handle the received IP address
        System.out.println("Received IP: " + ipAddress.getIp());

        // Respond back (e.g., with a success message)
        return ResponseEntity.ok("Received IP: " + ipAddress.getIp());
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    @Transactional
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    // Other CRUD operations
}