package softverskekomponente.flightservice.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import softverskekomponente.flightservice.entities.Flight;


public interface FlightRepository extends JpaRepository<Flight, Integer> {

	@Query("select count(f)>0 from Flight f where f.airplane.id = :airplaneID")
	boolean existsAirplaneID(int airplaneID);
	
	@Query("select f from Flight f where f.canceled = 0")
	List<Flight> findAllNotCanceled(Pageable pageable);
}
