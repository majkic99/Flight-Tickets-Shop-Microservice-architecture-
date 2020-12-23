package softverskekomponente.flightservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import softverskekomponente.flightservice.entities.Flight;


public interface FlightRepository extends JpaRepository<Flight, Long> {

	@Query("select f from Flight f where f.airplane.id = :airplaneID")
	boolean existsAirplaneID(long airplaneID);
}
