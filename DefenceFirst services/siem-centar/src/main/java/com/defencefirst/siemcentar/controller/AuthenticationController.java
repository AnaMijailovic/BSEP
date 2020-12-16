package com.defencefirst.siemcentar.controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defencefirst.siemcentar.dto.LoginDTO;
import com.defencefirst.siemcentar.model.Administrator;
import com.defencefirst.siemcentar.model.Role;
import com.defencefirst.siemcentar.model.User;
import com.defencefirst.siemcentar.security.TokenUtils;
import com.defencefirst.siemcentar.service.CustomUserDetailsService;





//Kontroler zaduzen za autentifikaciju korisnika
@RestController

@CrossOrigin
@RequestMapping(value ="/siem/auth", produces = MediaType.APPLICATION_JSON_VALUE)

public class AuthenticationController {

	@Autowired
	TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService userDetailsService;


	

	@PostMapping(value = "/login")
	public ResponseEntity<String> createAuthenticationToken(@RequestBody @Valid LoginDTO authenticationRequest,
			HttpServletResponse response, Device device) throws AuthenticationException, IOException {
		System.out.println("ULETEO SAM U LOGOVANJE");
		if (device == null) {
			System.out.println("PUKLA DETEKCIJA");
		}
		if (device.isNormal()) {
			System.out.println("KACIS SE PREKO KOMPA");
		} else if (device.isTablet()) {
			System.out.println("KACIS SE PREKO TABLETA");
		} else if (device.isMobile()) {
			System.out.println("KACIS SE PREKO MOBILNOG");
		}
		
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));

		// Ubaci username + password u kontext
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token
		User user = (User) authentication.getPrincipal();
		
		@SuppressWarnings("unused")
		int expiresIn = tokenUtils.getExpiredIn(device);
		Role role = null;
		//zajedno sa tokenom salje se i uloga na front pa u zavisnosti od tipa korisnika
		//na frontu ce ce otvarati posebno strana za usera, admina i sys-admina
	
		if (user instanceof Administrator) {
			role = Role.ROLE_ADMINISTRATOR;
		}
	
		else {
			role = Role.ROLE_OPERATOR;
		}

		String jwt = tokenUtils.generateToken(user.getUsername(), role.toString(), device);
		// Vrati token kao odgovor na uspesno autentifikaciju
		return new ResponseEntity<String>(jwt, HttpStatus.OK);
	}


	@PostMapping(value = "/change-password")
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_OPERATOR')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
		
		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}
	
	

	


	
	
	
	


	
	
	
}