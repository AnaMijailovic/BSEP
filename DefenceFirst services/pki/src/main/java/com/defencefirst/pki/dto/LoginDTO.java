package com.defencefirst.pki.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginDTO {

    @NotNull(message = "Username is required")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Username is not valid")
    private String username;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "[a-zA-Z0-9.,!?]+", message = "Password is not valid")
    private String password;

    public LoginDTO() {
        super();
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
