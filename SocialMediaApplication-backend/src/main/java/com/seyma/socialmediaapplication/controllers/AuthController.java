package com.seyma.socialmediaapplication.controllers;


import com.seyma.socialmediaapplication.model.RefreshToken;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.requests.RefreshRequest;
import com.seyma.socialmediaapplication.requests.UserRequest;
import com.seyma.socialmediaapplication.responses.AuthResponse;
import com.seyma.socialmediaapplication.security.JwtTokenProvider;
import com.seyma.socialmediaapplication.services.RefreshTokenService;
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

    private RefreshTokenService refreshTokenService;


    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService=refreshTokenService;
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
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return authResponse;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest registerRequest){
        AuthResponse authResponse =new AuthResponse();
        User existingUser = userService.getOneUserByUserName(registerRequest.getUserName());
        if(existingUser != null){
            authResponse.setMessage("Username already in use. :)");
            return new ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        User user =new User();
        user.setUsername(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.saveOneUser(user);

        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(registerRequest.getUserName(),registerRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        authResponse.setMessage("User succesfully registered");
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());
        return new ResponseEntity<>(authResponse,HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshRequest refreshRequest){
        AuthResponse response = new AuthResponse();
        RefreshToken token = refreshTokenService.getRefreshTokenByUserId(refreshRequest.getUserId());
        if(token.getToken().equals(refreshRequest.getRefreshToken()) && !refreshTokenService.isRefreshExpired(token)){
            User user=token.getUser();
            String jwtToken= jwtTokenProvider.generateJwtTokenByUserId(user.getId());
            response.setMessage("token successfully refreshed.");
            response.setAccessToken("Bearer " + jwtToken);
            response.setUserId(user.getId());
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            response.setMessage("refresh token is not valid");
            return new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED);
        }


    }



}
