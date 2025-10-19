package software.restaurante.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.ConsumableCategory;

@Repository
public interface ConsumableCategoryRepository extends JpaRepository<ConsumableCategory, Long> {

    List<ConsumableCategory> findByRestaurantId(Long restaurantId);

    @Query("""
        SELECT DISTINCT cc FROM ConsumableCategory cc 
        LEFT JOIN FETCH cc.consumables c 
        WHERE (cc.restaurant.id = :restaurantId)
        AND (:categoryId IS NULL OR cc.id = :categoryId)
        AND (:searchTerm IS NULL OR :searchTerm = '' OR
             LOWER(CAST(c.name AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    """)
    List<ConsumableCategory> findWithFilters(
        @Param("restaurantId") Long restaurantId,
        @Param("categoryId") Long categoryId,
        @Param("searchTerm") String searchTerm
    );
}
