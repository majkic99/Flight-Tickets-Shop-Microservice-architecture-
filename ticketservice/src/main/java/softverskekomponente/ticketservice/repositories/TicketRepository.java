package softverskekomponente.ticketservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import softverskekomponente.ticketservice.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	List<Ticket> findByIdUser(long idUser);
	
	List<Ticket> findByIdFlight(long idFlight);
	
	boolean existsByIdUser(long idUserk);
	
	boolean existsByIdFlight(long idFlight);

}
