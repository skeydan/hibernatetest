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
public class HelpPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "TOPIC")
    private String topic;
    @Basic(optional = false)
    @Column(name = "SEQ")
    private BigInteger seq;

    public HelpPK() {
    }

    public HelpPK(String topic, BigInteger seq) {
        this.topic = topic;
        this.seq = seq;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public BigInteger getSeq() {
        return seq;
    }

    public void setSeq(BigInteger seq) {
        this.seq = seq;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (topic != null ? topic.hashCode() : 0);
        hash += (seq != null ? seq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HelpPK)) {
            return false;
        }
        HelpPK other = (HelpPK) object;
        if ((this.topic == null && other.topic != null) || (this.topic != null && !this.topic.equals(other.topic))) {
            return false;
        }
        if ((this.seq == null && other.seq != null) || (this.seq != null && !this.seq.equals(other.seq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.trivadis.hibernate.hibernatetest.HelpPK[ topic=" + topic + ", seq=" + seq + " ]";
    }
    
}
