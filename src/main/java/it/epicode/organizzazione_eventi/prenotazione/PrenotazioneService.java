package it.epicode.organizzazione_eventi.prenotazione;


import it.epicode.organizzazione_eventi.auth.AppUser;
import it.epicode.organizzazione_eventi.auth.AppUserRepository;
import it.epicode.organizzazione_eventi.evento.Evento;
import it.epicode.organizzazione_eventi.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
public class PrenotazioneService {

    @Autowired
    private  PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private  EventoRepository eventoRepository;

    @Transactional
    public Prenotazione prenotazioneEvento(@Valid PrenotazioneRequest prenotazioneRequest, AppUser currentUser) {
        Evento evento = eventoRepository.findById(prenotazioneRequest.getEventoId())
                .orElseThrow(() -> new EntityNotFoundException("prenotazione non trovata"));

        if (evento.getPostiDisponibili() < prenotazioneRequest.getNumeroPosti()) {
            throw new RuntimeException("Numero di posti insufficienti.");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(currentUser);
        prenotazione.setEvento(evento);
        prenotazione.setNumeroPosti(prenotazioneRequest.getNumeroPosti());
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        evento.setPostiDisponibili(evento.getPostiDisponibili() -prenotazioneRequest.getNumeroPosti());
        eventoRepository.save(evento);

        return prenotazioneRepository.save(prenotazione);
    }

    @Transactional
    public void cancellaPrenotazione(Long id, AppUser currentUser) {

        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));


        if (!prenotazione.getUtente().equals(currentUser)) {
            throw new RuntimeException("Permesso negato: Non hai effettuato questa prenotazione.");
        }


        Evento evento = prenotazione.getEvento();


        evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getNumeroPosti());


        eventoRepository.save(evento);


        prenotazioneRepository.delete(prenotazione);
    }

}
