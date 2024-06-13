package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Diplome;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Diplome entity.
 */
@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {
    default Optional<Diplome> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Diplome> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Diplome> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select diplome from Diplome diplome left join fetch diplome.eleve left join fetch diplome.etudiant left join fetch diplome.professionnel left join fetch diplome.demandeur",
        countQuery = "select count(diplome) from Diplome diplome"
    )
    Page<Diplome> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select diplome from Diplome diplome left join fetch diplome.eleve left join fetch diplome.etudiant left join fetch diplome.professionnel left join fetch diplome.demandeur"
    )
    List<Diplome> findAllWithToOneRelationships();

    @Query(
        "select diplome from Diplome diplome left join fetch diplome.eleve left join fetch diplome.etudiant left join fetch diplome.professionnel left join fetch diplome.demandeur where diplome.id =:id"
    )
    Optional<Diplome> findOneWithToOneRelationships(@Param("id") Long id);
}
