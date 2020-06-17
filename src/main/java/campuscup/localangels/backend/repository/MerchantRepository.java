package campuscup.localangels.backend.repository;

import campuscup.localangels.backend.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    @Query("SELECT m FROM merchant m WHERE m.email = ?1")
    List<Merchant> findMerchantByEmail(String email);
}