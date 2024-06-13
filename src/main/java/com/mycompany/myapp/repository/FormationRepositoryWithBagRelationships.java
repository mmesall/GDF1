package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Formation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FormationRepositoryWithBagRelationships {
    Optional<Formation> fetchBagRelationships(Optional<Formation> formation);

    List<Formation> fetchBagRelationships(List<Formation> formations);

    Page<Formation> fetchBagRelationships(Page<Formation> formations);
}
