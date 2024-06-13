package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomEtablissement;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Concours.
 */
@Entity
@Table(name = "concours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Concours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_concours")
    private String nomConcours;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_etablissement")
    private NomEtablissement nomEtablissement;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude")
    private NiveauEtude niveauEtude;

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;

    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @Column(name = "date_concours")
    private LocalDate dateConcours;

    @Lob
    @Column(name = "affiche")
    private byte[] affiche;

    @Column(name = "affiche_content_type")
    private String afficheContentType;

    @JsonIgnoreProperties(
        value = { "etablissements", "priseEnCharge", "formationInitiale", "formationContinue", "concours" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Formation formation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Concours id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomConcours() {
        return this.nomConcours;
    }

    public Concours nomConcours(String nomConcours) {
        this.setNomConcours(nomConcours);
        return this;
    }

    public void setNomConcours(String nomConcours) {
        this.nomConcours = nomConcours;
    }

    public NomEtablissement getNomEtablissement() {
        return this.nomEtablissement;
    }

    public Concours nomEtablissement(NomEtablissement nomEtablissement) {
        this.setNomEtablissement(nomEtablissement);
        return this;
    }

    public void setNomEtablissement(NomEtablissement nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public Concours niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public LocalDate getDateOuverture() {
        return this.dateOuverture;
    }

    public Concours dateOuverture(LocalDate dateOuverture) {
        this.setDateOuverture(dateOuverture);
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateCloture() {
        return this.dateCloture;
    }

    public Concours dateCloture(LocalDate dateCloture) {
        this.setDateCloture(dateCloture);
        return this;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public LocalDate getDateConcours() {
        return this.dateConcours;
    }

    public Concours dateConcours(LocalDate dateConcours) {
        this.setDateConcours(dateConcours);
        return this;
    }

    public void setDateConcours(LocalDate dateConcours) {
        this.dateConcours = dateConcours;
    }

    public byte[] getAffiche() {
        return this.affiche;
    }

    public Concours affiche(byte[] affiche) {
        this.setAffiche(affiche);
        return this;
    }

    public void setAffiche(byte[] affiche) {
        this.affiche = affiche;
    }

    public String getAfficheContentType() {
        return this.afficheContentType;
    }

    public Concours afficheContentType(String afficheContentType) {
        this.afficheContentType = afficheContentType;
        return this;
    }

    public void setAfficheContentType(String afficheContentType) {
        this.afficheContentType = afficheContentType;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public Concours formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Concours)) {
            return false;
        }
        return getId() != null && getId().equals(((Concours) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Concours{" +
            "id=" + getId() +
            ", nomConcours='" + getNomConcours() + "'" +
            ", nomEtablissement='" + getNomEtablissement() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateConcours='" + getDateConcours() + "'" +
            ", affiche='" + getAffiche() + "'" +
            ", afficheContentType='" + getAfficheContentType() + "'" +
            "}";
    }
}
