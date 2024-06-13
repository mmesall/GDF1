package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Mention;
import com.mycompany.myapp.domain.enumeration.NiveauEtude;
import com.mycompany.myapp.domain.enumeration.NomFiliere;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Diplome.
 */
@Entity
@Table(name = "diplome")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Diplome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "domaine", nullable = false)
    private NomFiliere domaine;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau")
    private NiveauEtude niveau;

    @Enumerated(EnumType.STRING)
    @Column(name = "mention")
    private Mention mention;

    @Column(name = "annee_obtention")
    private String anneeObtention;

    @Column(name = "etablissement")
    private String etablissement;

    @Lob
    @Column(name = "document", nullable = false)
    private byte[] document;

    @NotNull
    @Column(name = "document_content_type", nullable = false)
    private String documentContentType;

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

    public Diplome id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return this.intitule;
    }

    public Diplome intitule(String intitule) {
        this.setIntitule(intitule);
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public NomFiliere getDomaine() {
        return this.domaine;
    }

    public Diplome domaine(NomFiliere domaine) {
        this.setDomaine(domaine);
        return this;
    }

    public void setDomaine(NomFiliere domaine) {
        this.domaine = domaine;
    }

    public NiveauEtude getNiveau() {
        return this.niveau;
    }

    public Diplome niveau(NiveauEtude niveau) {
        this.setNiveau(niveau);
        return this;
    }

    public void setNiveau(NiveauEtude niveau) {
        this.niveau = niveau;
    }

    public Mention getMention() {
        return this.mention;
    }

    public Diplome mention(Mention mention) {
        this.setMention(mention);
        return this;
    }

    public void setMention(Mention mention) {
        this.mention = mention;
    }

    public String getAnneeObtention() {
        return this.anneeObtention;
    }

    public Diplome anneeObtention(String anneeObtention) {
        this.setAnneeObtention(anneeObtention);
        return this;
    }

    public void setAnneeObtention(String anneeObtention) {
        this.anneeObtention = anneeObtention;
    }

    public String getEtablissement() {
        return this.etablissement;
    }

    public Diplome etablissement(String etablissement) {
        this.setEtablissement(etablissement);
        return this;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public byte[] getDocument() {
        return this.document;
    }

    public Diplome document(byte[] document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getDocumentContentType() {
        return this.documentContentType;
    }

    public Diplome documentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
        return this;
    }

    public void setDocumentContentType(String documentContentType) {
        this.documentContentType = documentContentType;
    }

    public Eleve getEleve() {
        return this.eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Diplome eleve(Eleve eleve) {
        this.setEleve(eleve);
        return this;
    }

    public Etudiant getEtudiant() {
        return this.etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Diplome etudiant(Etudiant etudiant) {
        this.setEtudiant(etudiant);
        return this;
    }

    public Professionnel getProfessionnel() {
        return this.professionnel;
    }

    public void setProfessionnel(Professionnel professionnel) {
        this.professionnel = professionnel;
    }

    public Diplome professionnel(Professionnel professionnel) {
        this.setProfessionnel(professionnel);
        return this;
    }

    public Demandeur getDemandeur() {
        return this.demandeur;
    }

    public void setDemandeur(Demandeur demandeur) {
        this.demandeur = demandeur;
    }

    public Diplome demandeur(Demandeur demandeur) {
        this.setDemandeur(demandeur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diplome)) {
            return false;
        }
        return getId() != null && getId().equals(((Diplome) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diplome{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", domaine='" + getDomaine() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", mention='" + getMention() + "'" +
            ", anneeObtention='" + getAnneeObtention() + "'" +
            ", etablissement='" + getEtablissement() + "'" +
            ", document='" + getDocument() + "'" +
            ", documentContentType='" + getDocumentContentType() + "'" +
            "}";
    }
}
