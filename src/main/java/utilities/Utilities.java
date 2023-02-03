/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import connectivity.Connexion;
import gestion.Convertisseur;
import java.io.File;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import materiel.Categorie;
import materiel.Enchere;
import materiel.Etat;
import materiel.Produit;
import model.Client;
import model.Statistique;

/**
 *
 * @author randretsa
 */
public class Utilities {
        public List<Etat> getAllEtat() throws Exception{
             Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Etat> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from etat";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Etat c = new Etat();
                c.setId(rs.getInt("id"));
                c.setNom(rs.getString("nom"));
                list.add(c);
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
            
        public List<Categorie> getAllCategorie() throws Exception{
             Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Categorie> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from categorie";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                
                Categorie c = new Categorie();
                c.setId(rs.getInt("id"));
                c.setType(rs.getString("type"));
                list.add(c);
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
        
        public Produit getProduit_byId(String id) throws Exception{
            Statement stmt = null;
            String sql = null;
            Connexion connexion = null;
            ResultSet rs = null;
            Produit p = null;
            try {
                p = new Produit();
                connexion = new Connexion();
                stmt = connexion.getConnect().createStatement();
                sql = "select*from produit where id="+id;
                rs = stmt.executeQuery(sql);

                while(rs.next()){
                        p.setId(rs.getInt("id"));
                        p.setName(rs.getString("name"));
                        p.setDescription(rs.getString("description"));
                        p.setEtatid(rs.getInt("etatid")+"");
                        p.setCategorieid(rs.getInt("categorieid")+"");
                        for(int i=0;i<6;i++){
                            File pa=new File("MEDIA/"+i+"/"+p.getId()+".png");
                            if(pa.exists()){            
                                byte[] pae = Files.readAllBytes(pa.toPath());                   
                                p.setImage((Base64.getEncoder().encodeToString(pae)));
                                break;
                            }
                        }
                }
            } catch (Exception e) {
                //TODO: handle exception
                throw e;
            }
            finally{
                rs.close();
                stmt.close();
                connexion.getConnect().close();
            }
            return p;
            
        }
        
        public List<Enchere> getNonSoldItem(int id) throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Enchere> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select * from v_etat_enchere where id not in (select enchereid from encherecloturer) and idcategorie="+id;            
            rs = stmt.executeQuery(sql);
            System.out.println(sql);
            while(rs.next()){
                
                Enchere e = new Enchere();
                Produit p =new Produit();
                p.setId(rs.getInt("idproduit"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setCategorieid(rs.getString("type"));
                p.setEtatid(rs.getString("nom"));
                e.setIdclient(rs.getInt("idclient"));
                e.setDate(rs.getDate("date"));
                e.setDate_fin(rs.getTimestamp("date_fin").toLocalDateTime());
                e.setId(rs.getInt("id"));
                e.setPrix_depart(rs.getDouble("prix_depart"));
                e.setPrix_vente(rs.getDouble("max"));
                e.setProduit(p);
                String ret=Convertisseur.get_date_between(e.getDate_fin());
                if(ret.compareTo("0j - 0h - 0m - 0s")==0){
                    Enchere.cloturer(e.getId(), e.getIdclient(), e.getPrix_depart());                
                }
                e.setCurrentDate(ret);
                list.add(e);
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
       
        public List<Statistique> getStatistique() throws Exception{
                     Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList<Statistique> list = null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "select*from v_statistique";
            rs = stmt.executeQuery(sql);
        
            while(rs.next()){
                Statistique s = new Statistique();
                s.setCategorie(rs.getString("type"));
                s.setMontant(rs.getDouble("montant"));
                s.setTotal(rs.getDouble("total"));
                s.getPourcentageValue();
                list.add(s);
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
}
