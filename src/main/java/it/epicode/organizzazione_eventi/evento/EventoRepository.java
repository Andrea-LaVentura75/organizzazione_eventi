package it.epicode.organizzazione_eventi.evento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento,Long> {
}
