package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Eleve;
import com.mycompany.myapp.repository.EleveRepository;
import com.mycompany.myapp.service.EleveService;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Eleve}.
 */
@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveServiceImpl.class);

    private final EleveRepository eleveRepository;

    public EleveServiceImpl(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    @Override
    public Eleve save(Eleve eleve) {
        log.debug("Request to save Eleve : {}", eleve);
        return eleveRepository.save(eleve);
    }

    @Override
    public Eleve update(Eleve eleve) {
        log.debug("Request to update Eleve : {}", eleve);
        return eleveRepository.save(eleve);
    }

    @Override
    public Optional<Eleve> partialUpdate(Eleve eleve) {
        log.debug("Request to partially update Eleve : {}", eleve);

        return eleveRepository
            .findById(eleve.getId())
            .map(existingEleve -> {
                if (eleve.getNom() != null) {
                    existingEleve.setNom(eleve.getNom());
                }
                if (eleve.getPrenom() != null) {
                    existingEleve.setPrenom(eleve.getPrenom());
                }
                if (eleve.getDateNaiss() != null) {
                    existingEleve.setDateNaiss(eleve.getDateNaiss());
                }
                if (eleve.getLieuNaiss() != null) {
                    existingEleve.setLieuNaiss(eleve.getLieuNaiss());
                }
                if (eleve.getSexe() != null) {
                    existingEleve.setSexe(eleve.getSexe());
                }
                if (eleve.getTelephone() != null) {
                    existingEleve.setTelephone(eleve.getTelephone());
                }
                if (eleve.getAdressePhysique() != null) {
                    existingEleve.setAdressePhysique(eleve.getAdressePhysique());
                }
                if (eleve.getRegionResidence() != null) {
                    existingEleve.setRegionResidence(eleve.getRegionResidence());
                }
                if (eleve.getDepartResidence() != null) {
                    existingEleve.setDepartResidence(eleve.getDepartResidence());
                }
                if (eleve.getNiveauEtude() != null) {
                    existingEleve.setNiveauEtude(eleve.getNiveauEtude());
                }
                if (eleve.getCni() != null) {
                    existingEleve.setCni(eleve.getCni());
                }

                return existingEleve;
            })
            .map(eleveRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Eleve> findAll(Pageable pageable) {
        log.debug("Request to get all Eleves");
        return eleveRepository.findAll(pageable);
    }

    public Page<Eleve> findAllWithEagerRelationships(Pageable pageable) {
        return eleveRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the eleves where Dossier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Eleve> findAllWhereDossierIsNull() {
        log.debug("Request to get all eleves where Dossier is null");
        return StreamSupport.stream(eleveRepository.findAll().spliterator(), false).filter(eleve -> eleve.getDossier() == null).toList();
    }

    /**
     *  Get all the eleves where Demandeur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Eleve> findAllWhereDemandeurIsNull() {
        log.debug("Request to get all eleves where Demandeur is null");
        return StreamSupport.stream(eleveRepository.findAll().spliterator(), false).filter(eleve -> eleve.getDemandeur() == null).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Eleve> findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        return eleveRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.deleteById(id);
    }
}
