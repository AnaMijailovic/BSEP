package com.defencefirst.siemcentar.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.defencefirst.siemcentar.security.TokenUtils;





//Filter koji ce presretati svaki zahtev klijenta ka serveru
//Sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String username;
		String authToken = tokenUtils.getToken(request);
		authToken = authToken.replace("\"","");
		System.out.print("\nToken: " + authToken);

		if (authToken != null) {
			// uzmi username iz tokena
			System.out.println("Username: " );
			username = tokenUtils.getUsernameFromToken(authToken); // vraca null???
			System.out.println("Username: " + username);
			
			if (username != null) {
				// uzmi user-a na osnovu username-a
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// proveri da li je prosledjeni token validan
				if (tokenUtils.validateToken(authToken, userDetails)) {
					// kreiraj autentifikaciju
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}
		
		chain.doFilter(request, response);
	}

}