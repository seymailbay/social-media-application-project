package com.seyma.socialmediaapplication.services;


import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    UserRepo userRepo;   // servis repoya bağlanır.

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepo.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user =  userRepo.findById(userId);
        if(user.isPresent()){
            User foundUser =user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            userRepo.save(foundUser);
            return foundUser;
        }
        else
            return null;
    }

    public void deleteById(Long userId) {
        userRepo.deleteById(userId);
    }

    public User getOneUserByUserName(String username) {
        return userRepo.findByUsername(username);
    }
}
