package com.Dennis.BookApp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    @NotEmpty
    @NotBlank
    @Size(min = 6)
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
