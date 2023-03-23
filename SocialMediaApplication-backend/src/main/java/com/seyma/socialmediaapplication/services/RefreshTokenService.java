package com.seyma.socialmediaapplication.services;

import com.seyma.socialmediaapplication.model.RefreshToken;
import com.seyma.socialmediaapplication.model.User;
import com.seyma.socialmediaapplication.repository.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${refresh.token.expires.in}")
    Long expireSeconds;

    private final RefreshTokenRepo refreshTokenRepo;

    @Autowired
    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
    }

    public boolean isRefreshExpired(RefreshToken token){
        return token.getExpiryDate().before(new Date());
    }

    public String createRefreshToken(User user){
        RefreshToken token =refreshTokenRepo.findByUserId(user.getId());
        if(token ==null){
            token = new RefreshToken();
            token.setUser(user);
        }

        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepo.save(token);
        return token.getToken();
    }


    public RefreshToken getByUser(Long userId) {
        return refreshTokenRepo.findByUserId(userId);
    }
}
