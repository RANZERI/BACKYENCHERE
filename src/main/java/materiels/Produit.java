/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 *
 * @author Mendrika
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Produit {
    private int id;
    private String nom;
    private String description;
    
    private Categorie categorie;
    
    private Etat etat;
    private String image;
    private String auteur;

    public Produit(int id, String nom, String description, Categorie categorie, Etat etat) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.categorie = categorie;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    
}
