package it.epicode.organizzazione_eventi.evento;

import it.epicode.organizzazione_eventi.auth.AppUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String titolo;

    private String descrizione;

    private String luogo;

    private LocalDate data;

    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private AppUser organizer;

}