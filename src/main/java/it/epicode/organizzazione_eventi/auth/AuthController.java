package it.epicode.organizzazione_eventi.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;

    @PostMapping("/register/user")
    public ResponseEntity<String> register(@RequestBody it.epicode.organizzazione_eventi.auth.RegisterRequest registerRequest) {
        appUserService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                Set.of(Role.ROLE_USER) // Assegna il ruolo di default
        );
        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }

    @PostMapping("/register/organizer")
    public ResponseEntity<String> registerOrganizer(@RequestBody RegisterRequest registerRequest){
        appUserService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getPassword(),
                Set.of(Role.ROLE_ORGANIZER)
        );
        return ResponseEntity.ok("Registrazione avvenuta con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<it.epicode.organizzazione_eventi.auth.AuthResponse> login(@RequestBody it.epicode.organizzazione_eventi.auth.LoginRequest loginRequest) {
        String token = appUserService.authenticateUser(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        return ResponseEntity.ok(new it.epicode.organizzazione_eventi.auth.AuthResponse(token));
    }


}
