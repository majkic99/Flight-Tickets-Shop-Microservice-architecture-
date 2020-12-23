package softverskekomponente.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softverskekomponente.userservice.entities.CreditCard;


@Repository
public interface CreditCardsRepository extends JpaRepository<CreditCard, Long> {

}
