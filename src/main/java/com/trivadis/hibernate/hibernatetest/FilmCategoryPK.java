/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivadis.hibernate.hibernatetest;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author key
 */
@Embeddable
public class FilmCategoryPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "FILM_ID")
    private BigInteger filmId;
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    private BigInteger categoryId;

    public FilmCategoryPK() {
    }

    public FilmCategoryPK(BigInteger filmId, BigInteger categoryId) {
        this.filmId = filmId;
        this.categoryId = categoryId;
    }

    public BigInteger getFilmId() {
        return filmId;
    }

    public void setFilmId(BigInteger filmId) {
        this.filmId = filmId;
    }

    public BigInteger getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(BigInteger categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (filmId != null ? filmId.hashCode() : 0);
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilmCategoryPK)) {
            return false;
        }
        FilmCategoryPK other = (FilmCategoryPK) object;
        if ((this.filmId == null && other.filmId != null) || (this.filmId != null && !this.filmId.equals(other.filmId))) {
            return false;
        }
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivadis.hibernate.hibernatetest.FilmCategoryPK[ filmId=" + filmId + ", categoryId=" + categoryId + " ]";
    }
    
}
