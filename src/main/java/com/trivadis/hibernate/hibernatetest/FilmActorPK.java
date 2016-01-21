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
public class FilmActorPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ACTOR_ID")
    private BigInteger actorId;
    @Basic(optional = false)
    @Column(name = "FILM_ID")
    private BigInteger filmId;

    public FilmActorPK() {
    }

    public FilmActorPK(BigInteger actorId, BigInteger filmId) {
        this.actorId = actorId;
        this.filmId = filmId;
    }

    public BigInteger getActorId() {
        return actorId;
    }

    public void setActorId(BigInteger actorId) {
        this.actorId = actorId;
    }

    public BigInteger getFilmId() {
        return filmId;
    }

    public void setFilmId(BigInteger filmId) {
        this.filmId = filmId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actorId != null ? actorId.hashCode() : 0);
        hash += (filmId != null ? filmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FilmActorPK)) {
            return false;
        }
        FilmActorPK other = (FilmActorPK) object;
        if ((this.actorId == null && other.actorId != null) || (this.actorId != null && !this.actorId.equals(other.actorId))) {
            return false;
        }
        if ((this.filmId == null && other.filmId != null) || (this.filmId != null && !this.filmId.equals(other.filmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivadis.hibernate.hibernatetest.FilmActorPK[ actorId=" + actorId + ", filmId=" + filmId + " ]";
    }
    
}
