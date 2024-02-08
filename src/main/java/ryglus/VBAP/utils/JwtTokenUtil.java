package ryglus.VBAP.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ryglus.VBAP.model.Customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(secret).build();
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String generateToken(Customer appUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("version", appUser.getJwtVersion());
        return doGenerateToken(claims, appUser.getUsername());
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Boolean validateToken(String token, Customer userDetails) {
        final String username = getUsernameFromToken(token);
        final int version = getJwtVersionFromToken(token);
        return (username.equals(userDetails.getUsername()) && version == userDetails.getJwtVersion() && !isTokenExpired(token));
    }

    public int getJwtVersionFromToken(String token) {
    Integer version = getClaimFromToken(token, claims -> claims.get("version", Integer.class));
    return version != null ? version : 0;
}
}