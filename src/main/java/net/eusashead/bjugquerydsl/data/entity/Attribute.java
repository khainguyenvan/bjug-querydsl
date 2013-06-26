package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysema.query.annotations.Config;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@XmlRootElement(name="attribute")
@JsonRootName(value="attribute")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "Attribute")
@Table(name = "PUBLIC.ATTRIBUTE")  
@Config(entityAccessors=true)
public class Attribute implements Comparable<Attribute>, Serializable {

    private static final long serialVersionUID = 7398598172855286075L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "ATTRIBUTE_ID", nullable = false)
    private Integer attributeId;


    /**
    * name field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;
    
    /**
    * product field
    */
    @NotNull
    @ManyToOne(targetEntity = net.eusashead.bjugquerydsl.data.entity.Product.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID",  nullable = false)
    } )
    private Product product;    

    /**
    * values field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.Value.class, mappedBy = "attribute", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Value> values = new TreeSet<Value>();    

    /**
    * Default constructor
    */
    public Attribute() {
    }
    
    /**
    * Simple Property constructor
    */
    public Attribute(Integer attributeId, String name) {
        this.attributeId = attributeId;
        this.name = name;
    }

    /**
    * Full Property constructor
    */
    public Attribute(Integer attributeId, String name, Product product, Collection<Value> values) {
        this.attributeId = attributeId;
        this.name = name;
        this.product = product;
        this.values = values;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getAttributeId() {
        return this.attributeId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    /**
    * Accessor for name field
    * returns the value of the name field
    */
    public String getName() {
        return this.name;
    }
          
    /**
    * Mutator for the name field
    * @param  sets the value of the name field
    */    
    public void setName(String name) {
      this.name = name;
    }
          
    /**
    * Accessor for product field
    * @return the value of the product field. 
    */
    public Product getProduct() {
        return this.product;
    }
      
    /**
    * Mutator for product field
    * @param product the new value for the product field
    */    
    public void setProduct(Product product) {
        this.product = product;
    }
          
    /**
    * Accessor for values field
    * @return the value of the values field. 
    */
    public Collection<Value> getValues() {
        return this.values;
    }
      
    /**
    * Mutator for values field
    * @param values the new value for the values field
    */        
    public void setValues(Collection<Value> values) {
        this.values = values;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Attribute
        if ( !(that instanceof Attribute) ) return false;

        // Safely cast to Attribute
        Attribute thatObj = (Attribute)that;

        // Equality is based on all field values
        return
            this.getName() == null ? thatObj.getName() == null : this.getName().equals(thatObj.getName())&&
            this.getProduct() == null ? thatObj.getProduct() == null : this.getProduct().equals(thatObj.getProduct())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getName() ? 0 : getName().hashCode());
        hash = 31 * hash + (null == getProduct() ? 0 : getProduct().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Attribute:";
        str +=  ("Identity = " + (null == attributeId ? "null" : attributeId.toString())) + ", ";
        str +=  ("name = " + (null == getName() ? "null" : getName().toString())) + ", ";
        str +=  ("product = " + (null == getProduct() ? "null" : getProduct().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Attribute thatObj) {
    
        int cmp;

        cmp = this.getName() == null ?
                (thatObj.getName() == null ? 0 : -1) :
                (thatObj.getName() == null ? 1 : this.getName().compareTo(thatObj.getName())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getProduct() == null ?
                (thatObj.getProduct() == null ? 0 : -1) :
                (thatObj.getProduct() == null ? 1 : this.getProduct().compareTo(thatObj.getProduct())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}