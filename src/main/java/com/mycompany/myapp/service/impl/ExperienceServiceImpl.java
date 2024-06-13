package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Experience;
import com.mycompany.myapp.repository.ExperienceRepository;
import com.mycompany.myapp.service.ExperienceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Experience}.
 */
@Service
@Transactional
public class ExperienceServiceImpl implements ExperienceService {

    private final Logger log = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    private final ExperienceRepository experienceRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public Experience save(Experience experience) {
        log.debug("Request to save Experience : {}", experience);
        return experienceRepository.save(experience);
    }

    @Override
    public Experience update(Experience experience) {
        log.debug("Request to update Experience : {}", experience);
        return experienceRepository.save(experience);
    }

    @Override
    public Optional<Experience> partialUpdate(Experience experience) {
        log.debug("Request to partially update Experience : {}", experience);

        return experienceRepository
            .findById(experience.getId())
            .map(existingExperience -> {
                if (experience.getDateDebut() != null) {
                    existingExperience.setDateDebut(experience.getDateDebut());
                }
                if (experience.getDateFin() != null) {
                    existingExperience.setDateFin(experience.getDateFin());
                }
                if (experience.getNomEntreprise() != null) {
                    existingExperience.setNomEntreprise(experience.getNomEntreprise());
                }
                if (experience.getPosteOccupe() != null) {
                    existingExperience.setPosteOccupe(experience.getPosteOccupe());
                }
                if (experience.getMission() != null) {
                    existingExperience.setMission(experience.getMission());
                }

                return existingExperience;
            })
            .map(experienceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Experience> findAll(Pageable pageable) {
        log.debug("Request to get all Experiences");
        return experienceRepository.findAll(pageable);
    }

    public Page<Experience> findAllWithEagerRelationships(Pageable pageable) {
        return experienceRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Experience> findOne(Long id) {
        log.debug("Request to get Experience : {}", id);
        return experienceRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Experience : {}", id);
        experienceRepository.deleteById(id);
    }
}
