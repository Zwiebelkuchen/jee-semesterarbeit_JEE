package de.java2enterprise.onlineshop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "ONLINESHOP", name = "ITEM")
@NamedQueries(
		value= {
				@NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
				@NamedQuery(name = "Item.findItem", query = "SELECT i FROM Item i where i.id = :id")})
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ITEM_ID_GENERATOR", sequenceName = "SEQ_ITEM", schema = "ONLINESHOP", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_ID_GENERATOR")
    private Long id;

    private String description;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    private byte[] foto;

    private Double price;

    private String title;
    //Änderung: Musste von LocalDateTime auf Date geändert werden 
    private Date sold;

    // bi-directional many-to-one association to Customer
    @ManyToOne
    private Customer seller;

    // bi-directional many-to-one association to Customer
    @ManyToOne
    private Customer buyer;

    // unidirektionale Verknüpfung zu Observed Tabelle
    @ManyToMany 
    @JoinTable(
    		schema="ONLINESHOP",
    		name="OBSERVED",
    		joinColumns=@JoinColumn(name="ITEM_ID", referencedColumnName="ID"),
    		inverseJoinColumns=@JoinColumn(name="CUSTOMER_ID", referencedColumnName="ID")
    		) 
    private Set<Customer> observed ;
    
    
    public Item() {
    }

 // Getter und Setter Methode fuer die Observed Tabelle
    public Set<Customer> getObserved() {return observed; }
    public void setObserved(Set<Customer> observed) {this.observed = observed;}
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFoto() {
        return this.foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    //Änderung: Musste von LocalDateTime auf Date geändert werden 

    public Date getSold() {
        return this.sold;
    }
    //Änderung: Musste von LocalDateTime auf Date geändert werden 

    public void setSold(Date sold) {
        this.sold = sold;
    }

    public Customer getSeller() {
        return this.seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getBuyer() {
        return this.buyer;
    }

    public void setBuyer(Customer buyer) {
        this.buyer = buyer;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Item)) {
            return false;
        }
        Item other = (Item) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return id + "-" + title + "-" + seller;
    }
}
