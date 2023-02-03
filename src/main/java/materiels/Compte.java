/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import connectivity.Connexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Client;

/**
 *
 * @author Mendrika
 */
public class Compte {
    private int id;
    private String date;
    private Client client;
    private double credit;
    
    public double solde(int idclient) throws Exception{
        double compte=0;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        try {
//            compte = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from v_solde where clientid="+idclient;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                compte+=rs.getDouble("solde");
            }
        } 
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return compte;
    }
            
    public void save()throws Exception{
        Statement stmt=null;
        Connection con=new Connexion().getConnect();
        try{
            String sql="Insert into Compte(credit,date,Clientid,estValider) values("+this.getCredit()+",'"+LocalDateTime.now()+"',"+this.getClient().getId()+",false)";
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
    public static void update(String value,String id)throws Exception{
        Statement stmt=null;
        Connection con=new Connexion().getConnect();
        try{
            String sql="update Compte set estValider="+value+" where id="+id;
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
    public double get_somme_compte(int idClient) throws Exception{
        double compte=0;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        try {
//            compte = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*from v_compte where estValider=true and id="+idClient;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                compte+=rs.getDouble(5);
            }
        } 
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return compte;
    }
    public static List<Compte> get_transaction_non_valider()throws Exception{
        List<Compte> compte=null;
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        try {
            compte = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*from v_compte where estValider=false";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                Client x=new Client();
                x.setId(rs.getInt(1));
                x.setEmail(rs.getString(2));
                x.setNom(rs.getString(3));
                x.setPrenom(rs.getString(4));
                Compte k = new Compte();
                k.setClient(x);
                k.setCredit(rs.getDouble(5));
                k.setDate(rs.getString(6));
                compte.add(k);
            }
        } 
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return compte;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
    
}
