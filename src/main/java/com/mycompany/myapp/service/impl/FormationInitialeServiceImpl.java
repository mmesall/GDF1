package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FormationInitiale;
import com.mycompany.myapp.repository.FormationInitialeRepository;
import com.mycompany.myapp.service.FormationInitialeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.FormationInitiale}.
 */
@Service
@Transactional
public class FormationInitialeServiceImpl implements FormationInitialeService {

    private final Logger log = LoggerFactory.getLogger(FormationInitialeServiceImpl.class);

    private final FormationInitialeRepository formationInitialeRepository;

    public FormationInitialeServiceImpl(FormationInitialeRepository formationInitialeRepository) {
        this.formationInitialeRepository = formationInitialeRepository;
    }

    @Override
    public FormationInitiale save(FormationInitiale formationInitiale) {
        log.debug("Request to save FormationInitiale : {}", formationInitiale);
        return formationInitialeRepository.save(formationInitiale);
    }

    @Override
    public FormationInitiale update(FormationInitiale formationInitiale) {
        log.debug("Request to update FormationInitiale : {}", formationInitiale);
        return formationInitialeRepository.save(formationInitiale);
    }

    @Override
    public Optional<FormationInitiale> partialUpdate(FormationInitiale formationInitiale) {
        log.debug("Request to partially update FormationInitiale : {}", formationInitiale);

        return formationInitialeRepository
            .findById(formationInitiale.getId())
            .map(existingFormationInitiale -> {
                if (formationInitiale.getNomFormationI() != null) {
                    existingFormationInitiale.setNomFormationI(formationInitiale.getNomFormationI());
                }
                if (formationInitiale.getDuree() != null) {
                    existingFormationInitiale.setDuree(formationInitiale.getDuree());
                }
                if (formationInitiale.getAdmission() != null) {
                    existingFormationInitiale.setAdmission(formationInitiale.getAdmission());
                }
                if (formationInitiale.getDiplomeRequis() != null) {
                    existingFormationInitiale.setDiplomeRequis(formationInitiale.getDiplomeRequis());
                }
                if (formationInitiale.getNiveauEtude() != null) {
                    existingFormationInitiale.setNiveauEtude(formationInitiale.getNiveauEtude());
                }
                if (formationInitiale.getFicheFormation() != null) {
                    existingFormationInitiale.setFicheFormation(formationInitiale.getFicheFormation());
                }
                if (formationInitiale.getFicheFormationContentType() != null) {
                    existingFormationInitiale.setFicheFormationContentType(formationInitiale.getFicheFormationContentType());
                }
                if (formationInitiale.getFiliere() != null) {
                    existingFormationInitiale.setFiliere(formationInitiale.getFiliere());
                }
                if (formationInitiale.getSerie() != null) {
                    existingFormationInitiale.setSerie(formationInitiale.getSerie());
                }
                if (formationInitiale.getCfp() != null) {
                    existingFormationInitiale.setCfp(formationInitiale.getCfp());
                }
                if (formationInitiale.getLycee() != null) {
                    existingFormationInitiale.setLycee(formationInitiale.getLycee());
                }
                if (formationInitiale.getNomConcours() != null) {
                    existingFormationInitiale.setNomConcours(formationInitiale.getNomConcours());
                }
                if (formationInitiale.getDateOuverture() != null) {
                    existingFormationInitiale.setDateOuverture(formationInitiale.getDateOuverture());
                }
                if (formationInitiale.getDateCloture() != null) {
                    existingFormationInitiale.setDateCloture(formationInitiale.getDateCloture());
                }
                if (formationInitiale.getDateConcours() != null) {
                    existingFormationInitiale.setDateConcours(formationInitiale.getDateConcours());
                }
                if (formationInitiale.getNomDiplome() != null) {
                    existingFormationInitiale.setNomDiplome(formationInitiale.getNomDiplome());
                }
                if (formationInitiale.getNomDebouche() != null) {
                    existingFormationInitiale.setNomDebouche(formationInitiale.getNomDebouche());
                }

                return existingFormationInitiale;
            })
            .map(formationInitialeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationInitiale> findAll(Pageable pageable) {
        log.debug("Request to get all FormationInitiales");
        return formationInitialeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormationInitiale> findOne(Long id) {
        log.debug("Request to get FormationInitiale : {}", id);
        return formationInitialeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormationInitiale : {}", id);
        formationInitialeRepository.deleteById(id);
    }
}
