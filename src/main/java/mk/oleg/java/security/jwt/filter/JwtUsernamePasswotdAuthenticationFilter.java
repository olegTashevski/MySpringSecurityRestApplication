package mk.oleg.java.security.jwt.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import mk.oleg.java.exception.UsernameExistException;
import mk.oleg.java.security.jwt.JwtUtil;
import mk.oleg.java.dto.UsernameAndPasswordAuthenticationRequest;
@RequiredArgsConstructor
public class JwtUsernamePasswotdAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil = new JwtUtil();

	@Override
	@SneakyThrows
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
				.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
		Authentication authentication = new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(),authenticationRequest.getPassword() );
		Authentication authenticate = authenticationManager.authenticate(authentication);
		return authenticate;		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User)authResult.getPrincipal();
		String access_token = jwtUtil.generateToken(user);
		String refresh_token = jwtUtil.generateRefreshToken(user);
		Map<String, String> tokens = new HashMap<>();
		tokens.put("access_token", access_token);
		tokens.put("refresh_token", refresh_token);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), tokens);
		
	}
	
	

}
