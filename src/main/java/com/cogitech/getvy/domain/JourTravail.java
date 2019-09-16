package com.cogitech.getvy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A JourTravail.
 */
@Entity
@Table(name = "jour_travail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class JourTravail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jour", nullable = false)
    private Instant jour;

    @ManyToOne
    @JsonIgnoreProperties("jourTravails")
    private Laboratoire laboratoire;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getJour() {
        return jour;
    }

    public JourTravail jour(Instant jour) {
        this.jour = jour;
        return this;
    }

    public void setJour(Instant jour) {
        this.jour = jour;
    }

    public Laboratoire getLaboratoire() {
        return laboratoire;
    }

    public JourTravail laboratoire(Laboratoire laboratoire) {
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
        if (!(o instanceof JourTravail)) {
            return false;
        }
        return id != null && id.equals(((JourTravail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "JourTravail{" +
            "id=" + getId() +
            ", jour='" + getJour() + "'" +
            "}";
    }
}
