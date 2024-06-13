package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ServiceMFPAI;
import com.mycompany.myapp.repository.ServiceMFPAIRepository;
import com.mycompany.myapp.service.ServiceMFPAIService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.ServiceMFPAI}.
 */
@Service
@Transactional
public class ServiceMFPAIServiceImpl implements ServiceMFPAIService {

    private final Logger log = LoggerFactory.getLogger(ServiceMFPAIServiceImpl.class);

    private final ServiceMFPAIRepository serviceMFPAIRepository;

    public ServiceMFPAIServiceImpl(ServiceMFPAIRepository serviceMFPAIRepository) {
        this.serviceMFPAIRepository = serviceMFPAIRepository;
    }

    @Override
    public ServiceMFPAI save(ServiceMFPAI serviceMFPAI) {
        log.debug("Request to save ServiceMFPAI : {}", serviceMFPAI);
        return serviceMFPAIRepository.save(serviceMFPAI);
    }

    @Override
    public ServiceMFPAI update(ServiceMFPAI serviceMFPAI) {
        log.debug("Request to update ServiceMFPAI : {}", serviceMFPAI);
        return serviceMFPAIRepository.save(serviceMFPAI);
    }

    @Override
    public Optional<ServiceMFPAI> partialUpdate(ServiceMFPAI serviceMFPAI) {
        log.debug("Request to partially update ServiceMFPAI : {}", serviceMFPAI);

        return serviceMFPAIRepository
            .findById(serviceMFPAI.getId())
            .map(existingServiceMFPAI -> {
                if (serviceMFPAI.getImageService() != null) {
                    existingServiceMFPAI.setImageService(serviceMFPAI.getImageService());
                }
                if (serviceMFPAI.getImageServiceContentType() != null) {
                    existingServiceMFPAI.setImageServiceContentType(serviceMFPAI.getImageServiceContentType());
                }
                if (serviceMFPAI.getNomService() != null) {
                    existingServiceMFPAI.setNomService(serviceMFPAI.getNomService());
                }
                if (serviceMFPAI.getChefService() != null) {
                    existingServiceMFPAI.setChefService(serviceMFPAI.getChefService());
                }
                if (serviceMFPAI.getDescription() != null) {
                    existingServiceMFPAI.setDescription(serviceMFPAI.getDescription());
                }

                return existingServiceMFPAI;
            })
            .map(serviceMFPAIRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ServiceMFPAI> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceMFPAIS");
        return serviceMFPAIRepository.findAll(pageable);
    }

    /**
     *  Get all the serviceMFPAIS where Agent is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceMFPAI> findAllWhereAgentIsNull() {
        log.debug("Request to get all serviceMFPAIS where Agent is null");
        return StreamSupport.stream(serviceMFPAIRepository.findAll().spliterator(), false)
            .filter(serviceMFPAI -> serviceMFPAI.getAgent() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceMFPAI> findOne(Long id) {
        log.debug("Request to get ServiceMFPAI : {}", id);
        return serviceMFPAIRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceMFPAI : {}", id);
        serviceMFPAIRepository.deleteById(id);
    }
}
