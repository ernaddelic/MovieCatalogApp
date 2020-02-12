package com.example.movieratingsservice.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.function.*;
import com.example.movieratingsservice.configuration.JwtProps;

@Component
public class JwtUtil {

    private JwtProps jwtProps;

    public JwtUtil(JwtProps jwtProps) {
        this.jwtProps = jwtProps;
    }

    public String createToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<String, Object>();
        return doGenerateToken(claims, userDetails);
    }

    public String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() 
        + 1000 * 60 *60 * 24 * 10)).signWith(SignatureAlgorithm.HS512, jwtProps.getKey()).compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtProps.getKey()).parseClaimsJws(token).getBody();
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}