package com.seyma.socialmediaapplication.services;

import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.UserRepo;
import com.seyma.socialmediaapplication.security.JwtUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        return JwtUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id){
        User user =userRepo.findById(id).get();
        return JwtUserDetails.create(user);
    }
}
