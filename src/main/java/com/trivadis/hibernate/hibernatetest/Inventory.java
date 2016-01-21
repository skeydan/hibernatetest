/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivadis.hibernate.hibernatetest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author key
 */
@Entity
@Table(name = "INVENTORY")
@NamedQueries({
    @NamedQuery(name = "Inventory.findAll", query = "SELECT i FROM Inventory i"),
    @NamedQuery(name = "Inventory.findByInventoryId", query = "SELECT i FROM Inventory i WHERE i.inventoryId = :inventoryId"),
    @NamedQuery(name = "Inventory.findByLastUpdate", query = "SELECT i FROM Inventory i WHERE i.lastUpdate = :lastUpdate")})
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "INVENTORY_ID")
    private BigDecimal inventoryId;
    @Basic(optional = false)
    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @JoinColumn(name = "FILM_ID", referencedColumnName = "FILM_ID")
    @ManyToOne(optional = false)
    private Film film;
    @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID")
    @ManyToOne(optional = false)
    private Store store;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryId")
    private Set<Rental> rentalSet;

    public Inventory() {
    }

    public Inventory(BigDecimal inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Inventory(BigDecimal inventoryId, Date lastUpdate) {
        this.inventoryId = inventoryId;
        this.lastUpdate = lastUpdate;
    }

    public BigDecimal getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(BigDecimal inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Rental> getRentalSet() {
        return rentalSet;
    }

    public void setRentalSet(Set<Rental> rentalSet) {
        this.rentalSet = rentalSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventoryId != null ? inventoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        if ((this.inventoryId == null && other.inventoryId != null) || (this.inventoryId != null && !this.inventoryId.equals(other.inventoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivadis.hibernate.hibernatetest.Inventory[ inventoryId=" + inventoryId + " ]";
    }
    
}
