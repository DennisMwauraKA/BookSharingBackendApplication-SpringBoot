package com.Dennis.BookApp.auth;

import com.Dennis.BookApp.email.EmailService;
import com.Dennis.BookApp.email.EmailTemplate;
import com.Dennis.BookApp.entity.Token;
import com.Dennis.BookApp.entity.User;
import com.Dennis.BookApp.repo.RoleRepo;
import com.Dennis.BookApp.repo.TokenRepo;
import com.Dennis.BookApp.repo.UserRepo;
import com.Dennis.BookApp.service.JwtService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationService {


    @Autowired
    private TokenRepo tokenRepository;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void registerUser(RegistrationRequest request) throws MessagingException {

// get the user roles from the database
        var userRole = roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("ROle user was not initialized"));
// generate an encoded password
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder().
                firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encryptedPassword)
                .enabled(false)
                .accountLocked(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        //send email verification
        emailService.sendEmail(
                user.getEmail(),
                user.getFirstName(),
                EmailTemplate.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {

        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {


        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));

        }

        return codeBuilder.toString();


    }

    public AuthenticationResponse authenticate(@Valid AuthenticationRequest request) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authentication.isAuthenticated()) {

                String token = jwtService.generateToken(request.getEmail());
                return new AuthenticationResponse(token);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials", e);
        }
        return null;

    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired, New token has been sent");

        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found "));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());

        tokenRepository.save(savedToken);

    }
}
