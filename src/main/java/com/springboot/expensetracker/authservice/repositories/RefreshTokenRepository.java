package com.springboot.expensetracker.authservice.repositories;

import com.springboot.expensetracker.authservice.entities.RefreshToken;
import com.springboot.expensetracker.authservice.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {

    RefreshToken findById(int id);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken findRefreshTokenByUserInfo(UserInfo userInfo);

}
