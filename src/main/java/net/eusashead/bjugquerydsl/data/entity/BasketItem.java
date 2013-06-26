package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysema.query.annotations.Config;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@XmlRootElement(name="basketItem")
@JsonRootName(value="basketItem")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "BasketItem")
@Table(name = "PUBLIC.BASKET_ITEM")  
@Config(entityAccessors=true)
public class BasketItem implements Comparable<BasketItem>, Serializable {

    private static final long serialVersionUID = -4225340977271544992L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "BASKET_ITEM_ID", nullable = false)
    private Integer basketItemId;


    /**
    * added field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "ADDED",  nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date added;
    
    /**
    * saveForLater field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "SAVE_FOR_LATER",  nullable = false, columnDefinition = "BOOLEAN")
    private Boolean saveForLater;
    
    /**
    * basket field
    */
    @NotNull
    @ManyToOne(targetEntity = net.eusashead.bjugquerydsl.data.entity.Basket.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "BASKET_ID", referencedColumnName = "BASKET_ID",  nullable = false)
    } )
    private Basket basket;    

    /**
    * inventory field
    */
    @NotNull
    @ManyToOne(targetEntity = net.eusashead.bjugquerydsl.data.entity.Inventory.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "INVENTORY_ID", referencedColumnName = "INVENTORY_ID",  nullable = false)
    } )
    private Inventory inventory;    

    /**
    * Default constructor
    */
    public BasketItem() {
    }
    
    /**
    * Simple Property constructor
    */
    public BasketItem(Integer basketItemId, Date added, Boolean saveForLater) {
        this.basketItemId = basketItemId;
        this.added = added;
        this.saveForLater = saveForLater;
    }

    /**
    * Full Property constructor
    */
    public BasketItem(Integer basketItemId, Date added, Boolean saveForLater, Basket basket, Inventory inventory) {
        this.basketItemId = basketItemId;
        this.added = added;
        this.saveForLater = saveForLater;
        this.basket = basket;
        this.inventory = inventory;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getBasketItemId() {
        return this.basketItemId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setBasketItemId(Integer basketItemId) {
        this.basketItemId = basketItemId;
    }

    /**
    * Accessor for added field
    * returns the value of the added field
    */
    public Date getAdded() {
        return this.added;
    }
          
    /**
    * Mutator for the added field
    * @param  sets the value of the added field
    */    
    public void setAdded(Date added) {
      this.added = added;
    }
          
    /**
    * Accessor for saveForLater field
    * returns the value of the saveForLater field
    */
    public Boolean getSaveForLater() {
        return this.saveForLater;
    }
          
    /**
    * Mutator for the saveForLater field
    * @param  sets the value of the saveForLater field
    */    
    public void setSaveForLater(Boolean saveForLater) {
      this.saveForLater = saveForLater;
    }
          
    /**
    * Accessor for basket field
    * @return the value of the basket field. 
    */
    public Basket getBasket() {
        return this.basket;
    }
      
    /**
    * Mutator for basket field
    * @param basket the new value for the basket field
    */    
    public void setBasket(Basket basket) {
        this.basket = basket;
    }
          
    /**
    * Accessor for inventory field
    * @return the value of the inventory field. 
    */
    public Inventory getInventory() {
        return this.inventory;
    }
      
    /**
    * Mutator for inventory field
    * @param inventory the new value for the inventory field
    */    
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of BasketItem
        if ( !(that instanceof BasketItem) ) return false;

        // Safely cast to BasketItem
        BasketItem thatObj = (BasketItem)that;

        // Equality is based on all field values
        return
            this.getAdded() == null ? thatObj.getAdded() == null : this.getAdded().equals(thatObj.getAdded())&&
            this.getSaveForLater() == null ? thatObj.getSaveForLater() == null : this.getSaveForLater().equals(thatObj.getSaveForLater())&&
            this.getBasket() == null ? thatObj.getBasket() == null : this.getBasket().equals(thatObj.getBasket())&&        
            this.getInventory() == null ? thatObj.getInventory() == null : this.getInventory().equals(thatObj.getInventory())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getAdded() ? 0 : getAdded().hashCode());
        hash = 31 * hash + (null == getSaveForLater() ? 0 : getSaveForLater().hashCode());
        hash = 31 * hash + (null == getBasket() ? 0 : getBasket().hashCode());
        hash = 31 * hash + (null == getInventory() ? 0 : getInventory().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "BasketItem:";
        str +=  ("Identity = " + (null == basketItemId ? "null" : basketItemId.toString())) + ", ";
        str +=  ("added = " + (null == getAdded() ? "null" : getAdded().toString())) + ", ";
        str +=  ("saveForLater = " + (null == getSaveForLater() ? "null" : getSaveForLater().toString())) + ", ";
        str +=  ("basket = " + (null == getBasket() ? "null" : getBasket().toString())) + ", ";
        str +=  ("inventory = " + (null == getInventory() ? "null" : getInventory().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(BasketItem thatObj) {
    
        int cmp;

        cmp = this.getAdded() == null ?
                (thatObj.getAdded() == null ? 0 : -1) :
                (thatObj.getAdded() == null ? 1 : this.getAdded().compareTo(thatObj.getAdded())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getSaveForLater() == null ?
                (thatObj.getSaveForLater() == null ? 0 : -1) :
                (thatObj.getSaveForLater() == null ? 1 : this.getSaveForLater().compareTo(thatObj.getSaveForLater())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getBasket() == null ?
                (thatObj.getBasket() == null ? 0 : -1) :
                (thatObj.getBasket() == null ? 1 : this.getBasket().compareTo(thatObj.getBasket())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getInventory() == null ?
                (thatObj.getInventory() == null ? 0 : -1) :
                (thatObj.getInventory() == null ? 1 : this.getInventory().compareTo(thatObj.getInventory())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}