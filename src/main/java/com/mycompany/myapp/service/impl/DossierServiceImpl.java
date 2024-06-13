package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Dossier;
import com.mycompany.myapp.repository.DossierRepository;
import com.mycompany.myapp.service.DossierService;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Dossier}.
 */
@Service
@Transactional
public class DossierServiceImpl implements DossierService {

    private final Logger log = LoggerFactory.getLogger(DossierServiceImpl.class);

    private final DossierRepository dossierRepository;

    public DossierServiceImpl(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @Override
    public Dossier save(Dossier dossier) {
        log.debug("Request to save Dossier : {}", dossier);
        return dossierRepository.save(dossier);
    }

    @Override
    public Dossier update(Dossier dossier) {
        log.debug("Request to update Dossier : {}", dossier);
        return dossierRepository.save(dossier);
    }

    @Override
    public Optional<Dossier> partialUpdate(Dossier dossier) {
        log.debug("Request to partially update Dossier : {}", dossier);

        return dossierRepository
            .findById(dossier.getId())
            .map(existingDossier -> {
                if (dossier.getNumDossier() != null) {
                    existingDossier.setNumDossier(dossier.getNumDossier());
                }
                if (dossier.getPrenom() != null) {
                    existingDossier.setPrenom(dossier.getPrenom());
                }
                if (dossier.getNom() != null) {
                    existingDossier.setNom(dossier.getNom());
                }
                if (dossier.getNomUtilisateur() != null) {
                    existingDossier.setNomUtilisateur(dossier.getNomUtilisateur());
                }
                if (dossier.getDateNaiss() != null) {
                    existingDossier.setDateNaiss(dossier.getDateNaiss());
                }
                if (dossier.getLieuNaiss() != null) {
                    existingDossier.setLieuNaiss(dossier.getLieuNaiss());
                }
                if (dossier.getRegionNaiss() != null) {
                    existingDossier.setRegionNaiss(dossier.getRegionNaiss());
                }
                if (dossier.getDepartementNaiss() != null) {
                    existingDossier.setDepartementNaiss(dossier.getDepartementNaiss());
                }
                if (dossier.getTypePiece() != null) {
                    existingDossier.setTypePiece(dossier.getTypePiece());
                }
                if (dossier.getNumeroPiece() != null) {
                    existingDossier.setNumeroPiece(dossier.getNumeroPiece());
                }
                if (dossier.getSexe() != null) {
                    existingDossier.setSexe(dossier.getSexe());
                }
                if (dossier.getRegionResidence() != null) {
                    existingDossier.setRegionResidence(dossier.getRegionResidence());
                }
                if (dossier.getDepResidence() != null) {
                    existingDossier.setDepResidence(dossier.getDepResidence());
                }
                if (dossier.getAdresseResidence() != null) {
                    existingDossier.setAdresseResidence(dossier.getAdresseResidence());
                }
                if (dossier.getTelephone1() != null) {
                    existingDossier.setTelephone1(dossier.getTelephone1());
                }
                if (dossier.getTelephone2() != null) {
                    existingDossier.setTelephone2(dossier.getTelephone2());
                }
                if (dossier.getEmail() != null) {
                    existingDossier.setEmail(dossier.getEmail());
                }
                if (dossier.getNiveauFormation() != null) {
                    existingDossier.setNiveauFormation(dossier.getNiveauFormation());
                }
                if (dossier.getSpecialite() != null) {
                    existingDossier.setSpecialite(dossier.getSpecialite());
                }
                if (dossier.getIntituleDiplome() != null) {
                    existingDossier.setIntituleDiplome(dossier.getIntituleDiplome());
                }
                if (dossier.getDiplome() != null) {
                    existingDossier.setDiplome(dossier.getDiplome());
                }
                if (dossier.getDiplomeContentType() != null) {
                    existingDossier.setDiplomeContentType(dossier.getDiplomeContentType());
                }
                if (dossier.getAnneeObtention() != null) {
                    existingDossier.setAnneeObtention(dossier.getAnneeObtention());
                }
                if (dossier.getLieuObtention() != null) {
                    existingDossier.setLieuObtention(dossier.getLieuObtention());
                }
                if (dossier.getCv() != null) {
                    existingDossier.setCv(dossier.getCv());
                }
                if (dossier.getCvContentType() != null) {
                    existingDossier.setCvContentType(dossier.getCvContentType());
                }
                if (dossier.getLettreMotivation() != null) {
                    existingDossier.setLettreMotivation(dossier.getLettreMotivation());
                }
                if (dossier.getLettreMotivationContentType() != null) {
                    existingDossier.setLettreMotivationContentType(dossier.getLettreMotivationContentType());
                }
                if (dossier.getProfession() != null) {
                    existingDossier.setProfession(dossier.getProfession());
                }
                if (dossier.getAutreSpecialite() != null) {
                    existingDossier.setAutreSpecialite(dossier.getAutreSpecialite());
                }
                if (dossier.getNomCompetence() != null) {
                    existingDossier.setNomCompetence(dossier.getNomCompetence());
                }
                if (dossier.getNiveauCompetence() != null) {
                    existingDossier.setNiveauCompetence(dossier.getNiveauCompetence());
                }
                if (dossier.getIntituleExperience() != null) {
                    existingDossier.setIntituleExperience(dossier.getIntituleExperience());
                }
                if (dossier.getPosteOccupe() != null) {
                    existingDossier.setPosteOccupe(dossier.getPosteOccupe());
                }
                if (dossier.getDateDebut() != null) {
                    existingDossier.setDateDebut(dossier.getDateDebut());
                }
                if (dossier.getDateFin() != null) {
                    existingDossier.setDateFin(dossier.getDateFin());
                }
                if (dossier.getNomEntreprise() != null) {
                    existingDossier.setNomEntreprise(dossier.getNomEntreprise());
                }
                if (dossier.getMission() != null) {
                    existingDossier.setMission(dossier.getMission());
                }

                return existingDossier;
            })
            .map(dossierRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Dossier> findAll(Pageable pageable) {
        log.debug("Request to get all Dossiers");
        return dossierRepository.findAll(pageable);
    }

    public Page<Dossier> findAllWithEagerRelationships(Pageable pageable) {
        return dossierRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the dossiers where Demandeur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Dossier> findAllWhereDemandeurIsNull() {
        log.debug("Request to get all dossiers where Demandeur is null");
        return StreamSupport.stream(dossierRepository.findAll().spliterator(), false)
            .filter(dossier -> dossier.getDemandeur() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Dossier> findOne(Long id) {
        log.debug("Request to get Dossier : {}", id);
        return dossierRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dossier : {}", id);
        dossierRepository.deleteById(id);
    }
}
