package org.group4.travelexpertsapi.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private SecretKey secretKey;

    public JwtService() {
        this.secretKey = generateSecretKey();
    }

    // generate secret key (to sign token)

    public SecretKey generateSecretKey() {
        SecretKey secretKey = Jwts.SIG.HS512.key().build();

        // encode key to string
        String encodedKey = DatatypeConverter.printHexBinary(secretKey.getEncoded());
//        System.out.println("EncodedKey: " + encodedKey);
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return Keys.hmacShaKeyFor(decodedKey);

    }

    // generate token

    public String generateToken(UserDetails userDetails) {

        // using Hash-map to store claims

        Map<String, String> claim = new HashMap<>();
        claim.put("webuser", "travelexpertsapp");

        return Jwts.builder()
                .claims(claim)
                .setSubject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(3600))) // 1 hour
                .signWith(secretKey)
                .compact();

    }

    private Claims getPayload(String authToken) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(authToken)
                .getPayload();
        return claims;
    }


    public String getUsernameFromToken(String authToken) {
        Claims claims = getPayload(authToken);
        return claims.getSubject();
    }



    public boolean checkIfTokenExpired(String authToken) {
        Claims claims = getPayload(authToken);
        return claims.getExpiration().after(Date.from(Instant.now()));

    }
}
