package com.example.gestion_user.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component  // Cette annotation permet à Spring de gérer JwtUtil comme un bean
public class JwtUtil {

    private final String secretKey; // Clé secrète injectée via application.properties
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 heure en millisecondes

    // Injection de la clé secrète via le constructeur
    public JwtUtil(@Value("${jwt.secret-key}") String secretKey) {
        this.secretKey = secretKey;
    }

    // Méthode pour générer un jeton JWT
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);  // Utiliser la clé secrète injectée
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);  // Définir l'heure d'expiration

        // Générer le jeton JWT avec le nom d'utilisateur comme sujet
        return JWT.create()
                .withSubject(username)  // Définir le nom d'utilisateur comme sujet
                .withIssuedAt(new Date())  // Définir l'heure actuelle comme date d'émission
                .withExpiresAt(expirationDate)  // Définir la date d'expiration
                .withIssuer("auth0")  // Facultatif : définir un émetteur pour votre JWT
                .sign(algorithm);  // Signer le jeton avec l'algorithme
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
          
            return true;
        } catch (JWTVerificationException exception) {
       
            return false;
        }
    }

    // Extraire le nom d'utilisateur du jeton
    public String extractUsername(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();  // Le sujet est le nom d'utilisateur
    }
}