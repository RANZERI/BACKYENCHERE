/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import connectivity.Connexion;
import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import materiel.Categorie;
import materiel.Produit;

/**
 *
 * @author randretsa
 */
public class Recherche {
   
       
    String[] name;
        String[] type;
        String[] etat;
        String description;
        double min;
        double max;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
        
    public String formatSql(String[] name,String [] type,String[] etat,String description){
        
        String sql = "SELECT * FROM v_etat_enchere where 1=1 "; 
        
        if(!"".equals(description)){
            sql = sql + "and description like '%"+description+"%'";
        }
        if(name!=null){
           if(name.length>0){
                       sql = sql + " and (";
            int i =0;
            for(String s : name){

                if(i==0) sql = sql + " name like '%"+s+"%'";
                else sql = sql +" OR name like '%"+s+"%'";
                i++;
            }
            sql = sql +")";}

        }
        
        
        if(type!=null){
           if(type.length>0){
            
                int i=0;
                sql = sql + " and (";
                for(String s : type){
                    if(i==0) sql = sql +" type like '%"+s+"%'";
                    else sql = sql +" OR type like '%"+s+"%'";
                    i++;
                }
                sql = sql +")";

           }  
        }
        if(etat!=null){
            if(etat.length>0){
            sql = sql + " and (";
            int i=0;
            for(String s : etat){
                if(i==0) sql = sql +" nom like '%"+s+"%'";
                else sql = sql +" OR nom like '%"+s+"%'";
                i++;
            }
            sql = sql +")";
            }
        }

        return sql;
    }
    
    public List<Produit> rechercher(String[] name,String [] type,String[] etat,String description,double min,double max) throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Produit> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = formatSql(name, type, etat, description);
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Produit produit = new Produit();
                produit.setId(rs.getInt("id"));
                produit.setName(rs.getString("name"));
                produit.setDescription(rs.getString("description"));
                produit.setCategorieid(rs.getString("type"));
                produit.setEtatid(rs.getString("nom"));
                produit.setMax(rs.getDouble("max"));
                if(produit.getMax()==0){ produit.setPrix_depart(rs.getDouble("prix_depart"));
                    
                }else produit.setPrix_depart(produit.getMax());
                 System.out.println(produit.getPrix_depart());
                 
                for(int i=0;i<6;i++){
                File p=new File("MEDIA/"+i+"/"+rs.getInt("id")+".png");
                if(p.exists()){            
                        byte[] pae = Files.readAllBytes(p.toPath());                   
                        produit.setImage((Base64.getEncoder().encodeToString(pae)));
                        break;
                    }
                }
                
                if(min==0 && max==0) list.add(produit);
                else if(max==0 && min > 0){
                    
                    if(produit.getPrix_depart()>=min) list.add(produit); 
               
                }else if(min==0 && max >0){
                    if(produit.getPrix_depart()<=max) list.add(produit); 
               
                }   
                else{
                    if(produit.getPrix_depart()<=max && produit.getPrix_depart()>=min) list.add(produit); 
                }
                
            }
            
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
        return list;
    
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getEtat() {
        return etat;
    }

    public void setEtat(String[] etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
