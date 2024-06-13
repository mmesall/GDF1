package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Admission;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.TypeFormation;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Formation.
 */
@Entity
@Table(name = "formation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_formation")
    private String nomFormation;

    @Lob
    @Column(name = "image_formation")
    private byte[] imageFormation;

    @Column(name = "image_formation_content_type")
    private String imageFormationContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_formation")
    private TypeFormation typeFormation;

    @Column(name = "duree")
    private String duree;

    @Enumerated(EnumType.STRING)
    @Column(name = "admission")
    private Admission admission;

    @Enumerated(EnumType.STRING)
    @Column(name = "diplome_requis")
    private DiplomeRequis diplomeRequis;

    @Lob
    @Column(name = "fiche_formation")
    private byte[] ficheFormation;

    @Column(name = "fiche_formation_content_type")
    private String ficheFormationContentType;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_formation__etablissement",
        joinColumns = @JoinColumn(name = "formation_id"),
        inverseJoinColumns = @JoinColumn(name = "etablissement_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "candidatureES", "candidaturePS", "formations" }, allowSetters = true)
    private Set<Etablissement> etablissements = new HashSet<>();

    @JsonIgnoreProperties(value = { "formation", "bailleur" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "formation")
    private PriseEnCharge priseEnCharge;

    @JsonIgnoreProperties(value = { "formation", "candidatureES" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "formation")
    private FormationInitiale formationInitiale;

    @JsonIgnoreProperties(value = { "formation", "candidaturePS" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "formation")
    private FormationContinue formationContinue;

    @JsonIgnoreProperties(value = { "formation" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "formation")
    private Concours concours;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFormation() {
        return this.nomFormation;
    }

    public Formation nomFormation(String nomFormation) {
        this.setNomFormation(nomFormation);
        return this;
    }

    public void setNomFormation(String nomFormation) {
        this.nomFormation = nomFormation;
    }

    public byte[] getImageFormation() {
        return this.imageFormation;
    }

    public Formation imageFormation(byte[] imageFormation) {
        this.setImageFormation(imageFormation);
        return this;
    }

    public void setImageFormation(byte[] imageFormation) {
        this.imageFormation = imageFormation;
    }

    public String getImageFormationContentType() {
        return this.imageFormationContentType;
    }

    public Formation imageFormationContentType(String imageFormationContentType) {
        this.imageFormationContentType = imageFormationContentType;
        return this;
    }

    public void setImageFormationContentType(String imageFormationContentType) {
        this.imageFormationContentType = imageFormationContentType;
    }

    public TypeFormation getTypeFormation() {
        return this.typeFormation;
    }

    public Formation typeFormation(TypeFormation typeFormation) {
        this.setTypeFormation(typeFormation);
        return this;
    }

    public void setTypeFormation(TypeFormation typeFormation) {
        this.typeFormation = typeFormation;
    }

    public String getDuree() {
        return this.duree;
    }

    public Formation duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Admission getAdmission() {
        return this.admission;
    }

    public Formation admission(Admission admission) {
        this.setAdmission(admission);
        return this;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public DiplomeRequis getDiplomeRequis() {
        return this.diplomeRequis;
    }

    public Formation diplomeRequis(DiplomeRequis diplomeRequis) {
        this.setDiplomeRequis(diplomeRequis);
        return this;
    }

    public void setDiplomeRequis(DiplomeRequis diplomeRequis) {
        this.diplomeRequis = diplomeRequis;
    }

    public byte[] getFicheFormation() {
        return this.ficheFormation;
    }

    public Formation ficheFormation(byte[] ficheFormation) {
        this.setFicheFormation(ficheFormation);
        return this;
    }

    public void setFicheFormation(byte[] ficheFormation) {
        this.ficheFormation = ficheFormation;
    }

    public String getFicheFormationContentType() {
        return this.ficheFormationContentType;
    }

    public Formation ficheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
        return this;
    }

    public void setFicheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
    }

    public Set<Etablissement> getEtablissements() {
        return this.etablissements;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        this.etablissements = etablissements;
    }

    public Formation etablissements(Set<Etablissement> etablissements) {
        this.setEtablissements(etablissements);
        return this;
    }

    public Formation addEtablissement(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        return this;
    }

    public Formation removeEtablissement(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        return this;
    }

    public PriseEnCharge getPriseEnCharge() {
        return this.priseEnCharge;
    }

    public void setPriseEnCharge(PriseEnCharge priseEnCharge) {
        if (this.priseEnCharge != null) {
            this.priseEnCharge.setFormation(null);
        }
        if (priseEnCharge != null) {
            priseEnCharge.setFormation(this);
        }
        this.priseEnCharge = priseEnCharge;
    }

    public Formation priseEnCharge(PriseEnCharge priseEnCharge) {
        this.setPriseEnCharge(priseEnCharge);
        return this;
    }

    public FormationInitiale getFormationInitiale() {
        return this.formationInitiale;
    }

    public void setFormationInitiale(FormationInitiale formationInitiale) {
        if (this.formationInitiale != null) {
            this.formationInitiale.setFormation(null);
        }
        if (formationInitiale != null) {
            formationInitiale.setFormation(this);
        }
        this.formationInitiale = formationInitiale;
    }

    public Formation formationInitiale(FormationInitiale formationInitiale) {
        this.setFormationInitiale(formationInitiale);
        return this;
    }

    public FormationContinue getFormationContinue() {
        return this.formationContinue;
    }

    public void setFormationContinue(FormationContinue formationContinue) {
        if (this.formationContinue != null) {
            this.formationContinue.setFormation(null);
        }
        if (formationContinue != null) {
            formationContinue.setFormation(this);
        }
        this.formationContinue = formationContinue;
    }

    public Formation formationContinue(FormationContinue formationContinue) {
        this.setFormationContinue(formationContinue);
        return this;
    }

    public Concours getConcours() {
        return this.concours;
    }

    public void setConcours(Concours concours) {
        if (this.concours != null) {
            this.concours.setFormation(null);
        }
        if (concours != null) {
            concours.setFormation(this);
        }
        this.concours = concours;
    }

    public Formation concours(Concours concours) {
        this.setConcours(concours);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formation)) {
            return false;
        }
        return getId() != null && getId().equals(((Formation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formation{" +
            "id=" + getId() +
            ", nomFormation='" + getNomFormation() + "'" +
            ", imageFormation='" + getImageFormation() + "'" +
            ", imageFormationContentType='" + getImageFormationContentType() + "'" +
            ", typeFormation='" + getTypeFormation() + "'" +
            ", duree='" + getDuree() + "'" +
            ", admission='" + getAdmission() + "'" +
            ", diplomeRequis='" + getDiplomeRequis() + "'" +
            ", ficheFormation='" + getFicheFormation() + "'" +
            ", ficheFormationContentType='" + getFicheFormationContentType() + "'" +
            "}";
    }
}
