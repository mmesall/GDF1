package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Etablissement;
import com.mycompany.myapp.repository.EtablissementRepository;
import com.mycompany.myapp.service.EtablissementService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Etablissement}.
 */
@Service
@Transactional
public class EtablissementServiceImpl implements EtablissementService {

    private final Logger log = LoggerFactory.getLogger(EtablissementServiceImpl.class);

    private final EtablissementRepository etablissementRepository;

    public EtablissementServiceImpl(EtablissementRepository etablissementRepository) {
        this.etablissementRepository = etablissementRepository;
    }

    @Override
    public Etablissement save(Etablissement etablissement) {
        log.debug("Request to save Etablissement : {}", etablissement);
        return etablissementRepository.save(etablissement);
    }

    @Override
    public Etablissement update(Etablissement etablissement) {
        log.debug("Request to update Etablissement : {}", etablissement);
        return etablissementRepository.save(etablissement);
    }

    @Override
    public Optional<Etablissement> partialUpdate(Etablissement etablissement) {
        log.debug("Request to partially update Etablissement : {}", etablissement);

        return etablissementRepository
            .findById(etablissement.getId())
            .map(existingEtablissement -> {
                if (etablissement.getNomEtablissement() != null) {
                    existingEtablissement.setNomEtablissement(etablissement.getNomEtablissement());
                }
                if (etablissement.getPhoto() != null) {
                    existingEtablissement.setPhoto(etablissement.getPhoto());
                }
                if (etablissement.getPhotoContentType() != null) {
                    existingEtablissement.setPhotoContentType(etablissement.getPhotoContentType());
                }
                if (etablissement.getRegion() != null) {
                    existingEtablissement.setRegion(etablissement.getRegion());
                }
                if (etablissement.getDepartement() != null) {
                    existingEtablissement.setDepartement(etablissement.getDepartement());
                }
                if (etablissement.getEmail() != null) {
                    existingEtablissement.setEmail(etablissement.getEmail());
                }
                if (etablissement.getTelephone() != null) {
                    existingEtablissement.setTelephone(etablissement.getTelephone());
                }
                if (etablissement.getTypeEtablissement() != null) {
                    existingEtablissement.setTypeEtablissement(etablissement.getTypeEtablissement());
                }
                if (etablissement.getStatut() != null) {
                    existingEtablissement.setStatut(etablissement.getStatut());
                }
                if (etablissement.getAutreRegion() != null) {
                    existingEtablissement.setAutreRegion(etablissement.getAutreRegion());
                }
                if (etablissement.getAutreDepartement() != null) {
                    existingEtablissement.setAutreDepartement(etablissement.getAutreDepartement());
                }
                if (etablissement.getCfp() != null) {
                    existingEtablissement.setCfp(etablissement.getCfp());
                }
                if (etablissement.getLycee() != null) {
                    existingEtablissement.setLycee(etablissement.getLycee());
                }
                if (etablissement.getFiliere() != null) {
                    existingEtablissement.setFiliere(etablissement.getFiliere());
                }
                if (etablissement.getSerie() != null) {
                    existingEtablissement.setSerie(etablissement.getSerie());
                }
                if (etablissement.getAutreFiliere() != null) {
                    existingEtablissement.setAutreFiliere(etablissement.getAutreFiliere());
                }
                if (etablissement.getAutreSerie() != null) {
                    existingEtablissement.setAutreSerie(etablissement.getAutreSerie());
                }
                if (etablissement.getAutreNomEtablissement() != null) {
                    existingEtablissement.setAutreNomEtablissement(etablissement.getAutreNomEtablissement());
                }

                return existingEtablissement;
            })
            .map(etablissementRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Etablissement> findAll(Pageable pageable) {
        log.debug("Request to get all Etablissements");
        return etablissementRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Etablissement> findOne(Long id) {
        log.debug("Request to get Etablissement : {}", id);
        return etablissementRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etablissement : {}", id);
        etablissementRepository.deleteById(id);
    }
}
