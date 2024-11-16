package com.Dennis.BookApp.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class RegistrationRequest {


    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;
    @NotEmpty
    @NotBlank
    @Email
    private String email;
    @NotEmpty
    @NotBlank
    @Size(min = 6)
    private String password;

    public @NotEmpty @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotEmpty @NotBlank String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty @NotBlank String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty @NotBlank String lastName) {
        this.lastName = lastName;
    }

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
