/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import connectivity.Connexion;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import materiels.Enchere;

/**
 *
 * @author Mendrika
 */
public class Client {
    private int id;
    private String email;
    private String password;
    private String pseudo;
    private String nom;
    private String prenom;
    private String token;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") 
    private LocalDate Date_de_naissance;
    private String sexe;
    public int getId() {
        return id;
    }

    public Client(int id, String email, String password, String pseudo, String nom, String prenom, LocalDate Date_de_naissance, String sexe) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.Date_de_naissance = Date_de_naissance;
        this.sexe = sexe;
    }

    public Client() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void inscrire()throws Exception{
        Statement stmt=null;
        Connection con=new Connexion().getConnect();
        try{
            String sql="Insert into Client(email,password,pseudo,nom,prenom,date_de_naissance,sexe) values('"+this.getEmail()+"','"+this.getPassword()+"','"+this.getPseudo()+"','"+this.getNom()+"','"+this.getPrenom()+"','"+this.getDate_de_naissance()+"','"+this.getSexe()+"')";
            stmt=con.createStatement();
            stmt.executeUpdate(sql);
        }
        finally{
            con.close();
        }
    }
    public static Client FindByToken(String id) throws Exception{
        Statement stmt=null;
        Timestamp time=null;
        String ssql="select * from client where token like '"+id+"'";
        System.out.println(ssql);
        Connection con=null;
        Client x=null;
        try{
            con=new Connexion().getConnect();
            stmt=con.createStatement();
            ResultSet fin=stmt.executeQuery(ssql);
            while(fin.next()){
                x=new Client();
                x.setId(fin.getInt(1));
                x.setToken(fin.getString("token"));
            }
        }
        finally{
            con.close();
        }
        return x;
    }
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDate_de_naissance() {
        return Date_de_naissance;
    }

    public void setDate_de_naissance(LocalDate Date_de_naissance) {
        this.Date_de_naissance = Date_de_naissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
