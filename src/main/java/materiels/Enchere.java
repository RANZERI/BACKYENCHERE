/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import connectivity.Connexion;
import gestion.Convertisseur;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import model.Client;

/**
 *
 * @author Mendrika
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Enchere {
    private int id;
    
    private LocalDateTime date;
    private double prix_depart;
    private LocalDateTime date_fin;
    private double prix_vente;
    private String name;
    private String description;
    private String catergorinom;
    private String etatnom;
    private String image;
    private String currentDate;
    private int clientid;
    private String statut;
    public Enchere(int id, LocalDateTime date, double prix_depart, LocalDateTime date_fin, double prix_vente, int idcategorie,int idproduit,String name, String description, String catergorinom, String etatnom, int idclient) {
        this.id = id;
        this.date = date;
        this.prix_depart = prix_depart;
        this.date_fin = date_fin;
        this.prix_vente = prix_vente;
        this.name = name;
        this.description = description;
        this.catergorinom = catergorinom;
        this.etatnom = etatnom;
        this.image = idproduit+"";
    }
    public Enchere() {
    }
    public void setId(int id) {
        this.id = id;
    }    
    public void setImage(String image) {
        this.image = image;
    }

        public Enchere findByIdEnchere(int id) throws Exception{
        Enchere compte=null;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        try {
            compte = new Enchere();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select enchere.id idenchere, date, date_fin,name,description,type,enchere.produitid from enchere join produit on produit.id = enchere.produitid join categorie on categorie.id = produit.categorieid where enchere.id="+id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                compte.setId(rs.getInt("idenchere"));
                compte.setDate(rs.getTimestamp("date").toLocalDateTime());
                compte.setDate_fin(rs.getTimestamp("date_fin").toLocalDateTime());
                compte.setName(rs.getString("name"));
                compte.setDescription(rs.getString("description"));
                compte.setCatergorinom(rs.getString("type"));
                for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+rs.getInt("produitid")+".png");
                if(p.exists()){            
                        byte[] pae = Files.readAllBytes(p.toPath());                   
                        compte.setImage((Base64.getEncoder().encodeToString(pae)));
                        break;
                    }
                }
            }
        } 
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return compte;
    }
    public static Enchere findById(int id) throws Exception{
        Enchere compte=null;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        try {
            compte = new Enchere();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*from Enchere where id="+id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                compte.setPrix_depart(rs.getDouble(3));
                compte.setDate_fin(rs.getTimestamp(4).toLocalDateTime());
                compte.setPrix_vente(rs.getDouble(5));
            }
        } 
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return compte;
    }
    public static List<Enchere> findByIdClient(int id) throws Exception{
        Enchere compte=null;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = new Connexion();
        Connection connect = connexion.getConnect();
        ResultSet rs = null;
        List<Enchere> list=new ArrayList<>();
        try {
            compte = new Enchere();
            stmt = connect.createStatement();
            sql = "SELECT*from v_etat_enchere where auteur="+id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                compte.setId(rs.getInt(id));
                compte.setName(rs.getString("name"));
                compte.setCatergorinom(rs.getString("type"));
                String cc=Convertisseur.get_date_between(rs.getTimestamp("date_fin").toLocalDateTime());
                if(cc.compareTo("0j - 0h - 0m - 0s")==0){
                    compte.setStatut("Deja cloturer");
                }
                else{
                    compte.setStatut("En cours");
                }
                if(rs.getDouble("max")!=0){
                    compte.setPrix_depart(rs.getDouble("max"));
                }
                else{
                    compte.setPrix_depart(rs.getDouble("prix_depart"));
                }
                compte.setDescription(rs.getString("description"));
                compte.setEtatnom(rs.getString("nom"));
                list.add(compte);
            }
        } 
        finally{
            connect.close();
        }
        return list;
    }
    public int getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setPrix_depart(double prix_depart) {
        this.prix_depart = prix_depart;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public void setPrix_vente(double prix_vente) {
        this.prix_vente = prix_vente;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCatergorinom(String catergorinom) {
        this.catergorinom = catergorinom;
    }

    public void setEtatnom(String etatnom) {
        this.etatnom = etatnom;
    }

    public double getPrix_depart() {
        return prix_depart;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public double getPrix_vente() {
        return prix_vente;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCatergorinom() {
        return catergorinom;
    }

    public String getEtatnom() {
        return etatnom;
    }

    public String getImage() {
        return image;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }


    
    
    
}
