package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Concours;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Concours entity.
 */
@Repository
public interface ConcoursRepository extends JpaRepository<Concours, Long> {
    default Optional<Concours> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Concours> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Concours> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select concours from Concours concours left join fetch concours.formation",
        countQuery = "select count(concours) from Concours concours"
    )
    Page<Concours> findAllWithToOneRelationships(Pageable pageable);

    @Query("select concours from Concours concours left join fetch concours.formation")
    List<Concours> findAllWithToOneRelationships();

    @Query("select concours from Concours concours left join fetch concours.formation where concours.id =:id")
    Optional<Concours> findOneWithToOneRelationships(@Param("id") Long id);
}
