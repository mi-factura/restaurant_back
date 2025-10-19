package software.restaurante.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import software.restaurante.domain.Consumable;
import software.restaurante.domain.ConsumableCategory;

@Repository
public interface ConsumableRepository extends JpaRepository<Consumable, Long> {

    @Query("""
        SELECT c FROM Consumable c 
        WHERE c.restaurant.id = :restaurantId
        AND (:categoryId IS NULL OR c.category.id = :categoryId)
        AND (:searchTerm IS NULL OR :searchTerm = '' OR 
             LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
    """)
    List<Consumable> findByRestaurantIdAndOptionalFilters(
        @Param("restaurantId") Long restaurantId,
        @Param("categoryId") Long categoryId,
        @Param("searchTerm") String searchTerm
    );
    
    List<Consumable> findByRestaurantId(Long restaurantId);
}
