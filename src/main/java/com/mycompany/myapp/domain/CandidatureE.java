package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.Resultat;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CandidatureE.
 */
@Entity
@Table(name = "candidature_e")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CandidatureE implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "offre_formation")
    private NomFiliere offreFormation;

    @Column(name = "date_debut_offre")
    private LocalDate dateDebutOffre;

    @Column(name = "date_fin_offre")
    private LocalDate dateFinOffre;

    @Column(name = "date_depot")
    private LocalDate dateDepot;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultat")
    private Resultat resultat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidatureES", "dossier", "demandeur" }, allowSetters = true)
    private Eleve eleve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "user", "diplomes", "experiences", "candidatureES", "dossier", "demandeur" }, allowSetters = true)
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "formation", "candidatureES" }, allowSetters = true)
    private FormationInitiale formationInitiale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "candidatureES", "candidaturePS", "formations" }, allowSetters = true)
    private Etablissement etablissement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CandidatureE id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomFiliere getOffreFormation() {
        return this.offreFormation;
    }

    public CandidatureE offreFormation(NomFiliere offreFormation) {
        this.setOffreFormation(offreFormation);
        return this;
    }

    public void setOffreFormation(NomFiliere offreFormation) {
        this.offreFormation = offreFormation;
    }

    public LocalDate getDateDebutOffre() {
        return this.dateDebutOffre;
    }

    public CandidatureE dateDebutOffre(LocalDate dateDebutOffre) {
        this.setDateDebutOffre(dateDebutOffre);
        return this;
    }

    public void setDateDebutOffre(LocalDate dateDebutOffre) {
        this.dateDebutOffre = dateDebutOffre;
    }

    public LocalDate getDateFinOffre() {
        return this.dateFinOffre;
    }

    public CandidatureE dateFinOffre(LocalDate dateFinOffre) {
        this.setDateFinOffre(dateFinOffre);
        return this;
    }

    public void setDateFinOffre(LocalDate dateFinOffre) {
        this.dateFinOffre = dateFinOffre;
    }

    public LocalDate getDateDepot() {
        return this.dateDepot;
    }

    public CandidatureE dateDepot(LocalDate dateDepot) {
        this.setDateDepot(dateDepot);
        return this;
    }

    public void setDateDepot(LocalDate dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Resultat getResultat() {
        return this.resultat;
    }

    public CandidatureE resultat(Resultat resultat) {
        this.setResultat(resultat);
        return this;
    }

    public void setResultat(Resultat resultat) {
        this.resultat = resultat;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public CandidatureE eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public CandidatureE etudiant(Etudiant etudiant) {
        this.setEtudiant(etudiant);
        return this;
    }

    public FormationInitiale getFormationInitiale() {
        return this.formationInitiale;
    }

    public void setFormationInitiale(FormationInitiale formationInitiale) {
        this.formationInitiale = formationInitiale;
    }

    public CandidatureE formationInitiale(FormationInitiale formationInitiale) {
        this.setFormationInitiale(formationInitiale);
        return this;
    }

    public Etablissement getEtablissement() {
        return this.etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public CandidatureE etablissement(Etablissement etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidatureE)) {
            return false;
        }
        return getId() != null && getId().equals(((CandidatureE) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidatureE{" +
            "id=" + getId() +
            ", offreFormation='" + getOffreFormation() + "'" +
            ", dateDebutOffre='" + getDateDebutOffre() + "'" +
            ", dateFinOffre='" + getDateFinOffre() + "'" +
            ", dateDepot='" + getDateDepot() + "'" +
            ", resultat='" + getResultat() + "'" +
            "}";
    }
}
