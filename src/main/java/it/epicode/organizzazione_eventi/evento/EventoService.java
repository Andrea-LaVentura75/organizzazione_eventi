package it.epicode.organizzazione_eventi.evento;

import it.epicode.organizzazione_eventi.auth.AppUser;
import it.epicode.organizzazione_eventi.auth.AppUserRepository;
import it.epicode.organizzazione_eventi.auth.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final AppUserRepository appUserRepository;

    private final EventoRepository eventoRepository;

    public  Evento creaEvento(EventoRequest eventoRequest){
        AppUser organizer = appUserRepository.findById(eventoRequest.getOrganizzatoreId())
                .orElseThrow(() -> new RuntimeException("Organizzatore non trovato"));
        Evento evento = new Evento()
;
        BeanUtils.copyProperties(eventoRequest, evento);
        evento.setOrganizer(organizer);
        return eventoRepository.save(evento);
    }

    public Evento modificaEvento(Long id, EventoRequest eventoRequest) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        AppUser organizer = appUserRepository.findById(eventoRequest.getOrganizzatoreId())
                .orElseThrow(() -> new RuntimeException("Organizzatore non trovato"));
        BeanUtils.copyProperties(eventoRequest, evento);
        evento.setOrganizer(organizer);
        return eventoRepository.save(evento);

    }

    public Evento cancellaEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        eventoRepository.delete(evento);
        return evento;
    }

}
