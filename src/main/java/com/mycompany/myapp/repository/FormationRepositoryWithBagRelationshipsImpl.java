package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Formation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FormationRepositoryWithBagRelationshipsImpl implements FormationRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String FORMATIONS_PARAMETER = "formations";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Formation> fetchBagRelationships(Optional<Formation> formation) {
        return formation.map(this::fetchEtablissements);
    }

    @Override
    public Page<Formation> fetchBagRelationships(Page<Formation> formations) {
        return new PageImpl<>(fetchBagRelationships(formations.getContent()), formations.getPageable(), formations.getTotalElements());
    }

    @Override
    public List<Formation> fetchBagRelationships(List<Formation> formations) {
        return Optional.of(formations).map(this::fetchEtablissements).orElse(Collections.emptyList());
    }

    Formation fetchEtablissements(Formation result) {
        return entityManager
            .createQuery(
                "select formation from Formation formation left join fetch formation.etablissements where formation.id = :id",
                Formation.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Formation> fetchEtablissements(List<Formation> formations) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, formations.size()).forEach(index -> order.put(formations.get(index).getId(), index));
        List<Formation> result = entityManager
            .createQuery(
                "select formation from Formation formation left join fetch formation.etablissements where formation in :formations",
                Formation.class
            )
            .setParameter(FORMATIONS_PARAMETER, formations)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
