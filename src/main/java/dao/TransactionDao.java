/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectivity.Connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mendrika
 */
public class TransactionDao {
    private String id;
    private String idenchere;
    private String idclient;
    private double montant;
    private String date_mise;
    private String estValider;

    public String getEstValider() {
        return estValider;
    }

    public void setEstValider(String estValider) {
        this.estValider = estValider;
    }
    
    
    public String getId() {
        return id;
    }

    public String getIdenchere() {
        return idenchere;
    }

    public String getIdclient() {
        return idclient;
    }

    public double getMontant() {
        return montant;
    }

    public String getDate_mise() {
        return date_mise;
    }
    
    public void cloturer(int id) throws Exception{
        PreparedStatement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList list =null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            sql = "SELECT*FROM Enchere ";
            stmt = connexion.getConnect().prepareStatement(sql);
//            while(rs.next()){
//                String sql1=;
//                PreparedStatement stmt1 = connexion.getConnect().prepareStatement(sql1);
//            }
        }
        catch(Exception a){
            a.printStackTrace();
        }               
    }
    public void save() throws Exception
    {
        PreparedStatement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList list =null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            sql = "Insert into Compte(credit,date,Clientid,estValider) values("+this.getMontant()+",'"+this.getDate_mise()+"',"+this.getIdclient()+","+this.getEstValider()+")";
            stmt = connexion.getConnect().prepareStatement(sql);
            stmt.execute();
//            while(rs.next()){
//                String sql1=;
//                PreparedStatement stmt1 = connexion.getConnect().prepareStatement(sql1);
//            }
        }
        catch(Exception a){
            a.printStackTrace();
        }         
        
    }
}
