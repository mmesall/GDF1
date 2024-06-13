package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NIVEAUCOMP;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomDepartement;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.Sexe;
import com.mycompany.myapp.domain.enumeration.TypePiece;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "num_dossier", unique = true)
    private String numDossier;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "nom_utilisateur", unique = true)
    private String nomUtilisateur;

    @Column(name = "date_naiss")
    private LocalDate dateNaiss;

    @Column(name = "lieu_naiss")
    private String lieuNaiss;

    @Enumerated(EnumType.STRING)
    @Column(name = "region_naiss")
    private NomRegion regionNaiss;

    @Enumerated(EnumType.STRING)
    @Column(name = "departement_naiss")
    private NomDepartement departementNaiss;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_piece")
    private TypePiece typePiece;

    @Column(name = "numero_piece", unique = true)
    private Long numeroPiece;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Enumerated(EnumType.STRING)
    @Column(name = "region_residence")
    private NomRegion regionResidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "dep_residence")
    private NomDepartement depResidence;

    @Column(name = "adresse_residence")
    private String adresseResidence;

    @Column(name = "telephone_1")
    private String telephone1;

    @Column(name = "telephone_2")
    private String telephone2;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_formation")
    private NiveauEtude niveauFormation;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialite")
    private NomFiliere specialite;

    @Column(name = "intitule_diplome")
    private String intituleDiplome;

    @Lob
    @Column(name = "diplome")
    private byte[] diplome;

    @Column(name = "diplome_content_type")
    private String diplomeContentType;

    @Column(name = "annee_obtention")
    private LocalDate anneeObtention;

    @Column(name = "lieu_obtention")
    private String lieuObtention;

    @Lob
    @Column(name = "cv")
    private byte[] cv;

    @Column(name = "cv_content_type")
    private String cvContentType;

    @Lob
    @Column(name = "lettre_motivation")
    private byte[] lettreMotivation;

    @Column(name = "lettre_motivation_content_type")
    private String lettreMotivationContentType;

    @Column(name = "profession")
    private String profession;

    @Column(name = "autre_specialite")
    private String autreSpecialite;

    @Column(name = "nom_competence")
    private String nomCompetence;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_competence")
    private NIVEAUCOMP niveauCompetence;

    @Column(name = "intitule_experience")
    private String intituleExperience;

    @NotNull
    @Column(name = "poste_occupe", nullable = false)
    private String posteOccupe;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @Lob
    @Column(name = "mission")
    private String mission;

    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidatureES", "dossier", "demandeur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Eleve eleve;

    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidatureES", "dossier", "demandeur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Etudiant etudiant;

    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidaturePS", "dossier", "demandeur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Professionnel professionnel;

    @JsonIgnoreProperties(
        value = { "user", "dossier", "eleve", "etudiant", "professionnel", "diplomes", "experiences" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "dossier")
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumDossier() {
        return this.numDossier;
    }

    public Dossier numDossier(String numDossier) {
        this.setNumDossier(numDossier);
        return this;
    }

    public void setNumDossier(String numDossier) {
        this.numDossier = numDossier;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Dossier prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Dossier nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    public Dossier nomUtilisateur(String nomUtilisateur) {
        this.setNomUtilisateur(nomUtilisateur);
        return this;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Dossier dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Dossier lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public NomRegion getRegionNaiss() {
        return this.regionNaiss;
    }

    public Dossier regionNaiss(NomRegion regionNaiss) {
        this.setRegionNaiss(regionNaiss);
        return this;
    }

    public void setRegionNaiss(NomRegion regionNaiss) {
        this.regionNaiss = regionNaiss;
    }

    public NomDepartement getDepartementNaiss() {
        return this.departementNaiss;
    }

    public Dossier departementNaiss(NomDepartement departementNaiss) {
        this.setDepartementNaiss(departementNaiss);
        return this;
    }

    public void setDepartementNaiss(NomDepartement departementNaiss) {
        this.departementNaiss = departementNaiss;
    }

    public TypePiece getTypePiece() {
        return this.typePiece;
    }

    public Dossier typePiece(TypePiece typePiece) {
        this.setTypePiece(typePiece);
        return this;
    }

    public void setTypePiece(TypePiece typePiece) {
        this.typePiece = typePiece;
    }

    public Long getNumeroPiece() {
        return this.numeroPiece;
    }

    public Dossier numeroPiece(Long numeroPiece) {
        this.setNumeroPiece(numeroPiece);
        return this;
    }

    public void setNumeroPiece(Long numeroPiece) {
        this.numeroPiece = numeroPiece;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Dossier sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public NomRegion getRegionResidence() {
        return this.regionResidence;
    }

    public Dossier regionResidence(NomRegion regionResidence) {
        this.setRegionResidence(regionResidence);
        return this;
    }

    public void setRegionResidence(NomRegion regionResidence) {
        this.regionResidence = regionResidence;
    }

    public NomDepartement getDepResidence() {
        return this.depResidence;
    }

    public Dossier depResidence(NomDepartement depResidence) {
        this.setDepResidence(depResidence);
        return this;
    }

    public void setDepResidence(NomDepartement depResidence) {
        this.depResidence = depResidence;
    }

    public String getAdresseResidence() {
        return this.adresseResidence;
    }

    public Dossier adresseResidence(String adresseResidence) {
        this.setAdresseResidence(adresseResidence);
        return this;
    }

    public void setAdresseResidence(String adresseResidence) {
        this.adresseResidence = adresseResidence;
    }

    public String getTelephone1() {
        return this.telephone1;
    }

    public Dossier telephone1(String telephone1) {
        this.setTelephone1(telephone1);
        return this;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return this.telephone2;
    }

    public Dossier telephone2(String telephone2) {
        this.setTelephone2(telephone2);
        return this;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getEmail() {
        return this.email;
    }

    public Dossier email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NiveauEtude getNiveauFormation() {
        return this.niveauFormation;
    }

    public Dossier niveauFormation(NiveauEtude niveauFormation) {
        this.setNiveauFormation(niveauFormation);
        return this;
    }

    public void setNiveauFormation(NiveauEtude niveauFormation) {
        this.niveauFormation = niveauFormation;
    }

    public NomFiliere getSpecialite() {
        return this.specialite;
    }

    public Dossier specialite(NomFiliere specialite) {
        this.setSpecialite(specialite);
        return this;
    }

    public void setSpecialite(NomFiliere specialite) {
        this.specialite = specialite;
    }

    public String getIntituleDiplome() {
        return this.intituleDiplome;
    }

    public Dossier intituleDiplome(String intituleDiplome) {
        this.setIntituleDiplome(intituleDiplome);
        return this;
    }

    public void setIntituleDiplome(String intituleDiplome) {
        this.intituleDiplome = intituleDiplome;
    }

    public byte[] getDiplome() {
        return this.diplome;
    }

    public Dossier diplome(byte[] diplome) {
        this.setDiplome(diplome);
        return this;
    }

    public void setDiplome(byte[] diplome) {
        this.diplome = diplome;
    }

    public String getDiplomeContentType() {
        return this.diplomeContentType;
    }

    public Dossier diplomeContentType(String diplomeContentType) {
        this.diplomeContentType = diplomeContentType;
        return this;
    }

    public void setDiplomeContentType(String diplomeContentType) {
        this.diplomeContentType = diplomeContentType;
    }

    public LocalDate getAnneeObtention() {
        return this.anneeObtention;
    }

    public Dossier anneeObtention(LocalDate anneeObtention) {
        this.setAnneeObtention(anneeObtention);
        return this;
    }

    public void setAnneeObtention(LocalDate anneeObtention) {
        this.anneeObtention = anneeObtention;
    }

    public String getLieuObtention() {
        return this.lieuObtention;
    }

    public Dossier lieuObtention(String lieuObtention) {
        this.setLieuObtention(lieuObtention);
        return this;
    }

    public void setLieuObtention(String lieuObtention) {
        this.lieuObtention = lieuObtention;
    }

    public byte[] getCv() {
        return this.cv;
    }

    public Dossier cv(byte[] cv) {
        this.setCv(cv);
        return this;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }

    public String getCvContentType() {
        return this.cvContentType;
    }

    public Dossier cvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
        return this;
    }

    public void setCvContentType(String cvContentType) {
        this.cvContentType = cvContentType;
    }

    public byte[] getLettreMotivation() {
        return this.lettreMotivation;
    }

    public Dossier lettreMotivation(byte[] lettreMotivation) {
        this.setLettreMotivation(lettreMotivation);
        return this;
    }

    public void setLettreMotivation(byte[] lettreMotivation) {
        this.lettreMotivation = lettreMotivation;
    }

    public String getLettreMotivationContentType() {
        return this.lettreMotivationContentType;
    }

    public Dossier lettreMotivationContentType(String lettreMotivationContentType) {
        this.lettreMotivationContentType = lettreMotivationContentType;
        return this;
    }

    public void setLettreMotivationContentType(String lettreMotivationContentType) {
        this.lettreMotivationContentType = lettreMotivationContentType;
    }

    public String getProfession() {
        return this.profession;
    }

    public Dossier profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAutreSpecialite() {
        return this.autreSpecialite;
    }

    public Dossier autreSpecialite(String autreSpecialite) {
        this.setAutreSpecialite(autreSpecialite);
        return this;
    }

    public void setAutreSpecialite(String autreSpecialite) {
        this.autreSpecialite = autreSpecialite;
    }

    public String getNomCompetence() {
        return this.nomCompetence;
    }

    public Dossier nomCompetence(String nomCompetence) {
        this.setNomCompetence(nomCompetence);
        return this;
    }

    public void setNomCompetence(String nomCompetence) {
        this.nomCompetence = nomCompetence;
    }

    public NIVEAUCOMP getNiveauCompetence() {
        return this.niveauCompetence;
    }

    public Dossier niveauCompetence(NIVEAUCOMP niveauCompetence) {
        this.setNiveauCompetence(niveauCompetence);
        return this;
    }

    public void setNiveauCompetence(NIVEAUCOMP niveauCompetence) {
        this.niveauCompetence = niveauCompetence;
    }

    public String getIntituleExperience() {
        return this.intituleExperience;
    }

    public Dossier intituleExperience(String intituleExperience) {
        this.setIntituleExperience(intituleExperience);
        return this;
    }

    public void setIntituleExperience(String intituleExperience) {
        this.intituleExperience = intituleExperience;
    }

    public String getPosteOccupe() {
        return this.posteOccupe;
    }

    public Dossier posteOccupe(String posteOccupe) {
        this.setPosteOccupe(posteOccupe);
        return this;
    }

    public void setPosteOccupe(String posteOccupe) {
        this.posteOccupe = posteOccupe;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Dossier dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Dossier dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getNomEntreprise() {
        return this.nomEntreprise;
    }

    public Dossier nomEntreprise(String nomEntreprise) {
        this.setNomEntreprise(nomEntreprise);
        return this;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getMission() {
        return this.mission;
    }

    public Dossier mission(String mission) {
        this.setMission(mission);
        return this;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Dossier eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Dossier etudiant(Etudiant etudiant) {
        this.setEtudiant(etudiant);
        return this;
    }

    public Professionnel getProfessionnel() {
        return this.professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }

    public Dossier professionnel(Professionnel professionnel) {
        this.setProfessionnel(professionnel);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (this.demandeur != null) {
            this.demandeur.setDossier(null);
        }
        if (demandeur != null) {
            demandeur.setDossier(this);
        }
        this.demandeur = demandeur;
    }

    public Dossier demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return getId() != null && getId().equals(((Dossier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", numDossier='" + getNumDossier() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", nomUtilisateur='" + getNomUtilisateur() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", regionNaiss='" + getRegionNaiss() + "'" +
            ", departementNaiss='" + getDepartementNaiss() + "'" +
            ", typePiece='" + getTypePiece() + "'" +
            ", numeroPiece=" + getNumeroPiece() +
            ", sexe='" + getSexe() + "'" +
            ", regionResidence='" + getRegionResidence() + "'" +
            ", depResidence='" + getDepResidence() + "'" +
            ", adresseResidence='" + getAdresseResidence() + "'" +
            ", telephone1='" + getTelephone1() + "'" +
            ", telephone2='" + getTelephone2() + "'" +
            ", email='" + getEmail() + "'" +
            ", niveauFormation='" + getNiveauFormation() + "'" +
            ", specialite='" + getSpecialite() + "'" +
            ", intituleDiplome='" + getIntituleDiplome() + "'" +
            ", diplome='" + getDiplome() + "'" +
            ", diplomeContentType='" + getDiplomeContentType() + "'" +
            ", anneeObtention='" + getAnneeObtention() + "'" +
            ", lieuObtention='" + getLieuObtention() + "'" +
            ", cv='" + getCv() + "'" +
            ", cvContentType='" + getCvContentType() + "'" +
            ", lettreMotivation='" + getLettreMotivation() + "'" +
            ", lettreMotivationContentType='" + getLettreMotivationContentType() + "'" +
            ", profession='" + getProfession() + "'" +
            ", autreSpecialite='" + getAutreSpecialite() + "'" +
            ", nomCompetence='" + getNomCompetence() + "'" +
            ", niveauCompetence='" + getNiveauCompetence() + "'" +
            ", intituleExperience='" + getIntituleExperience() + "'" +
            ", posteOccupe='" + getPosteOccupe() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nomEntreprise='" + getNomEntreprise() + "'" +
            ", mission='" + getMission() + "'" +
            "}";
    }
}
