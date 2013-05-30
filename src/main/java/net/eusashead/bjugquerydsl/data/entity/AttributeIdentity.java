package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class AttributeIdentity implements Comparable<AttributeIdentity>, Serializable {

private static final long serialVersionUID = -5293489121999656023L;

    /**
    * attributeId property
    */
    private Integer attributeId;

    /**
    * productId property
    */
    private Integer productId;

    /**
    * Accessor for attributeId field
    * returns the value of the attributeId field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "ATTRIBUTE_ID",  nullable = false, columnDefinition = "INTEGER")
    public Integer getAttributeId() {
        return this.attributeId;
    }
              
    /**
    * Mutator for the attributeId field
    * @param  sets the value of the attributeId field
    */    
    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }    

    /**
    * Accessor for productId field
    * returns the value of the productId field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "PRODUCT_ID",  nullable = false, columnDefinition = "INTEGER")
    public Integer getProductId() {
        return this.productId;
    }
              
    /**
    * Mutator for the productId field
    * @param  sets the value of the productId field
    */    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of AttributeIdentity
        if ( !(that instanceof AttributeIdentity) ) return false;

        // Safely cast to AttributeIdentity
        AttributeIdentity thatObj = (AttributeIdentity)that;

        // Equality is based on all property values
        return
            this.getAttributeId() == null ? thatObj.getAttributeId() == null : this.getAttributeId().equals(thatObj.getAttributeId())&&
            this.getProductId() == null ? thatObj.getProductId() == null : this.getProductId().equals(thatObj.getProductId())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getAttributeId() ? 0 : getAttributeId().hashCode());
        hash = 31 * hash + (null == getProductId() ? 0 : getProductId().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "AttributeIdentity:";
        str +=  ("attributeId = " + (null == getAttributeId() ? "null" : getAttributeId().toString())) + ", ";
        str +=  ("productId = " + (null == getProductId() ? "null" : getProductId().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(AttributeIdentity thatObj) {
    
        int cmp;

        cmp = this.getAttributeId() == null ?
                (thatObj.getAttributeId() == null ? 0 : -1) :
                (thatObj.getAttributeId() == null ? 1 : this.getAttributeId().compareTo(thatObj.getAttributeId())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getProductId() == null ?
                (thatObj.getProductId() == null ? 0 : -1) :
                (thatObj.getProductId() == null ? 1 : this.getProductId().compareTo(thatObj.getProductId())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}