package it.epicode.organizzazione_eventi.prenotazione;

import it.epicode.organizzazione_eventi.auth.AppUser;
import it.epicode.organizzazione_eventi.evento.Evento;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @ManyToOne
    private AppUser utente;

    @ManyToOne
    private Evento evento;

    private LocalDateTime dataPrenotazione;

    private int numeroPosti;

}