package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Formation;
import com.mycompany.myapp.repository.FormationRepository;
import com.mycompany.myapp.service.FormationService;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Formation}.
 */
@Service
@Transactional
public class FormationServiceImpl implements FormationService {

    private final Logger log = LoggerFactory.getLogger(FormationServiceImpl.class);

    private final FormationRepository formationRepository;

    public FormationServiceImpl(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public Formation save(Formation formation) {
        log.debug("Request to save Formation : {}", formation);
        return formationRepository.save(formation);
    }

    @Override
    public Formation update(Formation formation) {
        log.debug("Request to update Formation : {}", formation);
        return formationRepository.save(formation);
    }

    @Override
    public Optional<Formation> partialUpdate(Formation formation) {
        log.debug("Request to partially update Formation : {}", formation);

        return formationRepository
            .findById(formation.getId())
            .map(existingFormation -> {
                if (formation.getNomFormation() != null) {
                    existingFormation.setNomFormation(formation.getNomFormation());
                }
                if (formation.getImageFormation() != null) {
                    existingFormation.setImageFormation(formation.getImageFormation());
                }
                if (formation.getImageFormationContentType() != null) {
                    existingFormation.setImageFormationContentType(formation.getImageFormationContentType());
                }
                if (formation.getTypeFormation() != null) {
                    existingFormation.setTypeFormation(formation.getTypeFormation());
                }
                if (formation.getDuree() != null) {
                    existingFormation.setDuree(formation.getDuree());
                }
                if (formation.getAdmission() != null) {
                    existingFormation.setAdmission(formation.getAdmission());
                }
                if (formation.getDiplomeRequis() != null) {
                    existingFormation.setDiplomeRequis(formation.getDiplomeRequis());
                }
                if (formation.getFicheFormation() != null) {
                    existingFormation.setFicheFormation(formation.getFicheFormation());
                }
                if (formation.getFicheFormationContentType() != null) {
                    existingFormation.setFicheFormationContentType(formation.getFicheFormationContentType());
                }

                return existingFormation;
            })
            .map(formationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Formation> findAll(Pageable pageable) {
        log.debug("Request to get all Formations");
        return formationRepository.findAll(pageable);
    }

    public Page<Formation> findAllWithEagerRelationships(Pageable pageable) {
        return formationRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the formations where PriseEnCharge is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Formation> findAllWherePriseEnChargeIsNull() {
        log.debug("Request to get all formations where PriseEnCharge is null");
        return StreamSupport.stream(formationRepository.findAll().spliterator(), false)
            .filter(formation -> formation.getPriseEnCharge() == null)
            .toList();
    }

    /**
     *  Get all the formations where FormationInitiale is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Formation> findAllWhereFormationInitialeIsNull() {
        log.debug("Request to get all formations where FormationInitiale is null");
        return StreamSupport.stream(formationRepository.findAll().spliterator(), false)
            .filter(formation -> formation.getFormationInitiale() == null)
            .toList();
    }

    /**
     *  Get all the formations where FormationContinue is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Formation> findAllWhereFormationContinueIsNull() {
        log.debug("Request to get all formations where FormationContinue is null");
        return StreamSupport.stream(formationRepository.findAll().spliterator(), false)
            .filter(formation -> formation.getFormationContinue() == null)
            .toList();
    }

    /**
     *  Get all the formations where Concours is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Formation> findAllWhereConcoursIsNull() {
        log.debug("Request to get all formations where Concours is null");
        return StreamSupport.stream(formationRepository.findAll().spliterator(), false)
            .filter(formation -> formation.getConcours() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Formation> findOne(Long id) {
        log.debug("Request to get Formation : {}", id);
        return formationRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Formation : {}", id);
        formationRepository.deleteById(id);
    }
}
