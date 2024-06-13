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
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FormationContinue.
 */
@Entity
@Table(name = "formation_continue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormationContinue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_formation_c")
    private String nomFormationC;

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

    @Lob
    @Column(name = "fiche_formation")
    private byte[] ficheFormation;

    @Column(name = "fiche_formation_content_type")
    private String ficheFormationContentType;

    @Column(name = "libelle_pc")
    private String libellePC;

    @Column(name = "montant_prise_en_charge")
    private Double montantPriseEnCharge;

    @Column(name = "cout_formation")
    private Double coutFormation;

    @Lob
    @Column(name = "detail_pc")
    private String detailPC;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_diplome")
    private DiplomeObtenu nomDiplome;

    @Column(name = "autre_diplome")
    private String autreDiplome;

    @Column(name = "nom_debouche")
    private String nomDebouche;

    @JsonIgnoreProperties(
        value = { "etablissements", "priseEnCharge", "formationInitiale", "formationContinue", "concours" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Formation formation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formationContinue")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "professionnel", "formationContinue", "etablissement" }, allowSetters = true)
    private Set<CandidatureP> candidaturePS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FormationContinue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomFormationC() {
        return this.nomFormationC;
    }

    public FormationContinue nomFormationC(String nomFormationC) {
        this.setNomFormationC(nomFormationC);
        return this;
    }

    public void setNomFormationC(String nomFormationC) {
        this.nomFormationC = nomFormationC;
    }

    public String getDuree() {
        return this.duree;
    }

    public FormationContinue duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Admission getAdmission() {
        return this.admission;
    }

    public FormationContinue admission(Admission admission) {
        this.setAdmission(admission);
        return this;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public DiplomeRequis getDiplomeRequis() {
        return this.diplomeRequis;
    }

    public FormationContinue diplomeRequis(DiplomeRequis diplomeRequis) {
        this.setDiplomeRequis(diplomeRequis);
        return this;
    }

    public void setDiplomeRequis(DiplomeRequis diplomeRequis) {
        this.diplomeRequis = diplomeRequis;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public FormationContinue niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public NomFiliere getFiliere() {
        return this.filiere;
    }

    public FormationContinue filiere(NomFiliere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(NomFiliere filiere) {
        this.filiere = filiere;
    }

    public NomSerie getSerie() {
        return this.serie;
    }

    public FormationContinue serie(NomSerie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(NomSerie serie) {
        this.serie = serie;
    }

    public CFP getCfp() {
        return this.cfp;
    }

    public FormationContinue cfp(CFP cfp) {
        this.setCfp(cfp);
        return this;
    }

    public void setCfp(CFP cfp) {
        this.cfp = cfp;
    }

    public LYCEE getLycee() {
        return this.lycee;
    }

    public FormationContinue lycee(LYCEE lycee) {
        this.setLycee(lycee);
        return this;
    }

    public void setLycee(LYCEE lycee) {
        this.lycee = lycee;
    }

    public byte[] getFicheFormation() {
        return this.ficheFormation;
    }

    public FormationContinue ficheFormation(byte[] ficheFormation) {
        this.setFicheFormation(ficheFormation);
        return this;
    }

    public void setFicheFormation(byte[] ficheFormation) {
        this.ficheFormation = ficheFormation;
    }

    public String getFicheFormationContentType() {
        return this.ficheFormationContentType;
    }

    public FormationContinue ficheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
        return this;
    }

    public void setFicheFormationContentType(String ficheFormationContentType) {
        this.ficheFormationContentType = ficheFormationContentType;
    }

    public String getLibellePC() {
        return this.libellePC;
    }

    public FormationContinue libellePC(String libellePC) {
        this.setLibellePC(libellePC);
        return this;
    }

    public void setLibellePC(String libellePC) {
        this.libellePC = libellePC;
    }

    public Double getMontantPriseEnCharge() {
        return this.montantPriseEnCharge;
    }

    public FormationContinue montantPriseEnCharge(Double montantPriseEnCharge) {
        this.setMontantPriseEnCharge(montantPriseEnCharge);
        return this;
    }

    public void setMontantPriseEnCharge(Double montantPriseEnCharge) {
        this.montantPriseEnCharge = montantPriseEnCharge;
    }

    public Double getCoutFormation() {
        return this.coutFormation;
    }

    public FormationContinue coutFormation(Double coutFormation) {
        this.setCoutFormation(coutFormation);
        return this;
    }

    public void setCoutFormation(Double coutFormation) {
        this.coutFormation = coutFormation;
    }

    public String getDetailPC() {
        return this.detailPC;
    }

    public FormationContinue detailPC(String detailPC) {
        this.setDetailPC(detailPC);
        return this;
    }

    public void setDetailPC(String detailPC) {
        this.detailPC = detailPC;
    }

    public DiplomeObtenu getNomDiplome() {
        return this.nomDiplome;
    }

    public FormationContinue nomDiplome(DiplomeObtenu nomDiplome) {
        this.setNomDiplome(nomDiplome);
        return this;
    }

    public void setNomDiplome(DiplomeObtenu nomDiplome) {
        this.nomDiplome = nomDiplome;
    }

    public String getAutreDiplome() {
        return this.autreDiplome;
    }

    public FormationContinue autreDiplome(String autreDiplome) {
        this.setAutreDiplome(autreDiplome);
        return this;
    }

    public void setAutreDiplome(String autreDiplome) {
        this.autreDiplome = autreDiplome;
    }

    public String getNomDebouche() {
        return this.nomDebouche;
    }

    public FormationContinue nomDebouche(String nomDebouche) {
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

    public FormationContinue formation(Formation formation) {
        this.setFormation(formation);
        return this;
    }

    public Set<CandidatureP> getCandidaturePS() {
        return this.candidaturePS;
    }

    public void setCandidaturePS(Set<CandidatureP> candidaturePS) {
        if (this.candidaturePS != null) {
            this.candidaturePS.forEach(i -> i.setFormationContinue(null));
        }
        if (candidaturePS != null) {
            candidaturePS.forEach(i -> i.setFormationContinue(this));
        }
        this.candidaturePS = candidaturePS;
    }

    public FormationContinue candidaturePS(Set<CandidatureP> candidaturePS) {
        this.setCandidaturePS(candidaturePS);
        return this;
    }

    public FormationContinue addCandidatureP(CandidatureP candidatureP) {
        this.candidaturePS.add(candidatureP);
        candidatureP.setFormationContinue(this);
        return this;
    }

    public FormationContinue removeCandidatureP(CandidatureP candidatureP) {
        this.candidaturePS.remove(candidatureP);
        candidatureP.setFormationContinue(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FormationContinue)) {
            return false;
        }
        return getId() != null && getId().equals(((FormationContinue) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormationContinue{" +
            "id=" + getId() +
            ", nomFormationC='" + getNomFormationC() + "'" +
            ", duree='" + getDuree() + "'" +
            ", admission='" + getAdmission() + "'" +
            ", diplomeRequis='" + getDiplomeRequis() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", filiere='" + getFiliere() + "'" +
            ", serie='" + getSerie() + "'" +
            ", cfp='" + getCfp() + "'" +
            ", lycee='" + getLycee() + "'" +
            ", ficheFormation='" + getFicheFormation() + "'" +
            ", ficheFormationContentType='" + getFicheFormationContentType() + "'" +
            ", libellePC='" + getLibellePC() + "'" +
            ", montantPriseEnCharge=" + getMontantPriseEnCharge() +
            ", coutFormation=" + getCoutFormation() +
            ", detailPC='" + getDetailPC() + "'" +
            ", nomDiplome='" + getNomDiplome() + "'" +
            ", autreDiplome='" + getAutreDiplome() + "'" +
            ", nomDebouche='" + getNomDebouche() + "'" +
            "}";
    }
}
