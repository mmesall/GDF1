package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Admission;
import com.mycompany.myapp.domain.enumeration.CFP;
import com.mycompany.myapp.domain.enumeration.DiplomeObtenu;
import com.mycompany.myapp.domain.enumeration.DiplomeRequis;
import com.mycompany.myapp.domain.enumeration.LYCEE;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.NomSerie;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormationInitiale.
 */
@Entity
@Table(name = "formation_initiale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormationInitiale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_formation_i")
    private String nomFormationI;

    @Column(name = "duree")
    private String duree;

    @Enumerated(EnumType.STRING)
    @Column(name = "admission")
    private Admission admission;

    @Enumerated(EnumType.STRING)
    @Column(name = "diplome_requis")
    private DiplomeRequis diplomeRequis;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude")
    private NiveauEtude niveauEtude;

    @Lob
    @Column(name = "fiche_formation")
    private byte[] ficheFormation;

    @Column(name = "fiche_formation_content_type")
    private String ficheFormationContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "filiere")
    private NomFiliere filiere;

    @Enumerated(EnumType.STRING)
    @Column(name = "serie")
    private NomSerie serie;

    @Enumerated(EnumType.STRING)
    @Column(name = "cfp")
    private CFP cfp;

    @Enumerated(EnumType.STRING)
    @Column(name = "lycee")
    private LYCEE lycee;

    @Column(name = "nom_concours")
    private String nomConcours;

    @Column(name = "date_ouverture")
    private LocalDate dateOuverture;

    @Column(name = "date_cloture")
    private LocalDate dateCloture;

    @Column(name = "date_concours")
    private LocalDate dateConcours;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_diplome")
    private DiplomeObtenu nomDiplome;

    @Column(name = "nom_debouche")
    private String nomDebouche;

    @JsonIgnoreProperties(
        value = { "etablissements", "priseEnCharge", "formationInitiale", "formationContinue", "concours" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Formation formation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formationInitiale")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "formationInitiale", "etablissement" }, allowSetters = true)
    private Set<CandidatureE> candidatureES = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormationInitiale id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFormationI() {
        return this.nomFormationI;
    }

    public FormationInitiale nomFormationI(String nomFormationI) {
        this.setNomFormationI(nomFormationI);
        return this;
    }

    public void setNomFormationI(String nomFormationI) {
        this.nomFormationI = nomFormationI;
    }

    public String getDuree() {
        return this.duree;
    }

    public FormationInitiale duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Admission getAdmission() {
        return this.admission;
    }

    public FormationInitiale admission(Admission admission) {
        this.setAdmission(admission);
        return this;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public DiplomeRequis getDiplomeRequis() {
        return this.diplomeRequis;
    }

    public FormationInitiale diplomeRequis(DiplomeRequis diplomeRequis) {
        this.setDiplomeRequis(diplomeRequis);
        return this;
    }

    public void setDiplomeRequis(DiplomeRequis diplomeRequis) {
        this.diplomeRequis = diplomeRequis;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public FormationInitiale niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public byte[] getFicheFormation() {
        return this.ficheFormation;
    }

    public FormationInitiale ficheFormation(byte[] ficheFormation) {
        this.setFicheFormation(ficheFormation);
        return this;
    }

    public void setFicheFormation(byte[] ficheFormation) {
        this.ficheFormation = ficheFormation;
    }

    public String getFicheFormationContentType() {
        return this.ficheFormationContentType;
    }

    public FormationInitiale ficheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
        return this;
    }

    public void setFicheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
    }

    public NomFiliere getFiliere() {
        return this.filiere;
    }

    public FormationInitiale filiere(NomFiliere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(NomFiliere filiere) {
        this.filiere = filiere;
    }

    public NomSerie getSerie() {
        return this.serie;
    }

    public FormationInitiale serie(NomSerie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(NomSerie serie) {
        this.serie = serie;
    }

    public CFP getCfp() {
        return this.cfp;
    }

    public FormationInitiale cfp(CFP cfp) {
        this.setCfp(cfp);
        return this;
    }

    public void setCfp(CFP cfp) {
        this.cfp = cfp;
    }

    public LYCEE getLycee() {
        return this.lycee;
    }

    public FormationInitiale lycee(LYCEE lycee) {
        this.setLycee(lycee);
        return this;
    }

    public void setLycee(LYCEE lycee) {
        this.lycee = lycee;
    }

    public String getNomConcours() {
        return this.nomConcours;
    }

    public FormationInitiale nomConcours(String nomConcours) {
        this.setNomConcours(nomConcours);
        return this;
    }

    public void setNomConcours(String nomConcours) {
        this.nomConcours = nomConcours;
    }

    public LocalDate getDateOuverture() {
        return this.dateOuverture;
    }

    public FormationInitiale dateOuverture(LocalDate dateOuverture) {
        this.setDateOuverture(dateOuverture);
        return this;
    }

    public void setDateOuverture(LocalDate dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public LocalDate getDateCloture() {
        return this.dateCloture;
    }

    public FormationInitiale dateCloture(LocalDate dateCloture) {
        this.setDateCloture(dateCloture);
        return this;
    }

    public void setDateCloture(LocalDate dateCloture) {
        this.dateCloture = dateCloture;
    }

    public LocalDate getDateConcours() {
        return this.dateConcours;
    }

    public FormationInitiale dateConcours(LocalDate dateConcours) {
        this.setDateConcours(dateConcours);
        return this;
    }

    public void setDateConcours(LocalDate dateConcours) {
        this.dateConcours = dateConcours;
    }

    public DiplomeObtenu getNomDiplome() {
        return this.nomDiplome;
    }

    public FormationInitiale nomDiplome(DiplomeObtenu nomDiplome) {
        this.setNomDiplome(nomDiplome);
        return this;
    }

    public void setNomDiplome(DiplomeObtenu nomDiplome) {
        this.nomDiplome = nomDiplome;
    }

    public String getNomDebouche() {
        return this.nomDebouche;
    }

    public FormationInitiale nomDebouche(String nomDebouche) {
        this.setNomDebouche(nomDebouche);
        return this;
    }

    public void setNomDebouche(String nomDebouche) {
        this.nomDebouche = nomDebouche;
    }

    public Formation getFormation() {
        return this.formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public FormationInitiale formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public Set<CandidatureE> getCandidatureES() {
        return this.candidatureES;
    }

    public void setCandidatureES(Set<CandidatureE> candidatureES) {
        if (this.candidatureES != null) {
            this.candidatureES.forEach(i -> i.setFormationInitiale(null));
        }
        if (candidatureES != null) {
            candidatureES.forEach(i -> i.setFormationInitiale(this));
        }
        this.candidatureES = candidatureES;
    }

    public FormationInitiale candidatureES(Set<CandidatureE> candidatureES) {
        this.setCandidatureES(candidatureES);
        return this;
    }

    public FormationInitiale addCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.add(candidatureE);
        candidatureE.setFormationInitiale(this);
        return this;
    }

    public FormationInitiale removeCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.remove(candidatureE);
        candidatureE.setFormationInitiale(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormationInitiale)) {
            return false;
        }
        return getId() != null && getId().equals(((FormationInitiale) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormationInitiale{" +
            "id=" + getId() +
            ", nomFormationI='" + getNomFormationI() + "'" +
            ", duree='" + getDuree() + "'" +
            ", admission='" + getAdmission() + "'" +
            ", diplomeRequis='" + getDiplomeRequis() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", ficheFormation='" + getFicheFormation() + "'" +
            ", ficheFormationContentType='" + getFicheFormationContentType() + "'" +
            ", filiere='" + getFiliere() + "'" +
            ", serie='" + getSerie() + "'" +
            ", cfp='" + getCfp() + "'" +
            ", lycee='" + getLycee() + "'" +
            ", nomConcours='" + getNomConcours() + "'" +
            ", dateOuverture='" + getDateOuverture() + "'" +
            ", dateCloture='" + getDateCloture() + "'" +
            ", dateConcours='" + getDateConcours() + "'" +
            ", nomDiplome='" + getNomDiplome() + "'" +
            ", nomDebouche='" + getNomDebouche() + "'" +
            "}";
    }
}
