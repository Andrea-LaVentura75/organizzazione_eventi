package it.epicode.organizzazione_eventi.evento;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequest {

    private String descrizione;

    private String titolo;

    private String luogo;

    private LocalDate data;

    private int postiDisponibili;

    private Long organizzatoreId;
}
