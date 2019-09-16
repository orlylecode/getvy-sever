package com.cogitech.getvy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Examen.
 */
@Entity
@Table(name = "examen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Examen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "prix", precision = 21, scale = 2)
    private BigDecimal prix;

    @Column(name = "disponibilite")
    private Boolean disponibilite;

    @ManyToOne
    @JsonIgnoreProperties("examen")
    private Laboratoire laboratoire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Examen nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Examen description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public Examen prix(BigDecimal prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Boolean isDisponibilite() {
        return disponibilite;
    }

    public Examen disponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
        return this;
    }

    public void setDisponibilite(Boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Laboratoire getLaboratoire() {
        return laboratoire;
    }

    public Examen laboratoire(Laboratoire laboratoire) {
        this.laboratoire = laboratoire;
        return this;
    }

    public void setLaboratoire(Laboratoire laboratoire) {
        this.laboratoire = laboratoire;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        return id != null && id.equals(((Examen) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Examen{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", prix=" + getPrix() +
            ", disponibilite='" + isDisponibilite() + "'" +
            "}";
    }
}
