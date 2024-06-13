package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Demandeur;
import com.mycompany.myapp.repository.DemandeurRepository;
import com.mycompany.myapp.service.DemandeurService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Demandeur}.
 */
@Service
@Transactional
public class DemandeurServiceImpl implements DemandeurService {

    private final Logger log = LoggerFactory.getLogger(DemandeurServiceImpl.class);

    private final DemandeurRepository demandeurRepository;

    public DemandeurServiceImpl(DemandeurRepository demandeurRepository) {
        this.demandeurRepository = demandeurRepository;
    }

    @Override
    public Demandeur save(Demandeur demandeur) {
        log.debug("Request to save Demandeur : {}", demandeur);
        return demandeurRepository.save(demandeur);
    }

    @Override
    public Demandeur update(Demandeur demandeur) {
        log.debug("Request to update Demandeur : {}", demandeur);
        return demandeurRepository.save(demandeur);
    }

    @Override
    public Optional<Demandeur> partialUpdate(Demandeur demandeur) {
        log.debug("Request to partially update Demandeur : {}", demandeur);

        return demandeurRepository
            .findById(demandeur.getId())
            .map(existingDemandeur -> {
                if (demandeur.getNom() != null) {
                    existingDemandeur.setNom(demandeur.getNom());
                }
                if (demandeur.getPrenom() != null) {
                    existingDemandeur.setPrenom(demandeur.getPrenom());
                }
                if (demandeur.getDateNaiss() != null) {
                    existingDemandeur.setDateNaiss(demandeur.getDateNaiss());
                }
                if (demandeur.getLieuNaiss() != null) {
                    existingDemandeur.setLieuNaiss(demandeur.getLieuNaiss());
                }
                if (demandeur.getSexe() != null) {
                    existingDemandeur.setSexe(demandeur.getSexe());
                }
                if (demandeur.getTelephone() != null) {
                    existingDemandeur.setTelephone(demandeur.getTelephone());
                }
                if (demandeur.getEmail() != null) {
                    existingDemandeur.setEmail(demandeur.getEmail());
                }
                if (demandeur.getProfil() != null) {
                    existingDemandeur.setProfil(demandeur.getProfil());
                }

                return existingDemandeur;
            })
            .map(demandeurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Demandeur> findAll(Pageable pageable) {
        log.debug("Request to get all Demandeurs");
        return demandeurRepository.findAll(pageable);
    }

    public Page<Demandeur> findAllWithEagerRelationships(Pageable pageable) {
        return demandeurRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Demandeur> findOne(Long id) {
        log.debug("Request to get Demandeur : {}", id);
        return demandeurRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Demandeur : {}", id);
        demandeurRepository.deleteById(id);
    }
}
