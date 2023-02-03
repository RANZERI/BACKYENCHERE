/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.restservice;

import static com.example.restservice.CategorieController.convertToBufferedImage;
import dao.ProduitDao;
import gestion.Convertisseur;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.HashMap;
import javax.imageio.ImageIO;
import materiel.Enchere;
import materiel.Produit;
import model.Compte;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import utilities.Recherche;
import utilities.Utilities;

@CrossOrigin
@RestController
public class RequestController  {
    
    
	@GetMapping("/caracteristiques")
	public HashMap getInsertProduitForm(){
            HashMap<String,Object> map = new HashMap();         
            try{
            Utilities utilities= new Utilities();            
            map.put("etat",utilities.getAllEtat());
            map.put("categorie", utilities.getAllCategorie());           
           }
            catch(Exception aaa){
                aaa.printStackTrace();
            }
            return map;
	
        }

        
        
        
       @PostMapping("/caracteristiques") 
       public String insertProduit(@RequestBody Produit produit){
            try{
               produit.save();                       
               return "insertion reussi";
            }
            catch(Exception aaa){
                    aaa.printStackTrace();
                    return aaa.getMessage();
            }
       }
       
       	@GetMapping("/toenchere/{id}")
	public Produit getProduitById(@PathVariable String id){
            try{         
                Utilities utilities= new Utilities();
                return utilities.getProduit_byId(id);
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return null;
            }
	
        }
        
        @PostMapping("/toenchere") 
       public String createEnchere(@RequestBody Enchere enchere){
            try{
                enchere.save();             
                return "mise en enchere réussi";            
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return aaa.getMessage();
            }
	}
       
        @GetMapping("/encheres/{id}")
	public HashMap getListEnchere(@PathVariable int id){
            try{
                HashMap<String,Object> map = new HashMap();

                Utilities utilities= new Utilities();

                map.put("data",utilities.getNonSoldItem(id));

               return map;
            }
            catch(Exception aaa){
                    aaa.printStackTrace();
                    return null;
            }	
        }

        
        @PostMapping("/comptes") 
       public String chargeCompte(@RequestBody Compte compte) throws Exception{
            try{
                compte.chargerCompte();

                return "compte recharger";
            }
            catch(Exception aaa){
                    aaa.printStackTrace();
                    return aaa.getMessage();
            }
	}
       
        @GetMapping("/statistiques")
	public HashMap statistiques(){
                HashMap<String,Object> map = new HashMap();
            try{

                Utilities utilities= new Utilities();

                map.put("data",utilities.getStatistique());

            }
            catch(Exception aaa){
                    aaa.printStackTrace();
            }
            return map;
	
        }
        
       @PostMapping("/recherche") 
       public HashMap recherche(@RequestBody Recherche recherche){
            HashMap<String,Object> map = new HashMap();
            try{

                Recherche r = new Recherche();
                map.put("data",r.rechercher(recherche.getName(), recherche.getType(), recherche.getEtat(), recherche.getDescription(),recherche.getMin(),recherche.getMax()));
                    
            }
            catch(Exception aaa){
                    aaa.printStackTrace();
            }
            return map;
	}
        
    
}
