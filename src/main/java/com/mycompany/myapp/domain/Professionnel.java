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
 * A Professionnel.
 */
@Entity
@Table(name = "professionnel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Professionnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "profession", nullable = false)
    private String profession;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professionnel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professionnel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    private Set<Experience> experiences = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professionnel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "professionnel", "formationContinue", "etablissement" }, allowSetters = true)
    private Set<CandidatureP> candidaturePS = new HashSet<>();

    @JsonIgnoreProperties(value = { "eleve", "etudiant", "professionnel", "demandeur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "professionnel")
    private Dossier dossier;

    @JsonIgnoreProperties(
        value = { "user", "dossier", "eleve", "etudiant", "professionnel", "diplomes", "experiences" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "professionnel")
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Professionnel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfession() {
        return this.profession;
    }

    public Professionnel profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getNom() {
        return this.nom;
    }

    public Professionnel nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Professionnel prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Professionnel dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Professionnel lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Professionnel sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Professionnel telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public String getAdressePhysique() {
        return this.adressePhysique;
    }

    public Professionnel adressePhysique(String adressePhysique) {
        this.setAdressePhysique(adressePhysique);
        return this;
    }

    public void setAdressePhysique(String adressePhysique) {
        this.adressePhysique = adressePhysique;
    }

    public NomRegion getRegionResidence() {
        return this.regionResidence;
    }

    public Professionnel regionResidence(NomRegion regionResidence) {
        this.setRegionResidence(regionResidence);
        return this;
    }

    public void setRegionResidence(NomRegion regionResidence) {
        this.regionResidence = regionResidence;
    }

    public NomDepartement getDepartResidence() {
        return this.departResidence;
    }

    public Professionnel departResidence(NomDepartement departResidence) {
        this.setDepartResidence(departResidence);
        return this;
    }

    public void setDepartResidence(NomDepartement departResidence) {
        this.departResidence = departResidence;
    }

    public String getEmail() {
        return this.email;
    }

    public Professionnel email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCni() {
        return this.cni;
    }

    public Professionnel cni(Long cni) {
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

    public Professionnel user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Diplome> getDiplomes() {
        return this.diplomes;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        if (this.diplomes != null) {
            this.diplomes.forEach(i -> i.setProfessionnel(null));
        }
        if (diplomes != null) {
            diplomes.forEach(i -> i.setProfessionnel(this));
        }
        this.diplomes = diplomes;
    }

    public Professionnel diplomes(Set<Diplome> diplomes) {
        this.setDiplomes(diplomes);
        return this;
    }

    public Professionnel addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setProfessionnel(this);
        return this;
    }

    public Professionnel removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setProfessionnel(null);
        return this;
    }

    public Set<Experience> getExperiences() {
        return this.experiences;
    }

    public void setExperiences(Set<Experience> experiences) {
        if (this.experiences != null) {
            this.experiences.forEach(i -> i.setProfessionnel(null));
        }
        if (experiences != null) {
            experiences.forEach(i -> i.setProfessionnel(this));
        }
        this.experiences = experiences;
    }

    public Professionnel experiences(Set<Experience> experiences) {
        this.setExperiences(experiences);
        return this;
    }

    public Professionnel addExperience(Experience experience) {
        this.experiences.add(experience);
        experience.setProfessionnel(this);
        return this;
    }

    public Professionnel removeExperience(Experience experience) {
        this.experiences.remove(experience);
        experience.setProfessionnel(null);
        return this;
    }

    public Set<CandidatureP> getCandidaturePS() {
        return this.candidaturePS;
    }

    public void setCandidaturePS(Set<CandidatureP> candidaturePS) {
        if (this.candidaturePS != null) {
            this.candidaturePS.forEach(i -> i.setProfessionnel(null));
        }
        if (candidaturePS != null) {
            candidaturePS.forEach(i -> i.setProfessionnel(this));
        }
        this.candidaturePS = candidaturePS;
    }

    public Professionnel candidaturePS(Set<CandidatureP> candidaturePS) {
        this.setCandidaturePS(candidaturePS);
        return this;
    }

    public Professionnel addCandidatureP(CandidatureP candidatureP) {
        this.candidaturePS.add(candidatureP);
        candidatureP.setProfessionnel(this);
        return this;
    }

    public Professionnel removeCandidatureP(CandidatureP candidatureP) {
        this.candidaturePS.remove(candidatureP);
        candidatureP.setProfessionnel(null);
        return this;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public void setDossier(Dossier dossier) {
        if (this.dossier != null) {
            this.dossier.setProfessionnel(null);
        }
        if (dossier != null) {
            dossier.setProfessionnel(this);
        }
        this.dossier = dossier;
    }

    public Professionnel dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        if (this.demandeur != null) {
            this.demandeur.setProfessionnel(null);
        }
        if (demandeur != null) {
            demandeur.setProfessionnel(this);
        }
        this.demandeur = demandeur;
    }

    public Professionnel demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Professionnel)) {
            return false;
        }
        return getId() != null && getId().equals(((Professionnel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Professionnel{" +
            "id=" + getId() +
            ", profession='" + getProfession() + "'" +
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
