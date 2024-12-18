package com.Dennis.BookApp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {
    @NotEmpty(message = "email is mandatory")
    @NotBlank(message = "email is mandatory")
    @Email(message = "email is not well formatted")
    private String email;
    @NotEmpty(message="password is mandatory")
    @NotBlank(message = "password should not be empty")
    @Size(min = 6, message = "password should be minimum 8 characters ")
    private String password;

    public @NotEmpty @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @NotBlank @Email String email) {
        this.email = email;
    }

    public @NotEmpty @NotBlank @Size(min = 6) String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty @NotBlank @Size(min = 6) String password) {
        this.password = password;
    }
}
