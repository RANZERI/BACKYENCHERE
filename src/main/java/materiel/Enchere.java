/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiel;

import connectivity.Connexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDateTime;
import model.Client;

/**
 *
 * @author randretsa
 */
public class Enchere {
    int id;
    Date date;
    double prix_depart;
    LocalDateTime date_fin;
    double prix_vente;
    Produit produit;
    String currentDate;
    int idclient;


        public void save() throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "insert into Enchere(date,prix_depart,date_fin,prix_vente,produitid) values("+"current_date"+","+this.prix_depart+",'"+this.date_fin.toString().replace("T", " ")+":00',"+this.prix_vente+","+this.produit.getId()+")";
            System.out.println(sql+"    ejnfe");
            stmt.execute(sql);
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         
    }
        public static  void cloturer(int id,int clientid,double montant)throws Exception{
        Statement stmt=null;
        Connection con=new Connexion().getConnect();
        try{
            String sql="Insert into EnchereCloturer(Enchereid,Clientid,montant) values("+id+","+clientid+","+montant+")";
            stmt=con.createStatement();
            System.out.println(sql);
            stmt.executeUpdate(sql);
        }
        finally{
            if(con!=null){
                con.close();
            }
            if(stmt!=null){
                stmt.close();
            }
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIdclient() {
        return idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public double getPrix_depart() {
        return prix_depart;
    }


    public void setPrix_depart(double prix_depart) {
        this.prix_depart = prix_depart;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
    
    
    
}
