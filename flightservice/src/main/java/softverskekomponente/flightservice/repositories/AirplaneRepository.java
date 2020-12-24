package softverskekomponente.flightservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import softverskekomponente.flightservice.entities.Airplane;


public interface AirplaneRepository extends JpaRepository<Airplane, Integer> {

}
