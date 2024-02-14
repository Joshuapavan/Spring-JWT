package com.joshuapavan.jwtpractice.services.impl;

import com.joshuapavan.jwtpractice.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JWTServiceImpl implements JWTService {
    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken( UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24) * 7))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails){

//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();

//        OR

        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolvers){
        Claims claims = extractAllClaims(token);

        return claimResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] bytesKey = Decoders.BASE64.decode("1alhJvqdBcR5OU8tIfkf4EL9CV+wIPvp4XajmS1gSKDtGYCU01eHCct49eUFpzhMtoAh0TWh64vn58OEneZhDglgV7mmbz7bw9S96oS9lfHzJ5+9yeyw1kjQfB+kCKpkcYEs0jAcThNJjtrDGwZUrwjYTn/Dnh8rIWJjRfysha32uLrW/QKsOOKbLmJqoEqc5GCAZ3uISsdhQOdz8EG9+1iHSzJ2MWejt5IMGeHDk66ZS9mh4r4TMxmeiLkirFB23FF6H2jfTVH8Ru9WcKmHfEtaILBK9KT8BWBN4gjrCvoND68/2nWPFiPA8PXURbQKEBhZ8TNApvpqlxYtZu5Hz5nrifkcmJF7Vl5vDlSdEJc=");
        return Keys.hmacShaKeyFor(bytesKey);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userEmail = extractUserName(token);
//        log.info("\n\n\nUser email = "+userEmail+"\n\n\n\n");

        return (userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
