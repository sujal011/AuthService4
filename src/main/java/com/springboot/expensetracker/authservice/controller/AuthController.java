package com.springboot.expensetracker.authservice.controller;

import com.springboot.expensetracker.authservice.entities.RefreshToken;
import com.springboot.expensetracker.authservice.models.UserInfoDTO;
import com.springboot.expensetracker.authservice.requests.JwtResponseDTO;
import com.springboot.expensetracker.authservice.services.JwtService;
import com.springboot.expensetracker.authservice.services.RefreshTokenService;
import com.springboot.expensetracker.authservice.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class AuthController {

    private JwtService jwtService;
    private UserDetailsServiceImpl userDetailsService;
    private RefreshTokenService refreshTokenService;


//    @GetMapping("/auth/v1/signup")
//    public ResponseEntity<UserInfoDTO> getSignUp(@RequestBody UserInfoDTO userInfoDTO){
//        return ResponseEntity.ok(userInfoDTO);
//    }

    @GetMapping("/testjwt")
    public ResponseEntity testJwt(){
//
        return ResponseEntity.ok(SecurityContextHolder.getContext()
                .getAuthentication().getName()+"\n"+SecurityContextHolder.getContext().toString());
    }

    @PostMapping("/auth/v1/signup")
    public ResponseEntity SignUp(@RequestBody UserInfoDTO userInfoDTO){

        try {
            Boolean isSignUped = userDetailsService.signUpUser(userInfoDTO);
            if (!isSignUped){
                return new ResponseEntity<>("Username Already Exists", HttpStatus.CONFLICT);
            }
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userInfoDTO.getUsername());
            String jwtToken = jwtService.generateToken(userInfoDTO.getUsername());

            return new ResponseEntity<>(new JwtResponseDTO(jwtToken,refreshToken.getToken()),HttpStatus.CREATED);

        }
        catch (RuntimeException e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
