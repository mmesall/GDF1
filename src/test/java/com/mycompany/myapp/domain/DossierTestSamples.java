package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DossierTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Dossier getDossierSample1() {
        return new Dossier()
            .id(1L)
            .numDossier("numDossier1")
            .prenom("prenom1")
            .nom("nom1")
            .nomUtilisateur("nomUtilisateur1")
            .lieuNaiss("lieuNaiss1")
            .numeroPiece(1L)
            .adresseResidence("adresseResidence1")
            .telephone1("telephone11")
            .telephone2("telephone21")
            .email("email1")
            .intituleDiplome("intituleDiplome1")
            .lieuObtention("lieuObtention1")
            .profession("profession1")
            .autreSpecialite("autreSpecialite1")
            .nomCompetence("nomCompetence1")
            .intituleExperience("intituleExperience1")
            .posteOccupe("posteOccupe1")
            .nomEntreprise("nomEntreprise1");
    }

    public static Dossier getDossierSample2() {
        return new Dossier()
            .id(2L)
            .numDossier("numDossier2")
            .prenom("prenom2")
            .nom("nom2")
            .nomUtilisateur("nomUtilisateur2")
            .lieuNaiss("lieuNaiss2")
            .numeroPiece(2L)
            .adresseResidence("adresseResidence2")
            .telephone1("telephone12")
            .telephone2("telephone22")
            .email("email2")
            .intituleDiplome("intituleDiplome2")
            .lieuObtention("lieuObtention2")
            .profession("profession2")
            .autreSpecialite("autreSpecialite2")
            .nomCompetence("nomCompetence2")
            .intituleExperience("intituleExperience2")
            .posteOccupe("posteOccupe2")
            .nomEntreprise("nomEntreprise2");
    }

    public static Dossier getDossierRandomSampleGenerator() {
        return new Dossier()
            .id(longCount.incrementAndGet())
            .numDossier(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .nomUtilisateur(UUID.randomUUID().toString())
            .lieuNaiss(UUID.randomUUID().toString())
            .numeroPiece(longCount.incrementAndGet())
            .adresseResidence(UUID.randomUUID().toString())
            .telephone1(UUID.randomUUID().toString())
            .telephone2(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .intituleDiplome(UUID.randomUUID().toString())
            .lieuObtention(UUID.randomUUID().toString())
            .profession(UUID.randomUUID().toString())
            .autreSpecialite(UUID.randomUUID().toString())
            .nomCompetence(UUID.randomUUID().toString())
            .intituleExperience(UUID.randomUUID().toString())
            .posteOccupe(UUID.randomUUID().toString())
            .nomEntreprise(UUID.randomUUID().toString());
    }
}
