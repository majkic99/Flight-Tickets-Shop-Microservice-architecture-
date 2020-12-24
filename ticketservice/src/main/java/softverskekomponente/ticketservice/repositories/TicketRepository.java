package softverskekomponente.ticketservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import softverskekomponente.ticketservice.entities.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer>{
	
	List<Ticket> findByIdUser(int idUser);
	
	List<Ticket> findByIdFlight(int idFlight);
	
	boolean existsByIdUser(int idUserk);
	
	boolean existsByIdFlight(int idFlight);
	
	@Query("select count(t) from Ticket t where t.idFlight = :idFlight")
	int numberOfTakenPlaces(int idFlight);

	
}
