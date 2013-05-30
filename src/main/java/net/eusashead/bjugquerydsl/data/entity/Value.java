package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;
import net.eusashead.bjugquerydsl.data.entity.Attribute;
import net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit;

import javax.persistence.*;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysema.query.annotations.Config;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@XmlRootElement(name="value")
@JsonRootName(value="value")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "Value")
@Table(name = "PUBLIC.VALUE")  
@Config(entityAccessors=true)
public class Value implements Comparable<Value>, Serializable {

    private static final long serialVersionUID = 3155258155077930254L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "VALUE_ID", nullable = false)
    private Integer valueId;


    /**
    * name field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;
    
    /**
    * attribute field
    */
    @NotNull
    @ManyToOne(targetEntity = net.eusashead.bjugquerydsl.data.entity.Attribute.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "ATTRIBUTE_ID", referencedColumnName = "ATTRIBUTE_ID",  nullable = false)
    } )
    private Attribute attribute;    

    /**
    * stockKeepingUnits field
    */
    @ManyToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
        name = "PUBLIC.PRODUCT_VARIANT",
        joinColumns = { @JoinColumn(name = "VALUE_ID", referencedColumnName = "VALUE_ID", nullable = false) },
        inverseJoinColumns = { @JoinColumn(name = "SKU_ID", referencedColumnName = "SKU_ID", nullable = false) }
    )
    private Collection<StockKeepingUnit> stockKeepingUnits = new TreeSet<StockKeepingUnit>();

    /**
    * Default constructor
    */
    public Value() {
    }
    
    /**
    * Simple Property constructor
    */
    public Value(Integer valueId, String name) {
        this.valueId = valueId;
        this.name = name;
    }

    /**
    * Full Property constructor
    */
    public Value(Integer valueId, String name, Attribute attribute, Collection<StockKeepingUnit> stockKeepingUnits) {
        this.valueId = valueId;
        this.name = name;
        this.attribute = attribute;
        this.stockKeepingUnits = stockKeepingUnits;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getValueId() {
        return this.valueId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setValueId(Integer valueId) {
        this.valueId = valueId;
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
    * Accessor for attribute field
    * @return the value of the attribute field. 
    */
    public Attribute getAttribute() {
        return this.attribute;
    }
      
    /**
    * Mutator for attribute field
    * @param attribute the new value for the attribute field
    */    
    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
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

        // Check candidate is an instance of Value
        if ( !(that instanceof Value) ) return false;

        // Safely cast to Value
        Value thatObj = (Value)that;

        // Equality is based on all field values
        return
            this.getName() == null ? thatObj.getName() == null : this.getName().equals(thatObj.getName())&&
            this.getAttribute() == null ? thatObj.getAttribute() == null : this.getAttribute().equals(thatObj.getAttribute())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getName() ? 0 : getName().hashCode());
        hash = 31 * hash + (null == getAttribute() ? 0 : getAttribute().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Value:";
        str +=  ("Identity = " + (null == valueId ? "null" : valueId.toString())) + ", ";
        str +=  ("name = " + (null == getName() ? "null" : getName().toString())) + ", ";
        str +=  ("attribute = " + (null == getAttribute() ? "null" : getAttribute().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Value thatObj) {
    
        int cmp;

        cmp = this.getName() == null ?
                (thatObj.getName() == null ? 0 : -1) :
                (thatObj.getName() == null ? 1 : this.getName().compareTo(thatObj.getName())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getAttribute() == null ?
                (thatObj.getAttribute() == null ? 0 : -1) :
                (thatObj.getAttribute() == null ? 1 : this.getAttribute().compareTo(thatObj.getAttribute())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}