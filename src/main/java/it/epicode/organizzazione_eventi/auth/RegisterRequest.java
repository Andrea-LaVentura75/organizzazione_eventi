package it.epicode.organizzazione_eventi.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
