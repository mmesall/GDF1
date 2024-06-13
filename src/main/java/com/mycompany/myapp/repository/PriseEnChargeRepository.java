package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PriseEnCharge;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PriseEnCharge entity.
 */
@Repository
public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge, Long> {
    default Optional<PriseEnCharge> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PriseEnCharge> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PriseEnCharge> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select priseEnCharge from PriseEnCharge priseEnCharge left join fetch priseEnCharge.bailleur",
        countQuery = "select count(priseEnCharge) from PriseEnCharge priseEnCharge"
    )
    Page<PriseEnCharge> findAllWithToOneRelationships(Pageable pageable);

    @Query("select priseEnCharge from PriseEnCharge priseEnCharge left join fetch priseEnCharge.bailleur")
    List<PriseEnCharge> findAllWithToOneRelationships();

    @Query("select priseEnCharge from PriseEnCharge priseEnCharge left join fetch priseEnCharge.bailleur where priseEnCharge.id =:id")
    Optional<PriseEnCharge> findOneWithToOneRelationships(@Param("id") Long id);
}
