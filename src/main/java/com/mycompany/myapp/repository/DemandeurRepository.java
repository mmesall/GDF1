package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Demandeur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Demandeur entity.
 */
@Repository
public interface DemandeurRepository extends JpaRepository<Demandeur, Long> {
    default Optional<Demandeur> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Demandeur> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Demandeur> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select demandeur from Demandeur demandeur left join fetch demandeur.user left join fetch demandeur.dossier left join fetch demandeur.eleve left join fetch demandeur.etudiant left join fetch demandeur.professionnel",
        countQuery = "select count(demandeur) from Demandeur demandeur"
    )
    Page<Demandeur> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select demandeur from Demandeur demandeur left join fetch demandeur.user left join fetch demandeur.dossier left join fetch demandeur.eleve left join fetch demandeur.etudiant left join fetch demandeur.professionnel"
    )
    List<Demandeur> findAllWithToOneRelationships();

    @Query(
        "select demandeur from Demandeur demandeur left join fetch demandeur.user left join fetch demandeur.dossier left join fetch demandeur.eleve left join fetch demandeur.etudiant left join fetch demandeur.professionnel where demandeur.id =:id"
    )
    Optional<Demandeur> findOneWithToOneRelationships(@Param("id") Long id);
}
