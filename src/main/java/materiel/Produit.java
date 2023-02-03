/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiel;

import static com.example.restservice.CategorieController.convertToBufferedImage;
import connectivity.Connexion;
import gestion.Convertisseur;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;
import javax.imageio.ImageIO;
import model.Client;

/**
 *
 * @author randretsa
 */
public class Produit {
    int id;
    String name;
    String description;
    String categorieid;
    String etatid;
    String image;
    String idclient;
    double prix_depart;
    double max;
    
    public void saveUploadedFile(Connection con) throws Exception {
        if (image!=null) {
            image=image.split(",")[1];
//            image=new String(image.getBytes(),"UTF-8");     
            System.out.println(image+"  xxxx");
            byte[] bytes = Base64.getMimeDecoder().decode(image);
            int curr=Convertisseur.get_current_sequence("produit",con);
            File myObj = new File("MEDIA/"+categorieid+"/"+curr+".png");
            myObj.createNewFile();            
            try(OutputStream stream=new FileOutputStream("MEDIA/"+categorieid+"/"+curr+".png")){
                stream.write(bytes);
            }
            InputStream input = new FileInputStream(myObj);
            BufferedImage originalImage = ImageIO.read(input);
            int xx=(int)(originalImage.getWidth());            
            int yy= (int)(originalImage.getHeight());
            double prop=xx/yy;
            prop*=900;
            Image newResizedImage = originalImage.getScaledInstance((int)prop,900, Image.SCALE_SMOOTH);
            String s = myObj.getAbsolutePath();
            String fileExtension = s.substring(s.lastIndexOf(".") + 1);
            ImageIO.write(convertToBufferedImage(newResizedImage),fileExtension, myObj);
            input.close();
        }
    }   
    public void save() throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        Connection con = null;
        try {
            connexion = new Connexion();
            con = connexion.getConnect();
            stmt=con.createStatement();
            Client xx=Client.FindByToken(idclient);
            sql = "insert into Produit(name,description,Categorieid,Etatid,auteur) values('"+this.name+"','"+this.description+"',"+this.categorieid+","+this.etatid+","+xx.getId()+")";
            stmt.execute(sql);
            this.saveUploadedFile(con);
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            con.close();
        }
         
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorieid() {
        return categorieid;
    }

    public void setCategorieid(String categorieid) {
        this.categorieid = categorieid;
    }

    public String getEtatid() {
        return etatid;
    }

    public void setEtatid(String etatid) {
        this.etatid = etatid;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", name=" + name + ", description=" + description + ", categorieid=" + categorieid + ", etatid=" + etatid + '}';
    }

    public String getIdclient() {
        return idclient;
    }

    public void setIdclient(String idclient) {
        this.idclient = idclient;
    }

    public double getPrix_depart() {
        return prix_depart;
    }

    public void setPrix_depart(double prix_depart) {
        this.prix_depart = prix_depart;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
    
    
    
}
