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
 * A Laboratoire.
 */
@Entity
@Table(name = "laboratoire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Laboratoire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "lieu")
    private String lieu;

    @Column(name = "adresse_rue")
    private String adresseRue;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "numero_tel")
    private String numeroTel;

    @Column(name = "email")
    private String email;

    @Column(name = "heure_ouverture")
    private Instant heureOuverture;

    @Column(name = "heure_fermeture")
    private Instant heureFermeture;

    @Column(name = "ville")
    private String ville;

    @Column(name = "region")
    private String region;

    @OneToMany(mappedBy = "laboratoire")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Examen> examen = new HashSet<>();

    @OneToMany(mappedBy = "laboratoire")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JourTravail> jourNonOuvrables = new HashSet<>();

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

    public Laboratoire nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Laboratoire latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Laboratoire longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLieu() {
        return lieu;
    }

    public Laboratoire lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getAdresseRue() {
        return adresseRue;
    }

    public Laboratoire adresseRue(String adresseRue) {
        this.adresseRue = adresseRue;
        return this;
    }

    public void setAdresseRue(String adresseRue) {
        this.adresseRue = adresseRue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public Laboratoire codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public Laboratoire numeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
        return this;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getEmail() {
        return email;
    }

    public Laboratoire email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getHeureOuverture() {
        return heureOuverture;
    }

    public Laboratoire heureOuverture(Instant heureOuverture) {
        this.heureOuverture = heureOuverture;
        return this;
    }

    public void setHeureOuverture(Instant heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public Instant getHeureFermeture() {
        return heureFermeture;
    }

    public Laboratoire heureFermeture(Instant heureFermeture) {
        this.heureFermeture = heureFermeture;
        return this;
    }

    public void setHeureFermeture(Instant heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public String getVille() {
        return ville;
    }

    public Laboratoire ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRegion() {
        return region;
    }

    public Laboratoire region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Set<Examen> getExamen() {
        return examen;
    }

    public Laboratoire examen(Set<Examen> examen) {
        this.examen = examen;
        return this;
    }

    public Laboratoire addExamen(Examen examen) {
        this.examen.add(examen);
        examen.setLaboratoire(this);
        return this;
    }

    public Laboratoire removeExamen(Examen examen) {
        this.examen.remove(examen);
        examen.setLaboratoire(null);
        return this;
    }

    public void setExamen(Set<Examen> examen) {
        this.examen = examen;
    }

    public Set<JourTravail> getJourNonOuvrables() {
        return jourNonOuvrables;
    }

    public Laboratoire jourNonOuvrables(Set<JourTravail> jourTravails) {
        this.jourNonOuvrables = jourTravails;
        return this;
    }

    public Laboratoire addJourNonOuvrable(JourTravail jourTravail) {
        this.jourNonOuvrables.add(jourTravail);
        jourTravail.setLaboratoire(this);
        return this;
    }

    public Laboratoire removeJourNonOuvrable(JourTravail jourTravail) {
        this.jourNonOuvrables.remove(jourTravail);
        jourTravail.setLaboratoire(null);
        return this;
    }

    public void setJourNonOuvrables(Set<JourTravail> jourTravails) {
        this.jourNonOuvrables = jourTravails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Laboratoire)) {
            return false;
        }
        return id != null && id.equals(((Laboratoire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Laboratoire{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", lieu='" + getLieu() + "'" +
            ", adresseRue='" + getAdresseRue() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", numeroTel='" + getNumeroTel() + "'" +
            ", email='" + getEmail() + "'" +
            ", heureOuverture='" + getHeureOuverture() + "'" +
            ", heureFermeture='" + getHeureFermeture() + "'" +
            ", ville='" + getVille() + "'" +
            ", region='" + getRegion() + "'" +
            "}";
    }
}
