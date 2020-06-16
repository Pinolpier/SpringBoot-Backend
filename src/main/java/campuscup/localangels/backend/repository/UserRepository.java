package campuscup.localangels.backend.repository;

import campuscup.localangels.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM user u WHERE u.email = ?1")
    User findUserByEmail(String email);
}