package mk.oleg.java.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import mk.oleg.java.security.jwt.JwtUtil;
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil = new JwtUtil();
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getServletPath().equals("/login") || request.getServletPath().equals("/api/token/refresh")) {
			filterChain.doFilter(request, response);
			return;
		}
		String auhtorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(auhtorizationHeader ==null || !auhtorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
			String token = auhtorizationHeader.substring("Bearer ".length());
			String username = jwtUtil.extractUsername(token);
			if(username!=null && SecurityContextHolder.getContext().getAuthentication() ==null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);		
				if(jwtUtil.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken =
							new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(authToken);
					filterChain.doFilter(request, response);
				}
			}
			
			
			
		
		
		
		
	}

}
