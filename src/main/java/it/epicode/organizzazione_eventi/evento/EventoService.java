package it.epicode.organizzazione_eventi.evento;

import it.epicode.organizzazione_eventi.auth.AppUser;
import it.epicode.organizzazione_eventi.auth.AppUserRepository;
import it.epicode.organizzazione_eventi.auth.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.nio.file.AccessDeniedException;

@Service
@Validated
@RequiredArgsConstructor
public class EventoService {

    private final AppUserRepository appUserRepository;

    private final EventoRepository eventoRepository;

    private AppUser utenteLoggato(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return appUserRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("utente non esistente"));
    }

    public  Evento creaEvento(@Valid EventoRequest eventoRequest){
        AppUser organizer = appUserRepository.findById(eventoRequest.getOrganizzatoreId())
                .orElseThrow(() -> new EntityNotFoundException("Organizzatore non trovato"));
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoRequest, evento);
        evento.setOrganizer(organizer);
        return eventoRepository.save(evento);
    }

    public Evento modificaEvento(Long id,@Valid EventoRequest eventoRequest) throws AccessDeniedException {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        AppUser utenteLoggato= utenteLoggato();
        if(!evento.getOrganizer().getId().equals(utenteLoggato.getId())){
            throw new AccessDeniedException("non puoi modificare questo evento");
        }

        BeanUtils.copyProperties(eventoRequest, evento);
        return eventoRepository.save(evento);

    }

    public Evento cancellaEvento(Long id) throws AccessDeniedException {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        AppUser utenteLoggato= utenteLoggato();
        boolean admin = utenteLoggato.getRoles().contains(Role.ROLE_ADMIN);
        if(!evento.getOrganizer().getId().equals(utenteLoggato.getId()) && !admin){
            throw new AccessDeniedException("non puoi eliminare questo evento");
        }

        eventoRepository.delete(evento);
        return evento;
    }

}
