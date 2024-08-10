package com.springboot.expensetracker.authservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class JwtService {



    private final String SECRET = "fdc5d0732617f6026ef7f8736c8ddff521ef599f9dd5f6d4860907005da80de237c1f507098d05c099382eab18fd2c98a0bf8edb1654270d17a162f09262923d0ffc6b431b3af098930e61ad92f252c21ccc66f684263e0705dcb6b967746cd288a088d289e34702f977888944edbf3edeac56d11141033273959617edf7ec0f";

    private byte[] getKeyInBytes(){
        return Decoders.BASE64.decode(SECRET);
    }
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(getKeyInBytes());
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username){
        Map<String,Object> claims= new HashMap<>();
        return createToken(claims,username);
    }

    public String createToken(Map<String , Object> claims,String username){

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*15))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = exctractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims exctractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKeyInBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
