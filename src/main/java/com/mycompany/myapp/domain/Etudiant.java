package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A Etudiant.
 */
@Entity
@Table(name = "etudiant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "carte_etudiant", nullable = false, unique = true)
    private String carteEtudiant;

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

    @Column(name = "telephone", unique = true)
    private Long telephone;

    @Column(name = "adresse_physique")
    private String adressePhysique;

    @Enumerated(EnumType.STRING)
    @Column(name = "region_residence")
    private NomRegion regionResidence;

    @Enumerated(EnumType.STRING)
    @Column(name = "depart_residence")
    private NomDepartement departResidence;

    @Column(name = "email", unique = true)
    private String email;

    @NotNull
    @Column(name = "cni", nullable = false, unique = true)
    private Long cni;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etudiant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etudiant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etudiant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "formationInitiale", "etablissement" }, allowSetters = true)
    private Set<CandidatureE> candidatureES = new HashSet<>();

    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "etudiant")
    private Dossier dossier;

    @JsonIgnoreProperties(
        value = { "user", "dossier", "eleve", "etudiant", "professionnel", "diplomes", "experiences" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "etudiant")
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etudiant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarteEtudiant() {
        return this.carteEtudiant;
    }

    public Etudiant carteEtudiant(String carteEtudiant) {
        this.setCarteEtudiant(carteEtudiant);
        return this;
    }

    public void setCarteEtudiant(String carteEtudiant) {
        this.carteEtudiant = carteEtudiant;
    }

    public String getNom() {
        return this.nom;
    }

    public Etudiant nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Etudiant prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Etudiant dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Etudiant lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Etudiant sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Etudiant telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAdressePhysique() {
        return this.adressePhysique;
    }

    public Etudiant adressePhysique(String adressePhysique) {
        this.setAdressePhysique(adressePhysique);
        return this;
    }

    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    public NomRegion getRegionResidence() {
        return this.regionResidence;
    }

    public Etudiant regionResidence(NomRegion regionResidence) {
        this.setRegionResidence(regionResidence);
        return this;
    }

    public void setRegionResidence(NomRegion regionResidence) {
        this.regionResidence = regionResidence;
    }

    public NomDepartement getDepartResidence() {
        return this.departResidence;
    }

    public Etudiant departResidence(NomDepartement departResidence) {
        this.setDepartResidence(departResidence);
        return this;
    }

    public void setDepartResidence(NomDepartement departResidence) {
        this.departResidence = departResidence;
    }

    public String getEmail() {
        return this.email;
    }

    public Etudiant email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCni() {
        return this.cni;
    }

    public Etudiant cni(Long cni) {
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

    public Etudiant user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Diplome> getDiplomes() {
        return this.diplomes;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        if (this.diplomes != null) {
            this.diplomes.forEach(i -> i.setEtudiant(null));
        }
        if (diplomes != null) {
            diplomes.forEach(i -> i.setEtudiant(this));
        }
        this.diplomes = diplomes;
    }

    public Etudiant diplomes(Set<Diplome> diplomes) {
        this.setDiplomes(diplomes);
        return this;
    }

    public Etudiant addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setEtudiant(this);
        return this;
    }

    public Etudiant removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setEtudiant(null);
        return this;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setEtudiant(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setEtudiant(this));
        }
        this.experiences = experiences;
    }

    public Etudiant experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Etudiant addExperience(Experience experience) {
        this.experiences.add(experience);
        experience.setEtudiant(this);
        return this;
    }

    public Etudiant removeExperience(Experience experience) {
        this.experiences.remove(experience);
        experience.setEtudiant(null);
        return this;
    }

    public Set<CandidatureE> getCandidatureES() {
        return this.candidatureES;
    }

    public void setCandidatureES(Set<CandidatureE> candidatureES) {
        if (this.candidatureES != null) {
            this.candidatureES.forEach(i -> i.setEtudiant(null));
        }
        if (candidatureES != null) {
            candidatureES.forEach(i -> i.setEtudiant(this));
        }
        this.candidatureES = candidatureES;
    }

    public Etudiant candidatureES(Set<CandidatureE> candidatureES) {
        this.setCandidatureES(candidatureES);
        return this;
    }

    public Etudiant addCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.add(candidatureE);
        candidatureE.setEtudiant(this);
        return this;
    }

    public Etudiant removeCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.remove(candidatureE);
        candidatureE.setEtudiant(null);
        return this;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        if (this.dossier != null) {
            this.dossier.setEtudiant(null);
        }
        if (dossier != null) {
            dossier.setEtudiant(this);
        }
        this.dossier = dossier;
    }

    public Etudiant dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (this.demandeur != null) {
            this.demandeur.setEtudiant(null);
        }
        if (demandeur != null) {
            demandeur.setEtudiant(this);
        }
        this.demandeur = demandeur;
    }

    public Etudiant demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etudiant)) {
            return false;
        }
        return getId() != null && getId().equals(((Etudiant) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etudiant{" +
            "id=" + getId() +
            ", carteEtudiant='" + getCarteEtudiant() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", telephone=" + getTelephone() +
            ", adressePhysique='" + getAdressePhysique() + "'" +
            ", regionResidence='" + getRegionResidence() + "'" +
            ", departResidence='" + getDepartResidence() + "'" +
            ", email='" + getEmail() + "'" +
            ", cni=" + getCni() +
            "}";
    }
}
