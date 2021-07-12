package com.mindware.appform.controller;



import com.mindware.appform.config.JwtRequest;
import com.mindware.appform.config.JwtResponse;
import com.mindware.appform.config.JwtTokenUtil;
import com.mindware.appform.dto.UserDto;
import com.mindware.appform.entity.Rol;
import com.mindware.appform.entity.Users;
import com.mindware.appform.service.RolService;
import com.mindware.appform.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Qualifier("jwtUserDetailsService")
	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UsersService usersService;

	@Autowired
	private RolService rolService;

	@RequestMapping(value = "/rest/v1/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

//		return ResponseEntity.ok(getLoggedUser(authenticationRequest.getUsername(),token));
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

//	private UserDto getLoggedUser(String login, String token){
//		Users users = usersService.findByLogin(login).get();
//		Rol rol = rolService.findByName(users.getRolName()).get();
//		UserDto userDto = new UserDto();
//
//		userDto.setId(users.getId());
//		userDto.setFullName(users.getFullName());
//		userDto.setLogin(users.getLogin());
//		userDto.setMenu(rol.getOptions());
//		userDto.setRolName(users.getRolName());
//		userDto.setToken(token);
//		return userDto;
//	}
}
