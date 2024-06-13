package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ServiceMFPAI;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServiceMFPAI entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceMFPAIRepository extends JpaRepository<ServiceMFPAI, Long> {}
