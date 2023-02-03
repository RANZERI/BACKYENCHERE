/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connectivity.Connexion;
import gestion.Convertisseur;
import java.io.File;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import materiels.Categorie;
import materiels.Enchere;
import materiels.Etat;
import materiels.Produit;
import materiels.Transaction;
import model.Client;

/**
 *
 * @author Mendrika
 */
public class ProduitDao {
    public static List<Produit> getProduitBy(int id)throws  Exception{
        Statement stmt=null;
        Timestamp time=null;
        String ssql="select * from v_produit where id2="+id+" and id not in(select Produitid from v_enchere_fini )";
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        System.out.println(ssql+"");
        List<Produit> n=new ArrayList<Produit>();
        ResultSet fin=stmt.executeQuery(ssql);
        Produit x=null;
        while(fin.next()){
            Categorie cat=new Categorie();
            cat.setId(fin.getInt(4));
            cat.setImage(fin.getString(5));
            x=new Produit(fin.getInt(1),fin.getString(2),fin.getString(3),cat,new Etat(fin.getInt(6),fin.getString(7)));
            for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+x.getId()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    x.setImage((Base64.getEncoder().encodeToString(pae)));
                    break;
                }
            }
            n.add(x);
        }
        return n;
    }
    public static List<Produit> getProduitByClient(String id)throws  Exception{
        Statement stmt=null;
        Timestamp time=null;
        Client pp=Client.FindByToken(id);
        String ssql="select * from v_produit where auteur="+pp.getId()+" and id not in(select Produitid from enchere )";
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        System.out.println(ssql+"");
        List<Produit> n=new ArrayList<Produit>();
        ResultSet fin=stmt.executeQuery(ssql);
        Produit x=null;
        while(fin.next()){
            Categorie cat=new Categorie();
            cat.setId(fin.getInt(4));
            cat.setImage(fin.getString(5));
            x=new Produit(fin.getInt(1),fin.getString(2),fin.getString(3),cat,new Etat(fin.getInt(6),fin.getString(7)));
            for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+x.getId()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    x.setImage((Base64.getEncoder().encodeToString(pae)));
                    break;
                }
            }
            n.add(x);
        }
        return n;
    }
    public static List<Produit> getProduitByClientByCategorie(String categorie,String id)throws  Exception{
        Statement stmt=null;
        Timestamp time=null;
        Client pp=Client.FindByToken(id);
        String ssql="select * from v_produit where auteur="+pp.getId()+" and id2="+categorie+" and id not in(select Produitid from enchere )";
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        System.out.println(ssql+"");
        List<Produit> n=new ArrayList<Produit>();
        ResultSet fin=stmt.executeQuery(ssql);
        Produit x=null;
        while(fin.next()){
            Categorie cat=new Categorie();
            cat.setId(fin.getInt(4));
            cat.setImage(fin.getString(5));
            x=new Produit(fin.getInt(1),fin.getString(2),fin.getString(3),cat,new Etat(fin.getInt(6),fin.getString(7)));
            for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+x.getId()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    x.setImage((Base64.getEncoder().encodeToString(pae)));
                    break;
                }
            }
            n.add(x);
        }
        return n;
    }

    public static Enchere getEnchere(int id)throws  Exception{
        Statement stmt=null;
        Timestamp time=null;
        String ssql="select * from v_etat_enchere where id="+id;
        Connection con=new Connexion().getConnect();
        stmt=con.createStatement();
        ResultSet fin=stmt.executeQuery(ssql);
        Enchere x=null;
        while(fin.next()){
            x=new Enchere(fin.getInt(1), fin.getTimestamp(2).toLocalDateTime(), fin.getDouble(3), fin.getTimestamp(4).toLocalDateTime(), fin.getDouble(5), fin.getInt(6),fin.getInt(7),fin.getString(8), fin.getString(9),fin.getString(10), fin.getString(11), fin.getInt(12));
            String ret=Convertisseur.get_date_between(x.getDate_fin());
            if(ret.compareTo("0j - 0h - 0m - 0s")==0){
                materiel.Enchere.cloturer(x.getId(), x.getClientid(), x.getPrix_depart());                
            }
            x.setCurrentDate(ret);
        }
        System.out.println();
        if(x!=null){
            if(Transaction.get_last((x.getId()))!=null){
                double current=Transaction.get_last((x.getId())).getMontant();
                x.setPrix_depart(current);
            }
            for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+x.getImage()+".png");
                if(p.exists()){            
                    byte[] pae = Files.readAllBytes(p.toPath());                   
                    x.setImage((Base64.getEncoder().encodeToString(pae)));
                }
            }
        }
        System.out.println(ssql+"   "+x);
        return x;
    }
    
}
