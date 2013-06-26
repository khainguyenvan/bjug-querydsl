package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

import javax.annotation.Generated;
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
@XmlRootElement(name="basket")
@JsonRootName(value="basket")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "Basket")
@Table(name = "PUBLIC.BASKET")  
@Config(entityAccessors=true)
public class Basket implements Comparable<Basket>, Serializable {

    private static final long serialVersionUID = 3644963079174519043L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "BASKET_ID", nullable = false)
    private Integer basketId;


    /**
    * customer field
    */
    @NotNull
    @ManyToOne(targetEntity = net.eusashead.bjugquerydsl.data.entity.Customer.class, fetch = FetchType.LAZY,  optional = false)
    @JoinColumns( {
        @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMER_ID",  nullable = false)
    } )
    private Customer customer;    

    /**
    * basketItems field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.BasketItem.class, mappedBy = "basket", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<BasketItem> basketItems = new TreeSet<BasketItem>();    

    /**
    * Default constructor
    */
    public Basket() {
    }
    
    /**
    * Simple Property constructor
    */
    public Basket(Integer basketId) {
        this.basketId = basketId;
    }

    /**
    * Full Property constructor
    */
    public Basket(Integer basketId, Customer customer, Collection<BasketItem> basketItems) {
        this.basketId = basketId;
        this.customer = customer;
        this.basketItems = basketItems;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getBasketId() {
        return this.basketId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setBasketId(Integer basketId) {
        this.basketId = basketId;
    }

    /**
    * Accessor for customer field
    * @return the value of the customer field. 
    */
    public Customer getCustomer() {
        return this.customer;
    }
      
    /**
    * Mutator for customer field
    * @param customer the new value for the customer field
    */    
    public void setCustomer(Customer customer) {
        this.customer = customer;
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

        // Check candidate is an instance of Basket
        if ( !(that instanceof Basket) ) return false;

        // Safely cast to Basket
        Basket thatObj = (Basket)that;

        // Equality is based on all field values
        return
        	this.getBasketId() == null ? thatObj.getBasketId() == null : this.getBasketId().equals(thatObj.getBasketId())&& 
            this.getCustomer() == null ? thatObj.getCustomer() == null : this.getCustomer().equals(thatObj.getCustomer())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getBasketId() ? 0 : getBasketId().hashCode());
        hash = 31 * hash + (null == getCustomer() ? 0 : getCustomer().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Basket:";
        str +=  ("Identity = " + (null == basketId ? "null" : basketId.toString())) + ", ";
        str +=  ("customer = " + (null == getCustomer() ? "null" : getCustomer().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Basket thatObj) {
    
        int cmp;

        cmp = this.getBasketId() == null ?
                (thatObj.getBasketId() == null ? 0 : -1) :
                (thatObj.getBasketId() == null ? 1 : this.getBasketId().compareTo(thatObj.getBasketId())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getCustomer() == null ?
                (thatObj.getCustomer() == null ? 0 : -1) :
                (thatObj.getCustomer() == null ? 1 : this.getCustomer().compareTo(thatObj.getCustomer())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}