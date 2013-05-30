package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;
import java.util.Date;
import net.eusashead.bjugquerydsl.data.entity.BasketItem;
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
@XmlRootElement(name="inventory")
@JsonRootName(value="inventory")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "Inventory")
@Table(name = "PUBLIC.INVENTORY", uniqueConstraints = { @UniqueConstraint(columnNames = { "LABEL" }) })  
@Config(entityAccessors=true)
public class Inventory implements Comparable<Inventory>, Serializable {

    private static final long serialVersionUID = 745600250204467572L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "INVENTORY_ID", nullable = false)
    private Integer inventoryId;


    /**
    * created field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "CREATED",  nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    /**
    * label field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "LABEL",  nullable = false, columnDefinition = "VARCHAR(36)")
    private String label;
    
    /**
    * sold field
    */
    @Basic(fetch = FetchType.EAGER, optional = true)
    @Column(name = "SOLD",  nullable = true, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sold;
    
    /**
    * stockKeepingUnit field
    */
    @NotNull
    @ManyToOne(targetEntity = net.eusashead.bjugquerydsl.data.entity.StockKeepingUnit.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "SKU_ID", referencedColumnName = "SKU_ID",  nullable = false)
    } )
    private StockKeepingUnit stockKeepingUnit;    

    /**
    * basketItems field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.BasketItem.class, mappedBy = "inventory", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<BasketItem> basketItems = new TreeSet<BasketItem>();    

    /**
    * Default constructor
    */
    public Inventory() {
    }
    
    /**
    * Simple Property constructor
    */
    public Inventory(Integer inventoryId, Date created, String label, Date sold) {
        this.inventoryId = inventoryId;
        this.created = created;
        this.label = label;
        this.sold = sold;
    }

    /**
    * Full Property constructor
    */
    public Inventory(Integer inventoryId, Date created, String label, Date sold, StockKeepingUnit stockKeepingUnit, Collection<BasketItem> basketItems) {
        this.inventoryId = inventoryId;
        this.created = created;
        this.label = label;
        this.sold = sold;
        this.stockKeepingUnit = stockKeepingUnit;
        this.basketItems = basketItems;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getInventoryId() {
        return this.inventoryId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
    * Accessor for created field
    * returns the value of the created field
    */
    public Date getCreated() {
        return this.created;
    }
          
    /**
    * Mutator for the created field
    * @param  sets the value of the created field
    */    
    public void setCreated(Date created) {
      this.created = created;
    }
          
    /**
    * Accessor for label field
    * returns the value of the label field
    */
    public String getLabel() {
        return this.label;
    }
          
    /**
    * Mutator for the label field
    * @param  sets the value of the label field
    */    
    public void setLabel(String label) {
      this.label = label;
    }
          
    /**
    * Accessor for sold field
    * returns the value of the sold field
    */
    public Date getSold() {
        return this.sold;
    }
          
    /**
    * Mutator for the sold field
    * @param  sets the value of the sold field
    */    
    public void setSold(Date sold) {
      this.sold = sold;
    }
          
    /**
    * Accessor for stockKeepingUnit field
    * @return the value of the stockKeepingUnit field. 
    */
    public StockKeepingUnit getStockKeepingUnit() {
        return this.stockKeepingUnit;
    }
      
    /**
    * Mutator for stockKeepingUnit field
    * @param stockKeepingUnit the new value for the stockKeepingUnit field
    */    
    public void setStockKeepingUnit(StockKeepingUnit stockKeepingUnit) {
        this.stockKeepingUnit = stockKeepingUnit;
    }
          
    /**
    * Accessor for basketItems field
    * @return the value of the basketItems field. 
    */
    public Collection<BasketItem> getBasketItems() {
        return this.basketItems;
    }
      
    /**
    * Mutator for basketItems field
    * @param basketItems the new value for the basketItems field
    */        
    public void setBasketItems(Collection<BasketItem> basketItems) {
        this.basketItems = basketItems;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Inventory
        if ( !(that instanceof Inventory) ) return false;

        // Safely cast to Inventory
        Inventory thatObj = (Inventory)that;

        // Equality is based on all field values
        return
            this.getCreated() == null ? thatObj.getCreated() == null : this.getCreated().equals(thatObj.getCreated())&&
            this.getLabel() == null ? thatObj.getLabel() == null : this.getLabel().equals(thatObj.getLabel())&&
            this.getStockKeepingUnit() == null ? thatObj.getStockKeepingUnit() == null : this.getStockKeepingUnit().equals(thatObj.getStockKeepingUnit())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getCreated() ? 0 : getCreated().hashCode());
        hash = 31 * hash + (null == getLabel() ? 0 : getLabel().hashCode());
        hash = 31 * hash + (null == getStockKeepingUnit() ? 0 : getStockKeepingUnit().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Inventory:";
        str +=  ("Identity = " + (null == inventoryId ? "null" : inventoryId.toString())) + ", ";
        str +=  ("created = " + (null == getCreated() ? "null" : getCreated().toString())) + ", ";
        str +=  ("label = " + (null == getLabel() ? "null" : getLabel().toString())) + ", ";
        str +=  ("sold = " + (null == getSold() ? "null" : getSold().toString())) + ", ";
        str +=  ("stockKeepingUnit = " + (null == getStockKeepingUnit() ? "null" : getStockKeepingUnit().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Inventory thatObj) {
    
        int cmp;

        cmp = this.getCreated() == null ?
                (thatObj.getCreated() == null ? 0 : -1) :
                (thatObj.getCreated() == null ? 1 : this.getCreated().compareTo(thatObj.getCreated())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getLabel() == null ?
                (thatObj.getLabel() == null ? 0 : -1) :
                (thatObj.getLabel() == null ? 1 : this.getLabel().compareTo(thatObj.getLabel())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getStockKeepingUnit() == null ?
                (thatObj.getStockKeepingUnit() == null ? 0 : -1) :
                (thatObj.getStockKeepingUnit() == null ? 1 : this.getStockKeepingUnit().compareTo(thatObj.getStockKeepingUnit())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}