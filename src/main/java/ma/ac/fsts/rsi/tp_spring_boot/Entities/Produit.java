package ma.ac.fsts.rsi.tp_spring_boot.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class Produit {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private String description ;
    private int quantity ;
    private double prix ;

    public Produit() {
    }

    public Produit(String description, int quantity, double prix) {
        this.description = description;
        this.quantity = quantity;
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
