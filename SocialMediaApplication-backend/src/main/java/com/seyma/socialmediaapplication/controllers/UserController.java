package com.seyma.socialmediaapplication.controllers;

import com.seyma.socialmediaapplication.exceptions.UserNotFoundException;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.responses.UserResponse;
import com.seyma.socialmediaapplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    private  UserService userService;


    // Constructor injection ( User repo nun objesini bulup içine atar. Autowired la da yapabilirdik )
    public UserController(UserService userService){
        this. userService= userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return  userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return  userService.saveOneUser(newUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId){
        User user = userService.getOneUserById(userId);
        if(user ==null){
            throw new UserNotFoundException("User Not Found");
        }
        return new UserResponse(user);
    }
    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId, @RequestBody User newUser){
            return userService.updateOneUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
         userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
        return userService.getUserActivity(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        String message ="User Not Found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
