package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CandidatureP;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CandidatureP entity.
 */
@Repository
public interface CandidaturePRepository extends JpaRepository<CandidatureP, Long> {
    default Optional<CandidatureP> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CandidatureP> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CandidatureP> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select candidatureP from CandidatureP candidatureP left join fetch candidatureP.professionnel left join fetch candidatureP.formationContinue left join fetch candidatureP.etablissement",
        countQuery = "select count(candidatureP) from CandidatureP candidatureP"
    )
    Page<CandidatureP> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select candidatureP from CandidatureP candidatureP left join fetch candidatureP.professionnel left join fetch candidatureP.formationContinue left join fetch candidatureP.etablissement"
    )
    List<CandidatureP> findAllWithToOneRelationships();

    @Query(
        "select candidatureP from CandidatureP candidatureP left join fetch candidatureP.professionnel left join fetch candidatureP.formationContinue left join fetch candidatureP.etablissement where candidatureP.id =:id"
    )
    Optional<CandidatureP> findOneWithToOneRelationships(@Param("id") Long id);
}
