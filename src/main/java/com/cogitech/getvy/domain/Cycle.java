package com.cogitech.getvy.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cycle.
 */
@Entity
@Table(name = "cycle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cycle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private Instant dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private Instant dateFin;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "cycle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Groupe> groupes = new HashSet<>();

    @OneToMany(mappedBy = "cycle")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JourDeGarde> jourDeGardes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Cycle dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public Cycle dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getLibelle() {
        return libelle;
    }

    public Cycle libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Groupe> getGroupes() {
        return groupes;
    }

    public Cycle groupes(Set<Groupe> groupes) {
        this.groupes = groupes;
        return this;
    }

    public Cycle addGroupe(Groupe groupe) {
        this.groupes.add(groupe);
        groupe.setCycle(this);
        return this;
    }

    public Cycle removeGroupe(Groupe groupe) {
        this.groupes.remove(groupe);
        groupe.setCycle(null);
        return this;
    }

    public void setGroupes(Set<Groupe> groupes) {
        this.groupes = groupes;
    }

    public Set<JourDeGarde> getJourDeGardes() {
        return jourDeGardes;
    }

    public Cycle jourDeGardes(Set<JourDeGarde> jourDeGardes) {
        this.jourDeGardes = jourDeGardes;
        return this;
    }

    public Cycle addJourDeGarde(JourDeGarde jourDeGarde) {
        this.jourDeGardes.add(jourDeGarde);
        jourDeGarde.setCycle(this);
        return this;
    }

    public Cycle removeJourDeGarde(JourDeGarde jourDeGarde) {
        this.jourDeGardes.remove(jourDeGarde);
        jourDeGarde.setCycle(null);
        return this;
    }

    public void setJourDeGardes(Set<JourDeGarde> jourDeGardes) {
        this.jourDeGardes = jourDeGardes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cycle)) {
            return false;
        }
        return id != null && id.equals(((Cycle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cycle{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
