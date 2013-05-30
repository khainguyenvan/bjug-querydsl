package net.eusashead.bjugquerydsl.data.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;
import java.lang.Integer;
import java.util.Date;
import net.eusashead.bjugquerydsl.data.entity.Basket;

import javax.persistence.*;
import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mysema.query.annotations.Config;

@Generated("net.sourceforge.greenvine.generator.impl.java.entity.EntityGenerator")
@XmlRootElement(name="customer")
@JsonRootName(value="customer")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Entity(name = "Customer")
@Table(name = "PUBLIC.CUSTOMER", uniqueConstraints = { @UniqueConstraint(columnNames = { "EMAIL" }) })  
@Config(entityAccessors=true)
public class Customer implements Comparable<Customer>, Serializable {

    private static final long serialVersionUID = -3193833271302767018L;

    /**
    * Identity field
    */
    @NotNull
    @Id
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Integer customerId;


    /**
    * email field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "EMAIL",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;
    
    /**
    * firstName field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "FIRST_NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String firstName;
    
    /**
    * joined field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "JOINED",  nullable = false, columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joined;
    
    /**
    * lastName field
    */
    @NotNull
    @Basic(fetch = FetchType.EAGER, optional = false)
    @Column(name = "LAST_NAME",  nullable = false, columnDefinition = "VARCHAR(255)")
    private String lastName;
    
    /**
    * baskets field
    */
    @OneToMany(targetEntity = net.eusashead.bjugquerydsl.data.entity.Basket.class, mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Collection<Basket> baskets = new TreeSet<Basket>();    

    /**
    * Default constructor
    */
    public Customer() {
    }
    
    /**
    * Simple Property constructor
    */
    public Customer(Integer customerId, String email, String firstName, Date joined, String lastName) {
        this.customerId = customerId;
        this.email = email;
        this.firstName = firstName;
        this.joined = joined;
        this.lastName = lastName;
    }

    /**
    * Full Property constructor
    */
    public Customer(Integer customerId, String email, String firstName, Date joined, String lastName, Collection<Basket> baskets) {
        this.customerId = customerId;
        this.email = email;
        this.firstName = firstName;
        this.joined = joined;
        this.lastName = lastName;
        this.baskets = baskets;
    }

    /**
    * Accessor for the identity field
    * @returns the value of the identity field
    */
    public Integer getCustomerId() {
        return this.customerId;
    }
    
    /**
    * Mutator for the identity field
    * @param sets the value of the identity field
    */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
    * Accessor for email field
    * returns the value of the email field
    */
    public String getEmail() {
        return this.email;
    }
          
    /**
    * Mutator for the email field
    * @param  sets the value of the email field
    */    
    public void setEmail(String email) {
      this.email = email;
    }
          
    /**
    * Accessor for firstName field
    * returns the value of the firstName field
    */
    public String getFirstName() {
        return this.firstName;
    }
          
    /**
    * Mutator for the firstName field
    * @param  sets the value of the firstName field
    */    
    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }
          
    /**
    * Accessor for joined field
    * returns the value of the joined field
    */
    public Date getJoined() {
        return this.joined;
    }
          
    /**
    * Mutator for the joined field
    * @param  sets the value of the joined field
    */    
    public void setJoined(Date joined) {
      this.joined = joined;
    }
          
    /**
    * Accessor for lastName field
    * returns the value of the lastName field
    */
    public String getLastName() {
        return this.lastName;
    }
          
    /**
    * Mutator for the lastName field
    * @param  sets the value of the lastName field
    */    
    public void setLastName(String lastName) {
      this.lastName = lastName;
    }
          
    /**
    * Accessor for baskets field
    * @return the value of the baskets field. 
    */
    public Collection<Basket> getBaskets() {
        return this.baskets;
    }
      
    /**
    * Mutator for baskets field
    * @param baskets the new value for the baskets field
    */        
    public void setBaskets(Collection<Basket> baskets) {
        this.baskets = baskets;
    }
          
    @Override
    public boolean equals(Object that) {
        
        // Check references for equality
        if ( this == that ) return true;
        
        // Check for null
        if (that == null) return false;

        // Check candidate is an instance of Customer
        if ( !(that instanceof Customer) ) return false;

        // Safely cast to Customer
        Customer thatObj = (Customer)that;

        // Equality is based on all field values
        return
            this.getEmail() == null ? thatObj.getEmail() == null : this.getEmail().equals(thatObj.getEmail())&&
            this.getFirstName() == null ? thatObj.getFirstName() == null : this.getFirstName().equals(thatObj.getFirstName())&&
            this.getJoined() == null ? thatObj.getJoined() == null : this.getJoined().equals(thatObj.getJoined())&&
            this.getLastName() == null ? thatObj.getLastName() == null : this.getLastName().equals(thatObj.getLastName())&&
            true;
    }

    @Override
    public int hashCode() {
        
        int hash = 7;
        
        // Hash is based on all fields
        hash = 31 * hash + (null == getEmail() ? 0 : getEmail().hashCode());
        hash = 31 * hash + (null == getFirstName() ? 0 : getFirstName().hashCode());
        hash = 31 * hash + (null == getJoined() ? 0 : getJoined().hashCode());
        hash = 31 * hash + (null == getLastName() ? 0 : getLastName().hashCode());

        return hash;
    }
        
    @Override        
    public String toString() {
        String str = "Customer:";
        str +=  ("Identity = " + (null == customerId ? "null" : customerId.toString())) + ", ";
        str +=  ("email = " + (null == getEmail() ? "null" : getEmail().toString())) + ", ";
        str +=  ("firstName = " + (null == getFirstName() ? "null" : getFirstName().toString())) + ", ";
        str +=  ("joined = " + (null == getJoined() ? "null" : getJoined().toString())) + ", ";
        str +=  ("lastName = " + (null == getLastName() ? "null" : getLastName().toString())) + ", ";
        return str.substring(0, str.lastIndexOf(", "));
    }
    
    @Override
    public int compareTo(Customer thatObj) {
    
        int cmp;

        cmp = this.getEmail() == null ?
                (thatObj.getEmail() == null ? 0 : -1) :
                (thatObj.getEmail() == null ? 1 : this.getEmail().compareTo(thatObj.getEmail())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getFirstName() == null ?
                (thatObj.getFirstName() == null ? 0 : -1) :
                (thatObj.getFirstName() == null ? 1 : this.getFirstName().compareTo(thatObj.getFirstName())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getJoined() == null ?
                (thatObj.getJoined() == null ? 0 : -1) :
                (thatObj.getJoined() == null ? 1 : this.getJoined().compareTo(thatObj.getJoined())
                );
        if (cmp !=  0)
            return cmp;
        cmp = this.getLastName() == null ?
                (thatObj.getLastName() == null ? 0 : -1) :
                (thatObj.getLastName() == null ? 1 : this.getLastName().compareTo(thatObj.getLastName())
                );
        if (cmp !=  0)
            return cmp;

        return cmp;    
    }
        
}