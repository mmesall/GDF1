package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Professionnel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Professionnel entity.
 */
@Repository
public interface ProfessionnelRepository extends JpaRepository<Professionnel, Long> {
    default Optional<Professionnel> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Professionnel> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Professionnel> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select professionnel from Professionnel professionnel left join fetch professionnel.user",
        countQuery = "select count(professionnel) from Professionnel professionnel"
    )
    Page<Professionnel> findAllWithToOneRelationships(Pageable pageable);

    @Query("select professionnel from Professionnel professionnel left join fetch professionnel.user")
    List<Professionnel> findAllWithToOneRelationships();

    @Query("select professionnel from Professionnel professionnel left join fetch professionnel.user where professionnel.id =:id")
    Optional<Professionnel> findOneWithToOneRelationships(@Param("id") Long id);
}
