package com.citamed.api.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtIssuer {

    // 1. Inyectamos el secreto desde application.properties
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /**
     * Genera un nuevo token JWT para un usuario autenticado.
     */
    public String issueToken(Authentication authentication) {

        // El "subject" del token será el email del usuario
        String subject = authentication.getName();

        // Los "claims" (información) del token
        String roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" ")); // Ej: "ROLE_ADMIN ROLE_USER"

        Instant now = Instant.now();
        long validityInSeconds = 3600L * 24; // 24 horas de validez

        try {
            // 2. Creamos el Signer (firmante) usando nuestro secreto
            MACSigner signer = new MACSigner(jwtSecret.getBytes());

            // 3. Preparamos los Claims (la "carga útil" del token)
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .issueTime(Date.from(now))
                    .expirationTime(Date.from(now.plusSeconds(validityInSeconds)))
                    .claim("scope", roles) // Spring Security usa "scope" para roles/autoridades
                    .build();

            // 4. Creamos el JWT firmado
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS256), // Algoritmo HMAC SHA-256
                    claimsSet
            );

            // 5. Firmamos el token con nuestro secreto
            signedJWT.sign(signer);

            // 6. Devolvemos el token como un String
            return signedJWT.serialize();

        } catch (JOSEException e) {
            // Si la firma falla
            throw new RuntimeException("Error al firmar el token JWT", e);
        }
    }
}