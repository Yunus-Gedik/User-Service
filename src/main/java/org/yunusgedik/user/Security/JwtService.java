package org.yunusgedik.user.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.expiration-minutes}")
    private long expirationMinutes;

    private final JwtKeyProvider keyProvider;

    public JwtService(JwtKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    public String generateToken(Long userId, Set<String> roles) {
        Instant now = Instant.now();
        return Jwts.builder()
            .setSubject(userId.toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(expirationMinutes * 60)))
            .addClaims(Map.of("roles", roles)) // add roles claim
            .signWith(keyProvider.getPrivateKey(), SignatureAlgorithm.RS256)
            .compact();
    }

    public Long extractUserId(String token) {
        String subject = Jwts.parserBuilder()
            .setSigningKey(keyProvider.getPublicKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
        return Long.parseLong(subject);
    }

    public Set<String> extractRoles(String token) {
        Object rolesClaim = Jwts.parserBuilder()
            .setSigningKey(keyProvider.getPublicKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .get("roles");

        List<?> rolesList = (List<?>) rolesClaim;
        return rolesList.stream()
            .filter(obj -> obj instanceof String)
            .map(obj -> (String) obj)
            .collect(Collectors.toSet());
    }

    public static boolean isCurrentUserAdmin() {    
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
        return isAdmin;
    }

}