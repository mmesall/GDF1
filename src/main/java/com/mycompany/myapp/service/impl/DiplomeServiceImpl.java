package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Diplome;
import com.mycompany.myapp.repository.DiplomeRepository;
import com.mycompany.myapp.service.DiplomeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Diplome}.
 */
@Service
@Transactional
public class DiplomeServiceImpl implements DiplomeService {

    private final Logger log = LoggerFactory.getLogger(DiplomeServiceImpl.class);

    private final DiplomeRepository diplomeRepository;

    public DiplomeServiceImpl(DiplomeRepository diplomeRepository) {
        this.diplomeRepository = diplomeRepository;
    }

    @Override
    public Diplome save(Diplome diplome) {
        log.debug("Request to save Diplome : {}", diplome);
        return diplomeRepository.save(diplome);
    }

    @Override
    public Diplome update(Diplome diplome) {
        log.debug("Request to update Diplome : {}", diplome);
        return diplomeRepository.save(diplome);
    }

    @Override
    public Optional<Diplome> partialUpdate(Diplome diplome) {
        log.debug("Request to partially update Diplome : {}", diplome);

        return diplomeRepository
            .findById(diplome.getId())
            .map(existingDiplome -> {
                if (diplome.getIntitule() != null) {
                    existingDiplome.setIntitule(diplome.getIntitule());
                }
                if (diplome.getDomaine() != null) {
                    existingDiplome.setDomaine(diplome.getDomaine());
                }
                if (diplome.getNiveau() != null) {
                    existingDiplome.setNiveau(diplome.getNiveau());
                }
                if (diplome.getMention() != null) {
                    existingDiplome.setMention(diplome.getMention());
                }
                if (diplome.getAnneeObtention() != null) {
                    existingDiplome.setAnneeObtention(diplome.getAnneeObtention());
                }
                if (diplome.getEtablissement() != null) {
                    existingDiplome.setEtablissement(diplome.getEtablissement());
                }
                if (diplome.getDocument() != null) {
                    existingDiplome.setDocument(diplome.getDocument());
                }
                if (diplome.getDocumentContentType() != null) {
                    existingDiplome.setDocumentContentType(diplome.getDocumentContentType());
                }

                return existingDiplome;
            })
            .map(diplomeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Diplome> findAll(Pageable pageable) {
        log.debug("Request to get all Diplomes");
        return diplomeRepository.findAll(pageable);
    }

    public Page<Diplome> findAllWithEagerRelationships(Pageable pageable) {
        return diplomeRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Diplome> findOne(Long id) {
        log.debug("Request to get Diplome : {}", id);
        return diplomeRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diplome : {}", id);
        diplomeRepository.deleteById(id);
    }
}
