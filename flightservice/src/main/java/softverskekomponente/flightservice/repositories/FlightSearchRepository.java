package softverskekomponente.flightservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import softverskekomponente.flightservice.entities.Flight;

@RepositoryRestResource
public interface FlightSearchRepository extends JpaRepository<Flight, Integer>, JpaSpecificationExecutor<Flight>{

}
