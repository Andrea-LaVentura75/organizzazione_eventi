package it.epicode.organizzazione_eventi.evento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoRequest {

    @NotNull(message = "La descrizione non può essere null")
    @Schema(description = "Descrizione dell'evento", example = "Evento di beneficenza annuale")
    private String descrizione;

    @NotNull(message = "Il titolo non può essere null")
    @Schema(description = "Titolo dell'evento", example = "Maratona per la solidarietà")
    private String titolo;

    @NotNull(message = "Il luogo non può essere null")
    @Schema(description = "Luogo dell'evento", example = "Piazza del Popolo")
    private String luogo;

    @NotNull(message = "La data non può essere null")
    @Schema(description = "Data dell'evento", example = "2025-05-01")
    private LocalDate data;

    @Positive(message = "Il numero di posti disponibili deve essere maggiore di zero")
    @Schema(description = "Numero di posti disponibili per l'evento", example = "100")
    private int postiDisponibili;

    @NotNull(message = "L'ID dell'organizzatore non può essere null")
    @Positive(message = "L'ID dell'organizzatore deve essere positivo")
    @Schema(description = "ID dell'organizzatore dell'evento", example = "1")
    private Long organizzatoreId;
}
