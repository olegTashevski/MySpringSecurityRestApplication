package mk.oleg.java.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
public class JwtUtil {
	  private String SECRET_KEY = "secret";

	    public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }
	    
	    public boolean hasClaim(String token,String claimName) {
	    	final Claims claims = extractAllClaims(token);
	    	return claims.get(claimName)!=null;
	    }
	    

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    public String generateRefreshToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, userDetails.getUsername());
	    }
	    
	    public String generateToken(UserDetails userDetails ) {
	    	
	    	return createToken(new HashMap<>(), userDetails);
	    }

	    private String createToken(Map<String, Object> claims, UserDetails userDetails) {
			return Jwts.builder().setClaims(claims)
					.setSubject(userDetails.getUsername())
					.claim("authorities", userDetails.getAuthorities())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(45)))
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
			
		}

		private String createToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() +TimeUnit.DAYS.toMillis(8)))
	                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	    }

	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
    
}


