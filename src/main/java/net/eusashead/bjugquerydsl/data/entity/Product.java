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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysema.query.annotations.Config;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@XmlRootElement(name="product")
@JsonRootName(value="product")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "Product")
@Table(name = "PUBLIC.PRODUCT")  
@Config(entityAccessors=true)
public class Product implements Comparable<Product>, Serializable {

    private static final long serialVersionUID = 2412624652187678735L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private Integer productId;


    /**
    * name field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;
    
    /**
    * attributes field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.Attribute.class, mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Attribute> attributes = new TreeSet<Attribute>();    

    /**
    * stockKeepingUnits field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit.class, mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<StockKeepingUnit> stockKeepingUnits = new TreeSet<StockKeepingUnit>();    

    /**
    * Default constructor
    */
    public Product() {
    }
    
    /**
    * Simple Property constructor
    */
    public Product(Integer productId, String name) {
        this.productId = productId;
        this.name = name;
    }

    /**
    * Full Property constructor
    */
    public Product(Integer productId, String name, Collection<Attribute> attributes, Collection<StockKeepingUnit> stockKeepingUnits) {
        this.productId = productId;
        this.name = name;
        this.attributes = attributes;
        this.stockKeepingUnits = stockKeepingUnits;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getProductId() {
        return this.productId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setProductId(Integer productId) {
        this.productId = productId;
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
    * Accessor for attributes field
    * @return the value of the attributes field. 
    */
    public Collection<Attribute> getAttributes() {
        return this.attributes;
    }
      
    /**
    * Mutator for attributes field
    * @param attributes the new value for the attributes field
    */        
    public void setAttributes(Collection<Attribute> attributes) {
        this.attributes = attributes;
    }
          
    /**
    * Accessor for stockKeepingUnits field
    * @return the value of the stockKeepingUnits field. 
    */
    public Collection<StockKeepingUnit> getStockKeepingUnits() {
        return this.stockKeepingUnits;
    }
      
    /**
    * Mutator for stockKeepingUnits field
    * @param stockKeepingUnits the new value for the stockKeepingUnits field
    */        
    public void setStockKeepingUnits(Collection<StockKeepingUnit> stockKeepingUnits) {
        this.stockKeepingUnits = stockKeepingUnits;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Product
        if ( !(that instanceof Product) ) return false;

        // Safely cast to Product
        Product thatObj = (Product)that;

        // Equality is based on all field values
        return
            this.getName() == null ? thatObj.getName() == null : this.getName().equals(thatObj.getName())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getName() ? 0 : getName().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Product:";
        str +=  ("Identity = " + (null == productId ? "null" : productId.toString())) + ", ";
        str +=  ("name = " + (null == getName() ? "null" : getName().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Product thatObj) {
    
        int cmp;

        cmp = this.getName() == null ?
                (thatObj.getName() == null ? 0 : -1) :
                (thatObj.getName() == null ? 1 : this.getName().compareTo(thatObj.getName())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}