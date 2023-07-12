package com.authentication.jwt.config;

import com.authentication.jwt.models.entities.User;
import com.authentication.jwt.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {


    @Autowired
    private UserRepository userRepository;
    private static final String SECRET_KEY = "dJ6abSnBOE0oRe60YjPDOjSrDIjbBuXfySTmcFVDlkl8UUXNKB686lt2Zvav+H0FWl/aYEAWYjTXRdUla7KYmQEAVzmgh9j/04phaALbPnlapIERKhLTe7TjIuiUNBBO3JVz8hTa+zUE7TT0JgJ9drukivvtuiMHM+j/bVRzMsUhx20XDAicF5v2kH6YvL8Tcu9FrOB782j/5+1VTtpG7WzIlhrCfrlYSH2fJ83PbYcS4Y8D9f66Dw6SBIdlFEFqpUhMehAI4uZCqoCX+lX9OrG6E9tweWFxkO6T82QUWT0lY5NNs1qJSoFD7xQZWKIVJOgbdHm/zdI9V7gS8c+/1ph61yAnCX8upnkXmPKhcMc=";
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public User getAuthUser(HttpServletRequest request){
        System.out.println(request.getUserPrincipal().getName());
        User authUser = userRepository.findByEmail(request.getUserPrincipal().getName()).get();

        System.out.println("Util Function");
        System.out.println(authUser.toString());
        return authUser;
    }

}
