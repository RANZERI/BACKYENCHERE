/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import connectivity.Connexion;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author Mendrika
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Categorie implements Serializable{
    private int id;
    private String nom;
    private String image;

    public Categorie() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom() {
        return nom;
    }
    public static List<Categorie> getListCategorie()throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        List<Categorie> lisat =new ArrayList<>();
        try {
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*from Categorie";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Categorie c = new Categorie(rs.getInt(1),rs.getString(2));
                File p=new File("MEDIA/Categorie/"+c.getId()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    c.setImage((Base64.getEncoder().encodeToString(pae)));
                
                }
                
                lisat.add(c);
            }
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return lisat;
    }
}
