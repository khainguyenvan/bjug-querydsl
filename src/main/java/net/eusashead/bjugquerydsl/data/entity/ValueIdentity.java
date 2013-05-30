package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.annotation.Generated;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityComponentIdentityGenerator")
@Embeddable    
public class ValueIdentity implements Comparable<ValueIdentity>, Serializable {

private static final long serialVersionUID = 3540522776270654711L;

    /**
    * attributeId property
    */
    private Integer attributeId;

    /**
    * productId property
    */
    private Integer productId;

    /**
    * valueId property
    */
    private Integer valueId;

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

    /**
    * Accessor for valueId field
    * returns the value of the valueId field
    */
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "VALUE_ID",  nullable = false, columnDefinition = "INTEGER")
    public Integer getValueId() {
        return this.valueId;
    }
              
    /**
    * Mutator for the valueId field
    * @param  sets the value of the valueId field
    */    
    public void setValueId(Integer valueId) {
        this.valueId = valueId;
    }    

    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of ValueIdentity
        if ( !(that instanceof ValueIdentity) ) return false;

        // Safely cast to ValueIdentity
        ValueIdentity thatObj = (ValueIdentity)that;

        // Equality is based on all property values
        return
            this.getAttributeId() == null ? thatObj.getAttributeId() == null : this.getAttributeId().equals(thatObj.getAttributeId())&&
            this.getProductId() == null ? thatObj.getProductId() == null : this.getProductId().equals(thatObj.getProductId())&&
            this.getValueId() == null ? thatObj.getValueId() == null : this.getValueId().equals(thatObj.getValueId())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all properties
        hash = 31 * hash + (null == getAttributeId() ? 0 : getAttributeId().hashCode());
        hash = 31 * hash + (null == getProductId() ? 0 : getProductId().hashCode());
        hash = 31 * hash + (null == getValueId() ? 0 : getValueId().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "ValueIdentity:";
        str +=  ("attributeId = " + (null == getAttributeId() ? "null" : getAttributeId().toString())) + ", ";
        str +=  ("productId = " + (null == getProductId() ? "null" : getProductId().toString())) + ", ";
        str +=  ("valueId = " + (null == getValueId() ? "null" : getValueId().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(ValueIdentity thatObj) {
    
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
        cmp = this.getValueId() == null ?
                (thatObj.getValueId() == null ? 0 : -1) :
                (thatObj.getValueId() == null ? 1 : this.getValueId().compareTo(thatObj.getValueId())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }   
}