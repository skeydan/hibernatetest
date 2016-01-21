/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trivadis.hibernate.hibernatetest;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author key
 */
@Entity
@Table(name = "HELP")
@NamedQueries({
    @NamedQuery(name = "Help.findAll", query = "SELECT h FROM Help h"),
    @NamedQuery(name = "Help.findByTopic", query = "SELECT h FROM Help h WHERE h.helpPK.topic = :topic"),
    @NamedQuery(name = "Help.findBySeq", query = "SELECT h FROM Help h WHERE h.helpPK.seq = :seq"),
    @NamedQuery(name = "Help.findByInfo", query = "SELECT h FROM Help h WHERE h.info = :info")})
public class Help implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HelpPK helpPK;
    @Column(name = "INFO")
    private String info;

    public Help() {
    }

    public Help(HelpPK helpPK) {
        this.helpPK = helpPK;
    }

    public Help(String topic, BigInteger seq) {
        this.helpPK = new HelpPK(topic, seq);
    }

    public HelpPK getHelpPK() {
        return helpPK;
    }

    public void setHelpPK(HelpPK helpPK) {
        this.helpPK = helpPK;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (helpPK != null ? helpPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Help)) {
            return false;
        }
        Help other = (Help) object;
        if ((this.helpPK == null && other.helpPK != null) || (this.helpPK != null && !this.helpPK.equals(other.helpPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivadis.hibernate.hibernatetest.Help[ helpPK=" + helpPK + " ]";
    }
    
}
