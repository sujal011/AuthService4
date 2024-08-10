package com.springboot.expensetracker.authservice.services;

import com.springboot.expensetracker.authservice.entities.CustomUserDetails;
import com.springboot.expensetracker.authservice.entities.UserInfo;
import com.springboot.expensetracker.authservice.models.UserInfoDTO;
import com.springboot.expensetracker.authservice.repositories.UserInfoRepository;
import com.springboot.expensetracker.authservice.utils.Validation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserInfoRepository userInfoRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if(userInfo == null){
            throw new UsernameNotFoundException("User not found with "+username);
        }
        return new CustomUserDetails(userInfo);
    }

    public UserInfo checkIfUserExist(UserInfoDTO userInfoDTO){
        return userInfoRepository.findByUsername(userInfoDTO.getUsername());
    }

    public Boolean signUpUser(UserInfoDTO userInfoDTO) throws RuntimeException {

        if(Objects.nonNull(checkIfUserExist(userInfoDTO))){
            return false;
        }

        if(userInfoDTO.getEmail()!=null){
            if (Validation.validateEmail(userInfoDTO.getEmail())){
            throw new RuntimeException("Invalid Email");
     }
        }
        if (userInfoDTO.getPhoneNumber()!=null){
            if (Validation.validatePhoneNumber(userInfoDTO.getPhoneNumber())){
                throw new RuntimeException("Invalid PhoneNumber");
            }
        }



        String userId = UUID.randomUUID().toString();
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));

        userInfoRepository.save(new UserInfo(userId,userInfoDTO.getUsername(),userInfoDTO.getPassword(),new HashSet<>()));
        return true;
    }
}
