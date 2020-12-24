package softverskekomponente.userservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import softverskekomponente.userservice.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByEmail(String email);

	boolean existsByEmail(String email);
	
	@Query("select u from User u where u.id = :id")
	List<User> findById(int id);
}
