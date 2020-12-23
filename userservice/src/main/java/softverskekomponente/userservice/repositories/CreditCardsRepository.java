package softverskekomponente.userservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softverskekomponente.userservice.entities.CreditCard;


@Repository
public interface CreditCardsRepository extends JpaRepository<CreditCard, Long> {

	@Query("select c from CreditCard c where c.user.id = :userID")
	List<CreditCard> getCreditCardsByUserID(long userID);
	
}
