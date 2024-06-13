package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Concours;
import com.mycompany.myapp.repository.ConcoursRepository;
import com.mycompany.myapp.service.ConcoursService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Concours}.
 */
@Service
@Transactional
public class ConcoursServiceImpl implements ConcoursService {

    private final Logger log = LoggerFactory.getLogger(ConcoursServiceImpl.class);

    private final ConcoursRepository concoursRepository;

    public ConcoursServiceImpl(ConcoursRepository concoursRepository) {
        this.concoursRepository = concoursRepository;
    }

    @Override
    public Concours save(Concours concours) {
        log.debug("Request to save Concours : {}", concours);
        return concoursRepository.save(concours);
    }

    @Override
    public Concours update(Concours concours) {
        log.debug("Request to update Concours : {}", concours);
        return concoursRepository.save(concours);
    }

    @Override
    public Optional<Concours> partialUpdate(Concours concours) {
        log.debug("Request to partially update Concours : {}", concours);

        return concoursRepository
            .findById(concours.getId())
            .map(existingConcours -> {
                if (concours.getNomConcours() != null) {
                    existingConcours.setNomConcours(concours.getNomConcours());
                }
                if (concours.getNomEtablissement() != null) {
                    existingConcours.setNomEtablissement(concours.getNomEtablissement());
                }
                if (concours.getNiveauEtude() != null) {
                    existingConcours.setNiveauEtude(concours.getNiveauEtude());
                }
                if (concours.getDateOuverture() != null) {
                    existingConcours.setDateOuverture(concours.getDateOuverture());
                }
                if (concours.getDateCloture() != null) {
                    existingConcours.setDateCloture(concours.getDateCloture());
                }
                if (concours.getDateConcours() != null) {
                    existingConcours.setDateConcours(concours.getDateConcours());
                }
                if (concours.getAffiche() != null) {
                    existingConcours.setAffiche(concours.getAffiche());
                }
                if (concours.getAfficheContentType() != null) {
                    existingConcours.setAfficheContentType(concours.getAfficheContentType());
                }

                return existingConcours;
            })
            .map(concoursRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Concours> findAll(Pageable pageable) {
        log.debug("Request to get all Concours");
        return concoursRepository.findAll(pageable);
    }

    public Page<Concours> findAllWithEagerRelationships(Pageable pageable) {
        return concoursRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Concours> findOne(Long id) {
        log.debug("Request to get Concours : {}", id);
        return concoursRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concours : {}", id);
        concoursRepository.deleteById(id);
    }
}
