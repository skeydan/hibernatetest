/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivadis.hibernate.hibernatetest;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author key
 */
@Entity
@Table(name = "FILM_TEXT")
@NamedQueries({
    @NamedQuery(name = "FilmText.findAll", query = "SELECT f FROM FilmText f"),
    @NamedQuery(name = "FilmText.findByFilmId", query = "SELECT f FROM FilmText f WHERE f.filmId = :filmId"),
    @NamedQuery(name = "FilmText.findByTitle", query = "SELECT f FROM FilmText f WHERE f.title = :title")})
public class FilmText implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "FILM_ID")
    private BigDecimal filmId;
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    public FilmText() {
    }

    public FilmText(BigDecimal filmId) {
        this.filmId = filmId;
    }

    public FilmText(BigDecimal filmId, String title) {
        this.filmId = filmId;
        this.title = title;
    }

    public BigDecimal getFilmId() {
        return filmId;
    }

    public void setFilmId(BigDecimal filmId) {
        this.filmId = filmId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filmId != null ? filmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilmText)) {
            return false;
        }
        FilmText other = (FilmText) object;
        if ((this.filmId == null && other.filmId != null) || (this.filmId != null && !this.filmId.equals(other.filmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivadis.hibernate.hibernatetest.FilmText[ filmId=" + filmId + " ]";
    }
    
}
