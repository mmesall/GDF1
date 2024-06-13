package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bailleur.
 */
@Entity
@Table(name = "bailleur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bailleur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_bailleur", nullable = false, unique = true)
    private String nomBailleur;

    @Column(name = "budget_prevu")
    private Double budgetPrevu;

    @Column(name = "budget_depense")
    private Double budgetDepense;

    @Column(name = "budget_restant")
    private Double budgetRestant;

    @Column(name = "nbre_pc")
    private Long nbrePC;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bailleur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "formation", "bailleur" }, allowSetters = true)
    private Set<PriseEnCharge> priseEnCharges = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bailleur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomBailleur() {
        return this.nomBailleur;
    }

    public Bailleur nomBailleur(String nomBailleur) {
        this.setNomBailleur(nomBailleur);
        return this;
    }

    public void setNomBailleur(String nomBailleur) {
        this.nomBailleur = nomBailleur;
    }

    public Double getBudgetPrevu() {
        return this.budgetPrevu;
    }

    public Bailleur budgetPrevu(Double budgetPrevu) {
        this.setBudgetPrevu(budgetPrevu);
        return this;
    }

    public void setBudgetPrevu(Double budgetPrevu) {
        this.budgetPrevu = budgetPrevu;
    }

    public Double getBudgetDepense() {
        return this.budgetDepense;
    }

    public Bailleur budgetDepense(Double budgetDepense) {
        this.setBudgetDepense(budgetDepense);
        return this;
    }

    public void setBudgetDepense(Double budgetDepense) {
        this.budgetDepense = budgetDepense;
    }

    public Double getBudgetRestant() {
        return this.budgetRestant;
    }

    public Bailleur budgetRestant(Double budgetRestant) {
        this.setBudgetRestant(budgetRestant);
        return this;
    }

    public void setBudgetRestant(Double budgetRestant) {
        this.budgetRestant = budgetRestant;
    }

    public Long getNbrePC() {
        return this.nbrePC;
    }

    public Bailleur nbrePC(Long nbrePC) {
        this.setNbrePC(nbrePC);
        return this;
    }

    public void setNbrePC(Long nbrePC) {
        this.nbrePC = nbrePC;
    }

    public Set<PriseEnCharge> getPriseEnCharges() {
        return this.priseEnCharges;
    }

    public void setPriseEnCharges(Set<PriseEnCharge> priseEnCharges) {
        if (this.priseEnCharges != null) {
            this.priseEnCharges.forEach(i -> i.setBailleur(null));
        }
        if (priseEnCharges != null) {
            priseEnCharges.forEach(i -> i.setBailleur(this));
        }
        this.priseEnCharges = priseEnCharges;
    }

    public Bailleur priseEnCharges(Set<PriseEnCharge> priseEnCharges) {
        this.setPriseEnCharges(priseEnCharges);
        return this;
    }

    public Bailleur addPriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharges.add(priseEnCharge);
        priseEnCharge.setBailleur(this);
        return this;
    }

    public Bailleur removePriseEnCharge(PriseEnCharge priseEnCharge) {
        this.priseEnCharges.remove(priseEnCharge);
        priseEnCharge.setBailleur(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bailleur)) {
            return false;
        }
        return getId() != null && getId().equals(((Bailleur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bailleur{" +
            "id=" + getId() +
            ", nomBailleur='" + getNomBailleur() + "'" +
            ", budgetPrevu=" + getBudgetPrevu() +
            ", budgetDepense=" + getBudgetDepense() +
            ", budgetRestant=" + getBudgetRestant() +
            ", nbrePC=" + getNbrePC() +
            "}";
    }
}
