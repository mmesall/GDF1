package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomDepartement;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.Sexe;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "date_naiss")
    private LocalDate dateNaiss;

    @Column(name = "lieu_naiss")
    private String lieuNaiss;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "telephone")
    private Long telephone;

    @Column(name = "adresse_physique")
    private String adressePhysique;

    @Enumerated(EnumType.STRING)
    @Column(name = "region_residence")
    private NomRegion regionResidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "depart_residence")
    private NomDepartement departResidence;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude", nullable = false)
    private NiveauEtude niveauEtude;

    @Column(name = "cni", unique = true)
    private Long cni;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eleve")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eleve")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eleve")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "formationInitiale", "etablissement" }, allowSetters = true)
    private Set<CandidatureE> candidatureES = new HashSet<>();

    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "eleve")
    private Dossier dossier;

    @JsonIgnoreProperties(
        value = { "user", "dossier", "eleve", "etudiant", "professionnel", "diplomes", "experiences" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "eleve")
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Eleve id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Eleve nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Eleve prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Eleve dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Eleve lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Eleve sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Eleve telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAdressePhysique() {
        return this.adressePhysique;
    }

    public Eleve adressePhysique(String adressePhysique) {
        this.setAdressePhysique(adressePhysique);
        return this;
    }

    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    public NomRegion getRegionResidence() {
        return this.regionResidence;
    }

    public Eleve regionResidence(NomRegion regionResidence) {
        this.setRegionResidence(regionResidence);
        return this;
    }

    public void setRegionResidence(NomRegion regionResidence) {
        this.regionResidence = regionResidence;
    }

    public NomDepartement getDepartResidence() {
        return this.departResidence;
    }

    public Eleve departResidence(NomDepartement departResidence) {
        this.setDepartResidence(departResidence);
        return this;
    }

    public void setDepartResidence(NomDepartement departResidence) {
        this.departResidence = departResidence;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public Eleve niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Long getCni() {
        return this.cni;
    }

    public Eleve cni(Long cni) {
        this.setCni(cni);
        return this;
    }

    public void setCni(Long cni) {
        this.cni = cni;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Eleve user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Diplome> getDiplomes() {
        return this.diplomes;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        if (this.diplomes != null) {
            this.diplomes.forEach(i -> i.setEleve(null));
        }
        if (diplomes != null) {
            diplomes.forEach(i -> i.setEleve(this));
        }
        this.diplomes = diplomes;
    }

    public Eleve diplomes(Set<Diplome> diplomes) {
        this.setDiplomes(diplomes);
        return this;
    }

    public Eleve addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setEleve(this);
        return this;
    }

    public Eleve removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setEleve(null);
        return this;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setEleve(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setEleve(this));
        }
        this.experiences = experiences;
    }

    public Eleve experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Eleve addExperience(Experience experience) {
        this.experiences.add(experience);
        experience.setEleve(this);
        return this;
    }

    public Eleve removeExperience(Experience experience) {
        this.experiences.remove(experience);
        experience.setEleve(null);
        return this;
    }

    public Set<CandidatureE> getCandidatureES() {
        return this.candidatureES;
    }

    public void setCandidatureES(Set<CandidatureE> candidatureES) {
        if (this.candidatureES != null) {
            this.candidatureES.forEach(i -> i.setEleve(null));
        }
        if (candidatureES != null) {
            candidatureES.forEach(i -> i.setEleve(this));
        }
        this.candidatureES = candidatureES;
    }

    public Eleve candidatureES(Set<CandidatureE> candidatureES) {
        this.setCandidatureES(candidatureES);
        return this;
    }

    public Eleve addCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.add(candidatureE);
        candidatureE.setEleve(this);
        return this;
    }

    public Eleve removeCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.remove(candidatureE);
        candidatureE.setEleve(null);
        return this;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        if (this.dossier != null) {
            this.dossier.setEleve(null);
        }
        if (dossier != null) {
            dossier.setEleve(this);
        }
        this.dossier = dossier;
    }

    public Eleve dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (this.demandeur != null) {
            this.demandeur.setEleve(null);
        }
        if (demandeur != null) {
            demandeur.setEleve(this);
        }
        this.demandeur = demandeur;
    }

    public Eleve demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eleve)) {
            return false;
        }
        return getId() != null && getId().equals(((Eleve) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eleve{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephone=" + getTelephone() +
            ", adressePhysique='" + getAdressePhysique() + "'" +
            ", regionResidence='" + getRegionResidence() + "'" +
            ", departResidence='" + getDepartResidence() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", cni=" + getCni() +
            "}";
    }
}
