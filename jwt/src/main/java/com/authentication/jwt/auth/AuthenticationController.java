package com.authentication.jwt.auth;

import com.authentication.jwt.config.JwtService;
import com.authentication.jwt.models.ResponseWrapper;
import com.authentication.jwt.models.dto.UserDto;
import com.authentication.jwt.service.UserService;
import com.authentication.jwt.models.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtTokenUtil;

    @Autowired
    private AuthenticationService userDetailsService;


    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        User correspondingUser = userService.findUserByEmail(userDetails.getUsername());

        UserDto correspondingUserDto = new UserDto(correspondingUser.getId(), correspondingUser.getFirstName(), correspondingUser.getLastName(), correspondingUser.getEmail(), correspondingUser.getContactNumber(), correspondingUser.getNic(), correspondingUser.getAddressLine1(), correspondingUser.getAddressLine2(), correspondingUser.getCity(), correspondingUser.getCountry(), correspondingUser.getRoles());

        ResponseWrapper body = new ResponseWrapper(new AuthenticationResponse(jwt,correspondingUserDto), "success", "fetched");

        return ResponseEntity.status(HttpStatus.OK)
                .body(body);

    }


}
