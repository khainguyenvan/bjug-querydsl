package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;
import java.math.BigDecimal;
import net.eusashead.bjugquerydsl.data.entity.Inventory;
import net.eusashead.bjugquerydsl.data.entity.Product;
import net.eusashead.bjugquerydsl.data.entity.Value;

import javax.persistence.*;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysema.query.annotations.Config;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@XmlRootElement(name="stockKeepingUnit")
@JsonRootName(value="stockKeepingUnit")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "StockKeepingUnit")
@Table(name = "PUBLIC.STOCK_KEEPING_UNIT")  
@Config(entityAccessors=true)
public class StockKeepingUnit implements Comparable<StockKeepingUnit>, Serializable {

    private static final long serialVersionUID = 6854178561532570632L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "SKU_ID", nullable = false)
    private Integer skuId;


    /**
    * name field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;
    
    /**
    * price field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "PRICE",  nullable = false, columnDefinition = "DECIMAL(10, 2)")
    private BigDecimal price;
    
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
    * inventories field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.Inventory.class, mappedBy = "stockKeepingUnit", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Inventory> inventories = new TreeSet<Inventory>();    

    /**
    * values field
    */
    @ManyToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.Value.class, mappedBy = "stockKeepingUnits", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Collection<Value> values = new TreeSet<Value>();

    /**
    * Default constructor
    */
    public StockKeepingUnit() {
    }
    
    /**
    * Simple Property constructor
    */
    public StockKeepingUnit(Integer skuId, String name, BigDecimal price) {
        this.skuId = skuId;
        this.name = name;
        this.price = price;
    }

    /**
    * Full Property constructor
    */
    public StockKeepingUnit(Integer skuId, String name, BigDecimal price, Product product, Collection<Inventory> inventories, Collection<Value> values) {
        this.skuId = skuId;
        this.name = name;
        this.price = price;
        this.product = product;
        this.inventories = inventories;
        this.values = values;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getSkuId() {
        return this.skuId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
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
    * Accessor for price field
    * returns the value of the price field
    */
    public BigDecimal getPrice() {
        return this.price;
    }
          
    /**
    * Mutator for the price field
    * @param  sets the value of the price field
    */    
    public void setPrice(BigDecimal price) {
      this.price = price;
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
    * Accessor for inventories field
    * @return the value of the inventories field. 
    */
    public Collection<Inventory> getInventories() {
        return this.inventories;
    }
      
    /**
    * Mutator for inventories field
    * @param inventories the new value for the inventories field
    */        
    public void setInventories(Collection<Inventory> inventories) {
        this.inventories = inventories;
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

        // Check candidate is an instance of StockKeepingUnit
        if ( !(that instanceof StockKeepingUnit) ) return false;

        // Safely cast to StockKeepingUnit
        StockKeepingUnit thatObj = (StockKeepingUnit)that;

        // Equality is based on all field values
        return
            this.getName() == null ? thatObj.getName() == null : this.getName().equals(thatObj.getName())&&
            this.getPrice() == null ? thatObj.getPrice() == null : this.getPrice().equals(thatObj.getPrice())&&
            this.getProduct() == null ? thatObj.getProduct() == null : this.getProduct().equals(thatObj.getProduct())&&        
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getName() ? 0 : getName().hashCode());
        hash = 31 * hash + (null == getPrice() ? 0 : getPrice().hashCode());
        hash = 31 * hash + (null == getProduct() ? 0 : getProduct().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "StockKeepingUnit:";
        str +=  ("Identity = " + (null == skuId ? "null" : skuId.toString())) + ", ";
        str +=  ("name = " + (null == getName() ? "null" : getName().toString())) + ", ";
        str +=  ("price = " + (null == getPrice() ? "null" : getPrice().toString())) + ", ";
        str +=  ("product = " + (null == getProduct() ? "null" : getProduct().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(StockKeepingUnit thatObj) {
    
        int cmp;

        cmp = this.getName() == null ?
                (thatObj.getName() == null ? 0 : -1) :
                (thatObj.getName() == null ? 1 : this.getName().compareTo(thatObj.getName())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getPrice() == null ?
                (thatObj.getPrice() == null ? 0 : -1) :
                (thatObj.getPrice() == null ? 1 : this.getPrice().compareTo(thatObj.getPrice())
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