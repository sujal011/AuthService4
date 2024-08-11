package com.springboot.expensetracker.authservice.controller;
import com.springboot.expensetracker.authservice.entities.RefreshToken;
import com.springboot.expensetracker.authservice.entities.UserInfo;
import com.springboot.expensetracker.authservice.requests.AuthRequestDTO;
import com.springboot.expensetracker.authservice.requests.JwtResponseDTO;
import com.springboot.expensetracker.authservice.requests.RefreshTokenDTO;
import com.springboot.expensetracker.authservice.services.JwtService;
import com.springboot.expensetracker.authservice.services.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@AllArgsConstructor
@RestController
public class TokenController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private RefreshTokenService refreshTokenService;

    @PostMapping("/auth/v1/login")
    public ResponseEntity Login(@RequestBody AuthRequestDTO authRequestDTO){
        Authentication authentication;
try {
    authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
}
catch (BadCredentialsException e){
    System.out.println(e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
}
        if (authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            String jwtToken = jwtService.generateToken(authRequestDTO.getUsername());
            return new ResponseEntity<>(new JwtResponseDTO(jwtToken,refreshToken.getToken()),HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<>("Username and Password Combo is Wrong", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("auth/v1/refreshToken")
    public ResponseEntity RefreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {

        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(refreshTokenDTO.getToken());
try {
    if(refreshToken.isEmpty()){
        throw new Exception("Invalid RefreshtToken");
    }
    RefreshToken refreshToken1 = refreshToken.stream().findFirst().orElse(null);
    if(refreshToken1 == null){
        throw new Exception("RefreshToken Not Found");
    }
    RefreshToken refreshToken2 = refreshTokenService.verifyRefreshToken(refreshToken1);
    UserInfo userInfo = refreshToken2.getUserInfo();
    String jwtToken = jwtService.generateToken(userInfo.getUsername());
    return new ResponseEntity<>(new JwtResponseDTO(jwtToken,refreshToken2.getToken()),HttpStatus.CREATED);

}catch (Exception e){
    return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
}
    }


}
