package com.Dennis.BookApp.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public class AuthenticationController {


    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationRequest request) throws MessagingException {


        service.registerUser(request);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {

        return ResponseEntity.ok(service.authenticate(request));
    }


    @PostMapping("/activate-account")
    public void activateAccount(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }


}
