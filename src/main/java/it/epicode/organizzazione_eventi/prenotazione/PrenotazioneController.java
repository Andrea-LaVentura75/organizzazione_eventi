package it.epicode.organizzazione_eventi.prenotazione;

import it.epicode.organizzazione_eventi.auth.AppUser;
import it.epicode.organizzazione_eventi.auth.AppUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;
    private final AppUserRepository appUserRepository;

    @PostMapping("/prenotazioni")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Prenotazione> prenotaEvento(@Valid @RequestBody PrenotazioneRequest prenotazioneRequest, Principal principal) {

        AppUser currentUser = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Prenotazione prenotazione = prenotazioneService.prenotazioneEvento(prenotazioneRequest, currentUser);
        return ResponseEntity.ok(prenotazione);
    }

    @DeleteMapping("/prenotazioni/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> cancellaPrenotazione(@PathVariable Long id, Principal principal) {

        AppUser currentUser = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        prenotazioneService.cancellaPrenotazione(id, currentUser);
        return ResponseEntity.ok("Prenotazione cancellata con successo.");
    }
}
