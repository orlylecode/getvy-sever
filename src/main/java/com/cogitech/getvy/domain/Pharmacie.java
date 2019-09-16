package com.cogitech.getvy.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Pharmacie.
 */
@Entity
@Table(name = "pharmacie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pharmacie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

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

    @Column(name = "ville")
    private String ville;

    @Column(name = "region")
    private String region;

    @Column(name = "heure_ouverture")
    private Instant heureOuverture;

    @Column(name = "heure_fermeture")
    private Instant heureFermeture;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "pharmacie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Produit> produits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("pharmacies")
    private Groupe groupe;

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

    public Pharmacie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return lieu;
    }

    public Pharmacie lieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getAdresseRue() {
        return adresseRue;
    }

    public Pharmacie adresseRue(String adresseRue) {
        this.adresseRue = adresseRue;
        return this;
    }

    public void setAdresseRue(String adresseRue) {
        this.adresseRue = adresseRue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public Pharmacie codePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public Pharmacie numeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
        return this;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getEmail() {
        return email;
    }

    public Pharmacie email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVille() {
        return ville;
    }

    public Pharmacie ville(String ville) {
        this.ville = ville;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRegion() {
        return region;
    }

    public Pharmacie region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Instant getHeureOuverture() {
        return heureOuverture;
    }

    public Pharmacie heureOuverture(Instant heureOuverture) {
        this.heureOuverture = heureOuverture;
        return this;
    }

    public void setHeureOuverture(Instant heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public Instant getHeureFermeture() {
        return heureFermeture;
    }

    public Pharmacie heureFermeture(Instant heureFermeture) {
        this.heureFermeture = heureFermeture;
        return this;
    }

    public void setHeureFermeture(Instant heureFermeture) {
        this.heureFermeture = heureFermeture;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Pharmacie latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Pharmacie longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public Pharmacie produits(Set<Produit> produits) {
        this.produits = produits;
        return this;
    }

    public Pharmacie addProduit(Produit produit) {
        this.produits.add(produit);
        produit.setPharmacie(this);
        return this;
    }

    public Pharmacie removeProduit(Produit produit) {
        this.produits.remove(produit);
        produit.setPharmacie(null);
        return this;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public Pharmacie groupe(Groupe groupe) {
        this.groupe = groupe;
        return this;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pharmacie)) {
            return false;
        }
        return id != null && id.equals(((Pharmacie) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pharmacie{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", adresseRue='" + getAdresseRue() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", numeroTel='" + getNumeroTel() + "'" +
            ", email='" + getEmail() + "'" +
            ", ville='" + getVille() + "'" +
            ", region='" + getRegion() + "'" +
            ", heureOuverture='" + getHeureOuverture() + "'" +
            ", heureFermeture='" + getHeureFermeture() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
