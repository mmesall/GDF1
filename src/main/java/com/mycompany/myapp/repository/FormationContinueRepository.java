package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FormationContinue;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FormationContinue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormationContinueRepository extends JpaRepository<FormationContinue, Long> {}
