package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServiceMFPAI.
 */
@Entity
@Table(name = "service_mfpai")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServiceMFPAI implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "image_service")
    private byte[] imageService;

    @Column(name = "image_service_content_type")
    private String imageServiceContentType;

    @Column(name = "nom_service", unique = true)
    private String nomService;

    @NotNull
    @Column(name = "chef_service", nullable = false)
    private String chefService;

    @Lob
    @Column(name = "description")
    private String description;

    @JsonIgnoreProperties(value = { "user", "serviceMFPAI" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "serviceMFPAI")
    private Agent agent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServiceMFPAI id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageService() {
        return this.imageService;
    }

    public ServiceMFPAI imageService(byte[] imageService) {
        this.setImageService(imageService);
        return this;
    }

    public void setImageService(byte[] imageService) {
        this.imageService = imageService;
    }

    public String getImageServiceContentType() {
        return this.imageServiceContentType;
    }

    public ServiceMFPAI imageServiceContentType(String imageServiceContentType) {
        this.imageServiceContentType = imageServiceContentType;
        return this;
    }

    public void setImageServiceContentType(String imageServiceContentType) {
        this.imageServiceContentType = imageServiceContentType;
    }

    public String getNomService() {
        return this.nomService;
    }

    public ServiceMFPAI nomService(String nomService) {
        this.setNomService(nomService);
        return this;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public String getChefService() {
        return this.chefService;
    }

    public ServiceMFPAI chefService(String chefService) {
        this.setChefService(chefService);
        return this;
    }

    public void setChefService(String chefService) {
        this.chefService = chefService;
    }

    public String getDescription() {
        return this.description;
    }

    public ServiceMFPAI description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public void setAgent(Agent agent) {
        if (this.agent != null) {
            this.agent.setServiceMFPAI(null);
        }
        if (agent != null) {
            agent.setServiceMFPAI(this);
        }
        this.agent = agent;
    }

    public ServiceMFPAI agent(Agent agent) {
        this.setAgent(agent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceMFPAI)) {
            return false;
        }
        return getId() != null && getId().equals(((ServiceMFPAI) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceMFPAI{" +
            "id=" + getId() +
            ", imageService='" + getImageService() + "'" +
            ", imageServiceContentType='" + getImageServiceContentType() + "'" +
            ", nomService='" + getNomService() + "'" +
            ", chefService='" + getChefService() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
