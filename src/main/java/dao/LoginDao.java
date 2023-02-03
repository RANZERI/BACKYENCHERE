/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import connectivity.Connexion;
import gestion.Convertisseur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import model.Client;

/**
 *
 * @author Mendrika
 */
public class LoginDao {
    
    public static String get_rand_token(){
        String salta="abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder lop=new StringBuilder();
        Random rnd=new Random();
        while(lop.length()<45){
            int index=(int) (rnd.nextInt(34));
            lop.append(salta.charAt(index));
        }
        String saltStr=lop.toString();
        return saltStr;
    }
    public static Client findById(String token)throws Exception{
        Statement stmt=null;
        Timestamp time=null;
        String ssql="select * from Client where token like '"+token+"'";
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        System.out.println(ssql);
        Client n=null;
        ResultSet fin=stmt.executeQuery(ssql);
        while(fin.next()){
            n=new Client(fin.getInt("id"),fin.getString("email"),fin.getString("password"),fin.getString("pseudo"),fin.getString("nom"),fin.getString("prenom"),Convertisseur.get_dates(fin.getString("date_de_naissance")),fin.getString("sexe"));
        }
        return n;
    }
    public static void update(String token,String random,LocalDateTime local)throws Exception{
        Statement stmt=null;
        Connection con=new Connexion().getConnect();
        try{
            String sql="update Client set token ="+random+", date_expiration="+local+"  where token like '"+token+"'";
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
    public static Client update_first(String email,String pass)throws Exception{
        PreparedStatement stmt=null;
        Client ve=null;
        Connection con=new Connexion().getConnect();
        try{
            String sql="update Client set token ='"+LoginDao.get_rand_token()+"' where password like '"+pass+"' and email like '"+email+"'";
            stmt=con.prepareStatement(sql);
            System.out.println(sql);
            stmt.execute();
            String sql1="select * from Client where password like '"+pass+"' and email like '"+email+"'";
            stmt=con.prepareStatement(sql1);
            ResultSet fin=stmt.executeQuery();
            System.out.println(sql1);
            while(fin.next()){
                ve=new Client(fin.getInt("id"),fin.getString("email"),fin.getString("password"),fin.getString("pseudo"),fin.getString("nom"),fin.getString("prenom"),Convertisseur.get_dates(fin.getString("date_de_naissance")),fin.getString("sexe"));
                ve.setToken(fin.getString(7));
            }

        }
        finally{
            if(con!=null){
                con.close();
            }
            if(stmt!=null){
                stmt.close();
            }
        }
        return ve;
    }
}
