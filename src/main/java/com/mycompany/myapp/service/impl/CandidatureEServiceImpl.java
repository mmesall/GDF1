package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CandidatureE;
import com.mycompany.myapp.repository.CandidatureERepository;
import com.mycompany.myapp.service.CandidatureEService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.CandidatureE}.
 */
@Service
@Transactional
public class CandidatureEServiceImpl implements CandidatureEService {

    private final Logger log = LoggerFactory.getLogger(CandidatureEServiceImpl.class);

    private final CandidatureERepository candidatureERepository;

    public CandidatureEServiceImpl(CandidatureERepository candidatureERepository) {
        this.candidatureERepository = candidatureERepository;
    }

    @Override
    public CandidatureE save(CandidatureE candidatureE) {
        log.debug("Request to save CandidatureE : {}", candidatureE);
        return candidatureERepository.save(candidatureE);
    }

    @Override
    public CandidatureE update(CandidatureE candidatureE) {
        log.debug("Request to update CandidatureE : {}", candidatureE);
        return candidatureERepository.save(candidatureE);
    }

    @Override
    public Optional<CandidatureE> partialUpdate(CandidatureE candidatureE) {
        log.debug("Request to partially update CandidatureE : {}", candidatureE);

        return candidatureERepository
            .findById(candidatureE.getId())
            .map(existingCandidatureE -> {
                if (candidatureE.getOffreFormation() != null) {
                    existingCandidatureE.setOffreFormation(candidatureE.getOffreFormation());
                }
                if (candidatureE.getDateDebutOffre() != null) {
                    existingCandidatureE.setDateDebutOffre(candidatureE.getDateDebutOffre());
                }
                if (candidatureE.getDateFinOffre() != null) {
                    existingCandidatureE.setDateFinOffre(candidatureE.getDateFinOffre());
                }
                if (candidatureE.getDateDepot() != null) {
                    existingCandidatureE.setDateDepot(candidatureE.getDateDepot());
                }
                if (candidatureE.getResultat() != null) {
                    existingCandidatureE.setResultat(candidatureE.getResultat());
                }

                return existingCandidatureE;
            })
            .map(candidatureERepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidatureE> findAll(Pageable pageable) {
        log.debug("Request to get all CandidatureES");
        return candidatureERepository.findAll(pageable);
    }

    public Page<CandidatureE> findAllWithEagerRelationships(Pageable pageable) {
        return candidatureERepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidatureE> findOne(Long id) {
        log.debug("Request to get CandidatureE : {}", id);
        return candidatureERepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CandidatureE : {}", id);
        candidatureERepository.deleteById(id);
    }
}
