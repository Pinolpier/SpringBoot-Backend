package campuscup.localangels.backend.repository;

import campuscup.localangels.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // works because of:
    // findByQuestionId() works because Question is a field in the Answer class
    // and Id is a field in the Question class.
    // You see the nested relationship: Question - Id
    // https://www.callicoder.com/spring-boot-jpa-hibernate-postgresql-restful-crud-api-example/
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods
    List<Product> findByMerchantId(Long merchantId);

    @Query("SELECT p FROM Product p WHERE (:merchantId is null or p.merchant.id = :merchantId) and (:type is null or p.type = :type) and (:name is null or p.name = :name)")
    List<Product> findProductsByFilter(@Param("merchantId") Long merchantId, @Param("type") String type, @Param("name") String name);
}