package it.epicode.organizzazione_eventi.prenotazione;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrenotazioneRequest {

    @NotNull(message = "L'ID dell'evento non può essere null")
    @Positive(message = "L'ID dell'evento deve essere positivo")
    @Schema(description = "ID dell'evento per cui effettuare la prenotazione", example = "1")
    private Long eventoId;

    @Positive(message = "Il numero di posti deve essere maggiore di zero")
    @Schema(description = "Numero di posti richiesti per la prenotazione", example = "3")
    private int numeroPosti;

}
