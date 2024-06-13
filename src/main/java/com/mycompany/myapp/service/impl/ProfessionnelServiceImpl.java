package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Professionnel;
import com.mycompany.myapp.repository.ProfessionnelRepository;
import com.mycompany.myapp.service.ProfessionnelService;
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
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Professionnel}.
 */
@Service
@Transactional
public class ProfessionnelServiceImpl implements ProfessionnelService {

    private final Logger log = LoggerFactory.getLogger(ProfessionnelServiceImpl.class);

    private final ProfessionnelRepository professionnelRepository;

    public ProfessionnelServiceImpl(ProfessionnelRepository professionnelRepository) {
        this.professionnelRepository = professionnelRepository;
    }

    @Override
    public Professionnel save(Professionnel professionnel) {
        log.debug("Request to save Professionnel : {}", professionnel);
        return professionnelRepository.save(professionnel);
    }

    @Override
    public Professionnel update(Professionnel professionnel) {
        log.debug("Request to update Professionnel : {}", professionnel);
        return professionnelRepository.save(professionnel);
    }

    @Override
    public Optional<Professionnel> partialUpdate(Professionnel professionnel) {
        log.debug("Request to partially update Professionnel : {}", professionnel);

        return professionnelRepository
            .findById(professionnel.getId())
            .map(existingProfessionnel -> {
                if (professionnel.getProfession() != null) {
                    existingProfessionnel.setProfession(professionnel.getProfession());
                }
                if (professionnel.getNom() != null) {
                    existingProfessionnel.setNom(professionnel.getNom());
                }
                if (professionnel.getPrenom() != null) {
                    existingProfessionnel.setPrenom(professionnel.getPrenom());
                }
                if (professionnel.getDateNaiss() != null) {
                    existingProfessionnel.setDateNaiss(professionnel.getDateNaiss());
                }
                if (professionnel.getLieuNaiss() != null) {
                    existingProfessionnel.setLieuNaiss(professionnel.getLieuNaiss());
                }
                if (professionnel.getSexe() != null) {
                    existingProfessionnel.setSexe(professionnel.getSexe());
                }
                if (professionnel.getTelephone() != null) {
                    existingProfessionnel.setTelephone(professionnel.getTelephone());
                }
                if (professionnel.getAdressePhysique() != null) {
                    existingProfessionnel.setAdressePhysique(professionnel.getAdressePhysique());
                }
                if (professionnel.getRegionResidence() != null) {
                    existingProfessionnel.setRegionResidence(professionnel.getRegionResidence());
                }
                if (professionnel.getDepartResidence() != null) {
                    existingProfessionnel.setDepartResidence(professionnel.getDepartResidence());
                }
                if (professionnel.getEmail() != null) {
                    existingProfessionnel.setEmail(professionnel.getEmail());
                }
                if (professionnel.getCni() != null) {
                    existingProfessionnel.setCni(professionnel.getCni());
                }

                return existingProfessionnel;
            })
            .map(professionnelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Professionnel> findAll(Pageable pageable) {
        log.debug("Request to get all Professionnels");
        return professionnelRepository.findAll(pageable);
    }

    public Page<Professionnel> findAllWithEagerRelationships(Pageable pageable) {
        return professionnelRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the professionnels where Dossier is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Professionnel> findAllWhereDossierIsNull() {
        log.debug("Request to get all professionnels where Dossier is null");
        return StreamSupport.stream(professionnelRepository.findAll().spliterator(), false)
            .filter(professionnel -> professionnel.getDossier() == null)
            .toList();
    }

    /**
     *  Get all the professionnels where Demandeur is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Professionnel> findAllWhereDemandeurIsNull() {
        log.debug("Request to get all professionnels where Demandeur is null");
        return StreamSupport.stream(professionnelRepository.findAll().spliterator(), false)
            .filter(professionnel -> professionnel.getDemandeur() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Professionnel> findOne(Long id) {
        log.debug("Request to get Professionnel : {}", id);
        return professionnelRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Professionnel : {}", id);
        professionnelRepository.deleteById(id);
    }
}
