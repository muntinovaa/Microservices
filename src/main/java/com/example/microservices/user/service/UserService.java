package com.example.microservices.user.service;

import com.example.microservices.user.repository.UserRepository;
import com.example.microservices.user.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    // Other CRUD operations

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findUsersByName(String name) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("get_user_by_name", User.class);
        storedProcedure.registerStoredProcedureParameter("username", String.class, ParameterMode.IN);
        storedProcedure.setParameter("username", name);

        return storedProcedure.getResultList();
    }
}
