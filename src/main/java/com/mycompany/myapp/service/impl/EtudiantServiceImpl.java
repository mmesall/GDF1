package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Etudiant;
import com.mycompany.myapp.repository.EtudiantRepository;
import com.mycompany.myapp.service.EtudiantService;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Etudiant}.
 */
@Service
@Transactional
public class EtudiantServiceImpl implements EtudiantService {

    private final Logger log = LoggerFactory.getLogger(EtudiantServiceImpl.class);

    private final EtudiantRepository etudiantRepository;

    public EtudiantServiceImpl(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public Etudiant save(Etudiant etudiant) {
        log.debug("Request to save Etudiant : {}", etudiant);
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant update(Etudiant etudiant) {
        log.debug("Request to update Etudiant : {}", etudiant);
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Optional<Etudiant> partialUpdate(Etudiant etudiant) {
        log.debug("Request to partially update Etudiant : {}", etudiant);

        return etudiantRepository
            .findById(etudiant.getId())
            .map(existingEtudiant -> {
                if (etudiant.getCarteEtudiant() != null) {
                    existingEtudiant.setCarteEtudiant(etudiant.getCarteEtudiant());
                }
                if (etudiant.getNom() != null) {
                    existingEtudiant.setNom(etudiant.getNom());
                }
                if (etudiant.getPrenom() != null) {
                    existingEtudiant.setPrenom(etudiant.getPrenom());
                }
                if (etudiant.getDateNaiss() != null) {
                    existingEtudiant.setDateNaiss(etudiant.getDateNaiss());
                }
                if (etudiant.getLieuNaiss() != null) {
                    existingEtudiant.setLieuNaiss(etudiant.getLieuNaiss());
                }
                if (etudiant.getSexe() != null) {
                    existingEtudiant.setSexe(etudiant.getSexe());
                }
                if (etudiant.getTelephone() != null) {
                    existingEtudiant.setTelephone(etudiant.getTelephone());
                }
                if (etudiant.getAdressePhysique() != null) {
                    existingEtudiant.setAdressePhysique(etudiant.getAdressePhysique());
                }
                if (etudiant.getRegionResidence() != null) {
                    existingEtudiant.setRegionResidence(etudiant.getRegionResidence());
                }
                if (etudiant.getDepartResidence() != null) {
                    existingEtudiant.setDepartResidence(etudiant.getDepartResidence());
                }
                if (etudiant.getEmail() != null) {
                    existingEtudiant.setEmail(etudiant.getEmail());
                }
                if (etudiant.getCni() != null) {
                    existingEtudiant.setCni(etudiant.getCni());
                }

                return existingEtudiant;
            })
            .map(etudiantRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etudiant> findAll(Pageable pageable) {
        log.debug("Request to get all Etudiants");
        return etudiantRepository.findAll(pageable);
    }

    public Page<Etudiant> findAllWithEagerRelationships(Pageable pageable) {
        return etudiantRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the etudiants where Dossier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Etudiant> findAllWhereDossierIsNull() {
        log.debug("Request to get all etudiants where Dossier is null");
        return StreamSupport.stream(etudiantRepository.findAll().spliterator(), false)
            .filter(etudiant -> etudiant.getDossier() == null)
            .toList();
    }

    /**
     *  Get all the etudiants where Demandeur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Etudiant> findAllWhereDemandeurIsNull() {
        log.debug("Request to get all etudiants where Demandeur is null");
        return StreamSupport.stream(etudiantRepository.findAll().spliterator(), false)
            .filter(etudiant -> etudiant.getDemandeur() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Etudiant> findOne(Long id) {
        log.debug("Request to get Etudiant : {}", id);
        return etudiantRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etudiant : {}", id);
        etudiantRepository.deleteById(id);
    }
}
