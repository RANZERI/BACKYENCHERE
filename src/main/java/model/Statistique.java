/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author randretsa
 */
public class Statistique {
    String categorie;
    double montant;
    double total;
    double pourcentage;
 
            
    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void getPourcentageValue() {
        if(total!=0) pourcentage = (montant/total)*100;
    }
public double getPourcentage() {
        
        return pourcentage;
    }
    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }
    
    
}
