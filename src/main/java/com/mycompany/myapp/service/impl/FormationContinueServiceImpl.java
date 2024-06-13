package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FormationContinue;
import com.mycompany.myapp.repository.FormationContinueRepository;
import com.mycompany.myapp.service.FormationContinueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.FormationContinue}.
 */
@Service
@Transactional
public class FormationContinueServiceImpl implements FormationContinueService {

    private final Logger log = LoggerFactory.getLogger(FormationContinueServiceImpl.class);

    private final FormationContinueRepository formationContinueRepository;

    public FormationContinueServiceImpl(FormationContinueRepository formationContinueRepository) {
        this.formationContinueRepository = formationContinueRepository;
    }

    @Override
    public FormationContinue save(FormationContinue formationContinue) {
        log.debug("Request to save FormationContinue : {}", formationContinue);
        return formationContinueRepository.save(formationContinue);
    }

    @Override
    public FormationContinue update(FormationContinue formationContinue) {
        log.debug("Request to update FormationContinue : {}", formationContinue);
        return formationContinueRepository.save(formationContinue);
    }

    @Override
    public Optional<FormationContinue> partialUpdate(FormationContinue formationContinue) {
        log.debug("Request to partially update FormationContinue : {}", formationContinue);

        return formationContinueRepository
            .findById(formationContinue.getId())
            .map(existingFormationContinue -> {
                if (formationContinue.getNomFormationC() != null) {
                    existingFormationContinue.setNomFormationC(formationContinue.getNomFormationC());
                }
                if (formationContinue.getDuree() != null) {
                    existingFormationContinue.setDuree(formationContinue.getDuree());
                }
                if (formationContinue.getAdmission() != null) {
                    existingFormationContinue.setAdmission(formationContinue.getAdmission());
                }
                if (formationContinue.getDiplomeRequis() != null) {
                    existingFormationContinue.setDiplomeRequis(formationContinue.getDiplomeRequis());
                }
                if (formationContinue.getNiveauEtude() != null) {
                    existingFormationContinue.setNiveauEtude(formationContinue.getNiveauEtude());
                }
                if (formationContinue.getFiliere() != null) {
                    existingFormationContinue.setFiliere(formationContinue.getFiliere());
                }
                if (formationContinue.getSerie() != null) {
                    existingFormationContinue.setSerie(formationContinue.getSerie());
                }
                if (formationContinue.getCfp() != null) {
                    existingFormationContinue.setCfp(formationContinue.getCfp());
                }
                if (formationContinue.getLycee() != null) {
                    existingFormationContinue.setLycee(formationContinue.getLycee());
                }
                if (formationContinue.getFicheFormation() != null) {
                    existingFormationContinue.setFicheFormation(formationContinue.getFicheFormation());
                }
                if (formationContinue.getFicheFormationContentType() != null) {
                    existingFormationContinue.setFicheFormationContentType(formationContinue.getFicheFormationContentType());
                }
                if (formationContinue.getLibellePC() != null) {
                    existingFormationContinue.setLibellePC(formationContinue.getLibellePC());
                }
                if (formationContinue.getMontantPriseEnCharge() != null) {
                    existingFormationContinue.setMontantPriseEnCharge(formationContinue.getMontantPriseEnCharge());
                }
                if (formationContinue.getCoutFormation() != null) {
                    existingFormationContinue.setCoutFormation(formationContinue.getCoutFormation());
                }
                if (formationContinue.getDetailPC() != null) {
                    existingFormationContinue.setDetailPC(formationContinue.getDetailPC());
                }
                if (formationContinue.getNomDiplome() != null) {
                    existingFormationContinue.setNomDiplome(formationContinue.getNomDiplome());
                }
                if (formationContinue.getAutreDiplome() != null) {
                    existingFormationContinue.setAutreDiplome(formationContinue.getAutreDiplome());
                }
                if (formationContinue.getNomDebouche() != null) {
                    existingFormationContinue.setNomDebouche(formationContinue.getNomDebouche());
                }

                return existingFormationContinue;
            })
            .map(formationContinueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FormationContinue> findAll(Pageable pageable) {
        log.debug("Request to get all FormationContinues");
        return formationContinueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FormationContinue> findOne(Long id) {
        log.debug("Request to get FormationContinue : {}", id);
        return formationContinueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FormationContinue : {}", id);
        formationContinueRepository.deleteById(id);
    }
}
