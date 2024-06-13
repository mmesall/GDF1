package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Experience.
 */
@Entity
@Table(name = "experience")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Experience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;

    @NotNull
    @Column(name = "nom_entreprise", nullable = false)
    private String nomEntreprise;

    @NotNull
    @Column(name = "poste_occupe", nullable = false)
    private String posteOccupe;

    @Lob
    @Column(name = "mission")
    private String mission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidatureES", "dossier", "demandeur" }, allowSetters = true)
    private Eleve eleve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidatureES", "dossier", "demandeur" }, allowSetters = true)
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidaturePS", "dossier", "demandeur" }, allowSetters = true)
    private Professionnel professionnel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "user", "dossier", "eleve", "etudiant", "professionnel", "diplomes", "experiences" },
        allowSetters = true
    )
    private Demandeur demandeur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Experience id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Experience dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Experience dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getNomEntreprise() {
        return this.nomEntreprise;
    }

    public Experience nomEntreprise(String nomEntreprise) {
        this.setNomEntreprise(nomEntreprise);
        return this;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getPosteOccupe() {
        return this.posteOccupe;
    }

    public Experience posteOccupe(String posteOccupe) {
        this.setPosteOccupe(posteOccupe);
        return this;
    }

    public void setPosteOccupe(String posteOccupe) {
        this.posteOccupe = posteOccupe;
    }

    public String getMission() {
        return this.mission;
    }

    public Experience mission(String mission) {
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

    public Experience eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Experience etudiant(Etudiant etudiant) {
        this.setEtudiant(etudiant);
        return this;
    }

    public Professionnel getProfessionnel() {
        return this.professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }

    public Experience professionnel(Professionnel professionnel) {
        this.setProfessionnel(professionnel);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public Experience demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Experience)) {
            return false;
        }
        return getId() != null && getId().equals(((Experience) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Experience{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", nomEntreprise='" + getNomEntreprise() + "'" +
            ", posteOccupe='" + getPosteOccupe() + "'" +
            ", mission='" + getMission() + "'" +
            "}";
    }
}
