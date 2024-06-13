package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CandidatureE;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CandidatureE entity.
 */
@Repository
public interface CandidatureERepository extends JpaRepository<CandidatureE, Long> {
    default Optional<CandidatureE> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CandidatureE> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CandidatureE> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select candidatureE from CandidatureE candidatureE left join fetch candidatureE.eleve left join fetch candidatureE.etudiant left join fetch candidatureE.formationInitiale left join fetch candidatureE.etablissement",
        countQuery = "select count(candidatureE) from CandidatureE candidatureE"
    )
    Page<CandidatureE> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select candidatureE from CandidatureE candidatureE left join fetch candidatureE.eleve left join fetch candidatureE.etudiant left join fetch candidatureE.formationInitiale left join fetch candidatureE.etablissement"
    )
    List<CandidatureE> findAllWithToOneRelationships();

    @Query(
        "select candidatureE from CandidatureE candidatureE left join fetch candidatureE.eleve left join fetch candidatureE.etudiant left join fetch candidatureE.formationInitiale left join fetch candidatureE.etablissement where candidatureE.id =:id"
    )
    Optional<CandidatureE> findOneWithToOneRelationships(@Param("id") Long id);
}
