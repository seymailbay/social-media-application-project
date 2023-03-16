package com.seyma.socialmediaapplication.controllers;


import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.requests.UserRequest;
import com.seyma.socialmediaapplication.responses.AuthResponse;
import com.seyma.socialmediaapplication.security.JwtTokenProvider;
import com.seyma.socialmediaapplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName()
                ,loginRequest.getPassword());
        Authentication auth= authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken= jwtTokenProvider.generateJwtToken(auth);
        User user= userService.getOneUserByUserName(loginRequest.getUserName());
        AuthResponse authResponse =new AuthResponse();
        authResponse.setMessage("Bearer " + jwtToken);
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest){
        AuthResponse authResponse =new AuthResponse();
        if(userService.getOneUserByUserName(registerRequest.getUserName()) !=null){
            authResponse.setMessage("Username already in use. :)");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        User user =new User();
        user.setUsername(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveOneUser(user);
        authResponse.setMessage("User successfully registered.");
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

}
