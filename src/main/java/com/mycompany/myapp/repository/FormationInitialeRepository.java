package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FormationInitiale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormationInitiale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationInitialeRepository extends JpaRepository<FormationInitiale, Long> {}
