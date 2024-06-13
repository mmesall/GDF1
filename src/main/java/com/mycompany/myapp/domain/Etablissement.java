package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.CFP;
import com.mycompany.myapp.domain.enumeration.LYCEE;
import com.mycompany.myapp.domain.enumeration.NomDepartement;
import com.mycompany.myapp.domain.enumeration.NomEtablissement;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import com.mycompany.myapp.domain.enumeration.NomRegion;
import com.mycompany.myapp.domain.enumeration.NomSerie;
import com.mycompany.myapp.domain.enumeration.StatutEtab;
import com.mycompany.myapp.domain.enumeration.TypeEtablissement;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Etablissement.
 */
@Entity
@Table(name = "etablissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Etablissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nom_etablissement", unique = true)
    private NomEtablissement nomEtablissement;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "region", nullable = false)
    private NomRegion region;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "departement", nullable = false)
    private NomDepartement departement;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private Long telephone;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_etablissement")
    private TypeEtablissement typeEtablissement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutEtab statut;

    @Column(name = "autre_region")
    private String autreRegion;

    @Column(name = "autre_departement")
    private String autreDepartement;

    @Enumerated(EnumType.STRING)
    @Column(name = "cfp")
    private CFP cfp;

    @Enumerated(EnumType.STRING)
    @Column(name = "lycee")
    private LYCEE lycee;

    @Enumerated(EnumType.STRING)
    @Column(name = "filiere")
    private NomFiliere filiere;

    @Enumerated(EnumType.STRING)
    @Column(name = "serie")
    private NomSerie serie;

    @Column(name = "autre_filiere")
    private String autreFiliere;

    @Column(name = "autre_serie")
    private String autreSerie;

    @Column(name = "autre_nom_etablissement")
    private String autreNomEtablissement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "eleve", "etudiant", "formationInitiale", "etablissement" }, allowSetters = true)
    private Set<CandidatureE> candidatureES = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etablissement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "professionnel", "formationContinue", "etablissement" }, allowSetters = true)
    private Set<CandidatureP> candidaturePS = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "etablissements")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "etablissements", "priseEnCharge", "formationInitiale", "formationContinue", "concours" },
        allowSetters = true
    )
    private Set<Formation> formations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Etablissement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NomEtablissement getNomEtablissement() {
        return this.nomEtablissement;
    }

    public Etablissement nomEtablissement(NomEtablissement nomEtablissement) {
        this.setNomEtablissement(nomEtablissement);
        return this;
    }

    public void setNomEtablissement(NomEtablissement nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Etablissement photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Etablissement photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public NomRegion getRegion() {
        return this.region;
    }

    public Etablissement region(NomRegion region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(NomRegion region) {
        this.region = region;
    }

    public NomDepartement getDepartement() {
        return this.departement;
    }

    public Etablissement departement(NomDepartement departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(NomDepartement departement) {
        this.departement = departement;
    }

    public String getEmail() {
        return this.email;
    }

    public Etablissement email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getTelephone() {
        return this.telephone;
    }

    public Etablissement telephone(Long telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public TypeEtablissement getTypeEtablissement() {
        return this.typeEtablissement;
    }

    public Etablissement typeEtablissement(TypeEtablissement typeEtablissement) {
        this.setTypeEtablissement(typeEtablissement);
        return this;
    }

    public void setTypeEtablissement(TypeEtablissement typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public StatutEtab getStatut() {
        return this.statut;
    }

    public Etablissement statut(StatutEtab statut) {
        this.setStatut(statut);
        return this;
    }

    public void setStatut(StatutEtab statut) {
        this.statut = statut;
    }

    public String getAutreRegion() {
        return this.autreRegion;
    }

    public Etablissement autreRegion(String autreRegion) {
        this.setAutreRegion(autreRegion);
        return this;
    }

    public void setAutreRegion(String autreRegion) {
        this.autreRegion = autreRegion;
    }

    public String getAutreDepartement() {
        return this.autreDepartement;
    }

    public Etablissement autreDepartement(String autreDepartement) {
        this.setAutreDepartement(autreDepartement);
        return this;
    }

    public void setAutreDepartement(String autreDepartement) {
        this.autreDepartement = autreDepartement;
    }

    public CFP getCfp() {
        return this.cfp;
    }

    public Etablissement cfp(CFP cfp) {
        this.setCfp(cfp);
        return this;
    }

    public void setCfp(CFP cfp) {
        this.cfp = cfp;
    }

    public LYCEE getLycee() {
        return this.lycee;
    }

    public Etablissement lycee(LYCEE lycee) {
        this.setLycee(lycee);
        return this;
    }

    public void setLycee(LYCEE lycee) {
        this.lycee = lycee;
    }

    public NomFiliere getFiliere() {
        return this.filiere;
    }

    public Etablissement filiere(NomFiliere filiere) {
        this.setFiliere(filiere);
        return this;
    }

    public void setFiliere(NomFiliere filiere) {
        this.filiere = filiere;
    }

    public NomSerie getSerie() {
        return this.serie;
    }

    public Etablissement serie(NomSerie serie) {
        this.setSerie(serie);
        return this;
    }

    public void setSerie(NomSerie serie) {
        this.serie = serie;
    }

    public String getAutreFiliere() {
        return this.autreFiliere;
    }

    public Etablissement autreFiliere(String autreFiliere) {
        this.setAutreFiliere(autreFiliere);
        return this;
    }

    public void setAutreFiliere(String autreFiliere) {
        this.autreFiliere = autreFiliere;
    }

    public String getAutreSerie() {
        return this.autreSerie;
    }

    public Etablissement autreSerie(String autreSerie) {
        this.setAutreSerie(autreSerie);
        return this;
    }

    public void setAutreSerie(String autreSerie) {
        this.autreSerie = autreSerie;
    }

    public String getAutreNomEtablissement() {
        return this.autreNomEtablissement;
    }

    public Etablissement autreNomEtablissement(String autreNomEtablissement) {
        this.setAutreNomEtablissement(autreNomEtablissement);
        return this;
    }

    public void setAutreNomEtablissement(String autreNomEtablissement) {
        this.autreNomEtablissement = autreNomEtablissement;
    }

    public Set<CandidatureE> getCandidatureES() {
        return this.candidatureES;
    }

    public void setCandidatureES(Set<CandidatureE> candidatureES) {
        if (this.candidatureES != null) {
            this.candidatureES.forEach(i -> i.setEtablissement(null));
        }
        if (candidatureES != null) {
            candidatureES.forEach(i -> i.setEtablissement(this));
        }
        this.candidatureES = candidatureES;
    }

    public Etablissement candidatureES(Set<CandidatureE> candidatureES) {
        this.setCandidatureES(candidatureES);
        return this;
    }

    public Etablissement addCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.add(candidatureE);
        candidatureE.setEtablissement(this);
        return this;
    }

    public Etablissement removeCandidatureE(CandidatureE candidatureE) {
        this.candidatureES.remove(candidatureE);
        candidatureE.setEtablissement(null);
        return this;
    }

    public Set<CandidatureP> getCandidaturePS() {
        return this.candidaturePS;
    }

    public void setCandidaturePS(Set<CandidatureP> candidaturePS) {
        if (this.candidaturePS != null) {
            this.candidaturePS.forEach(i -> i.setEtablissement(null));
        }
        if (candidaturePS != null) {
            candidaturePS.forEach(i -> i.setEtablissement(this));
        }
        this.candidaturePS = candidaturePS;
    }

    public Etablissement candidaturePS(Set<CandidatureP> candidaturePS) {
        this.setCandidaturePS(candidaturePS);
        return this;
    }

    public Etablissement addCandidatureP(CandidatureP candidatureP) {
        this.candidaturePS.add(candidatureP);
        candidatureP.setEtablissement(this);
        return this;
    }

    public Etablissement removeCandidatureP(CandidatureP candidatureP) {
        this.candidaturePS.remove(candidatureP);
        candidatureP.setEtablissement(null);
        return this;
    }

    public Set<Formation> getFormations() {
        return this.formations;
    }

    public void setFormations(Set<Formation> formations) {
        if (this.formations != null) {
            this.formations.forEach(i -> i.removeEtablissement(this));
        }
        if (formations != null) {
            formations.forEach(i -> i.addEtablissement(this));
        }
        this.formations = formations;
    }

    public Etablissement formations(Set<Formation> formations) {
        this.setFormations(formations);
        return this;
    }

    public Etablissement addFormation(Formation formation) {
        this.formations.add(formation);
        formation.getEtablissements().add(this);
        return this;
    }

    public Etablissement removeFormation(Formation formation) {
        this.formations.remove(formation);
        formation.getEtablissements().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Etablissement)) {
            return false;
        }
        return getId() != null && getId().equals(((Etablissement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Etablissement{" +
            "id=" + getId() +
            ", nomEtablissement='" + getNomEtablissement() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", region='" + getRegion() + "'" +
            ", departement='" + getDepartement() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone=" + getTelephone() +
            ", typeEtablissement='" + getTypeEtablissement() + "'" +
            ", statut='" + getStatut() + "'" +
            ", autreRegion='" + getAutreRegion() + "'" +
            ", autreDepartement='" + getAutreDepartement() + "'" +
            ", cfp='" + getCfp() + "'" +
            ", lycee='" + getLycee() + "'" +
            ", filiere='" + getFiliere() + "'" +
            ", serie='" + getSerie() + "'" +
            ", autreFiliere='" + getAutreFiliere() + "'" +
            ", autreSerie='" + getAutreSerie() + "'" +
            ", autreNomEtablissement='" + getAutreNomEtablissement() + "'" +
            "}";
    }
}
