package campuscup.localangels.backend.repository;

import campuscup.localangels.backend.model.Merchant;
import campuscup.localangels.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    @Query("SELECT m FROM merchant m WHERE m.email = ?1")
    User findMerchantByEmail(String email);
}