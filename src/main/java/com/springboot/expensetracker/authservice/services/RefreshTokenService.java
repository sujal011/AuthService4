package com.springboot.expensetracker.authservice.services;

import com.springboot.expensetracker.authservice.entities.RefreshToken;
import com.springboot.expensetracker.authservice.entities.UserInfo;
import com.springboot.expensetracker.authservice.repositories.RefreshTokenRepository;
import com.springboot.expensetracker.authservice.repositories.UserInfoRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserInfoRepository userInfoRepository;


    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserInfoRepository userInfoRepository,RefreshTokenRepository refreshTokenRepository){
        this.userInfoRepository = userInfoRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username){

        UserInfo userInfo = userInfoRepository.findByUsername(username);

        RefreshToken refreshToken = refreshTokenRepository.findRefreshTokenByUserInfo(userInfo);
        if(refreshToken!=null){
//            refreshToken.setUserInfo(userInfo);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(1000*60*60));

            return refreshTokenRepository.save(refreshToken);
        }
        else {

            RefreshToken newRefreshToken = RefreshToken
                    .builder()
                    .userInfo(userInfo)
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(1000 * 60 * 60))
                    .build();

            return refreshTokenRepository.save(newRefreshToken);
        }
    }

    public RefreshToken verifyRefreshToken(RefreshToken refreshToken) throws Exception {
        if (refreshToken.getExpiryDate().compareTo(Instant.now())<0){
            throw new Exception("RefreshToken is Expired, Please Login Again!");
        }
        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }


}
