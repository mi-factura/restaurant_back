package software.restaurante.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.restaurante.domain.User;
import software.restaurante.domain.UserRestaurant;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.expiration}")
  private long jwtExpiration;

  public String generateToken(User user) {
    Map<String, Object> claims = new HashMap<>();

    // Información básica del usuario
    claims.put("user_id", user.getId().toString());
    claims.put("name", user.getFirstName() + " " + user.getLastName());
    claims.put("email", user.getEmail());

    // Roles y restaurantes
    List<String> roles = new ArrayList<>();
    List<Map<String, Object>> restaurants = new ArrayList<>();

    for (UserRestaurant userRestaurant : user.getUserRestaurants()) {
      if (userRestaurant.isActive()) {
        // Convert role name to uppercase to match the enum constant names
        String roleName = userRestaurant.getRole().name().toUpperCase();
        String role = roleName + "_" + userRestaurant.getRestaurant().getId();
        roles.add(role);

        Map<String, Object> restaurantInfo = new HashMap<>();
        restaurantInfo.put("id", userRestaurant.getRestaurant().getId());
        restaurantInfo.put("name", userRestaurant.getRestaurant().getLegalName());
        restaurantInfo.put("role", userRestaurant.getRole().name());
        restaurants.add(restaurantInfo);
      }
    }

    claims.put("roles", roles);
    claims.put("restaurants", restaurants);

    return buildToken(claims, user.getId().toString());
  }

  private String buildToken(Map<String, Object> extraClaims, String subject) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
        .setId(UUID.randomUUID().toString())  // jti claim
        .compact();
  }

  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public List<String> extractRoles(String token) {
    final Claims claims = extractAllClaims(token);
    return claims.get("roles", List.class);
  }

  public String extractUserId(String token) {
    final Claims claims = extractAllClaims(token);
    return claims.get("user_id", String.class);
  }

    public String extractUserIdFromRawToken(String token) {
        final Claims claims = extractAllClaims(getTokenFromHeaderValue(token));
        return claims.get("user_id", String.class);
    }

  @SuppressWarnings("unchecked")
  public List<Map<String, Object>> extractRestaurants(String token) {
    final Claims claims = extractAllClaims(token);
    return claims.get("restaurants", List.class);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private String getTokenFromHeaderValue(String authHeaderValue) {
    return authHeaderValue.split(" ")[1].trim();
  }

}
