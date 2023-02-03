package com.example.restservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import connectivity.Connexion;
import dao.LoginDao;
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
import java.time.LocalDate;
import materiels.Categorie;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;
import materiels.Compte;
import materiels.Enchere;
import materiels.Mongo;
import materiels.Produit;
import materiels.Transaction;
import model.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
public class CategorieController  {
	@GetMapping("/historique/{idclient}")
	public ResponseEntity<Object> getHistorique(@PathVariable String idclient) throws Exception{            
            Client cp=Client.FindByToken(idclient);
            return new ResponseEntity<>(new Mongo().Historique(cp.getId()), HttpStatus.OK);
	} 
	@GetMapping("/categors")
	public ResponseEntity<Object> getCategories() throws Exception{            
            return new ResponseEntity<>(Categorie.getListCategorie(), HttpStatus.OK);
	} 
	@GetMapping("/categorie/{id}")
	public ResponseEntity<Object> getProduit(@PathVariable int id) throws Exception{            
            return new ResponseEntity<>(ProduitDao.getProduitBy(id), HttpStatus.OK);
	} 
	@GetMapping("/categorie/{id}/{token}")
	public ResponseEntity<Object> getProduit(@PathVariable String id,@PathVariable String token) throws Exception{            
            return new ResponseEntity<>(ProduitDao.getProduitByClientByCategorie(id,token), HttpStatus.OK);
	} 
	@GetMapping("/produit/{id}")
	public ResponseEntity<Object> getProduitByClient(@PathVariable String id) throws Exception{            
            return new ResponseEntity<>(ProduitDao.getProduitByClient(id), HttpStatus.OK);
	} 

        @GetMapping("/compte/lister")
	public ResponseEntity<Object> getListe() throws Exception{            
            return new ResponseEntity<>(Compte.get_transaction_non_valider(), HttpStatus.OK);
	} 
        @PostMapping(value = "/compte/recharger")
	public ResponseEntity<Object> recharger(@RequestBody Compte id) { 
            try{
                Client cp=Client.FindByToken(id.getClient().getToken());
                id.setClient(cp);
                id.save();
                return new ResponseEntity<>("Inserer avec succes", HttpStatus.OK);
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
            }
	} 
	@PutMapping("/compte/valider/{id}")
	public ResponseEntity<Object> valider(@PathVariable String id) {            
            try{
                Compte.update("true", id);
                return new ResponseEntity<>("{\"message\": \"Inserer avec succes\"}", HttpStatus.OK);
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.BAD_REQUEST);
            }
	} 
	@PostMapping("/transaction/encherir")
	public ResponseEntity<Object> miser(@RequestBody Transaction transact) throws Exception{            
            try{
                Mongo mongo = new Mongo();
                if(transact.getCli().getToken()!=null){
                    Client x=Client.FindByToken(transact.getCli().getToken());
                    transact.setCli(x);
                    ObjectMapper map=new ObjectMapper();
                    System.out.println(map.writeValueAsString(transact.getCli()));
                    System.out.println(map.writeValueAsString(x));
                    mongo.saveTransaction(transact.getEnchere().getId(), transact, transact.getMontant());                
                    transact.save();
                }
            }
            catch(Exception aaa){
                aaa.printStackTrace();
                return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
            }
            return new ResponseEntity<>("Miser avec succes", HttpStatus.OK);
	} 
            @RequestMapping(value = "/login",method=RequestMethod.GET)
    public ResponseEntity<Object> get_name(@RequestParam(name="email")String mail,@RequestParam(name="password")String password) {
        Client ee=null;
        try{
            ee=LoginDao.update_first(mail, password);
            if(ee!=null){
                return new ResponseEntity<>(ee, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("{\"message\": \"utilisateur inexistant\" }", HttpStatus.OK);
            }
        }
        catch(Exception aaa){
            aaa.printStackTrace();
            return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/logout",method=RequestMethod.GET)
    public ResponseEntity<Object> logout(@RequestParam(name="token")String mail) {
        Client ee=null;
        try{
             LoginDao.update(mail,null,null);
        }
        catch(Exception aaa){
            aaa.printStackTrace();
            return new ResponseEntity<>("{\"message\": \""+aaa.getMessage()+"\"}", HttpStatus.OK);
        }
        return new ResponseEntity<>("{\"message\": \"Logout succes\"}", HttpStatus.OK);
    }
    @RequestMapping(value = "/inscrire",method=RequestMethod.POST)
    public ResponseEntity<Object> logout(@RequestBody Client mail) {
        try{
            mail.inscrire();
            return new ResponseEntity<>("Inscription succes", HttpStatus.OK);
        }
        catch(Exception aaa){
            aaa.printStackTrace();
            return new ResponseEntity<>(aaa.getMessage(), HttpStatus.OK);
        }
    }
    @GetMapping("/enchereclient/{id}")
    public ResponseEntity<Object> getEnchereByClient(@PathVariable String id) throws Exception{  
        Client xx=Client.FindByToken(id);
        return new ResponseEntity<>(Enchere.findByIdClient(xx.getId()), HttpStatus.OK);
    } 
    private void saveUploadedFile(MultipartFile file,int id,Connection con) throws Exception {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            int curr=Convertisseur.get_current_sequence("produit",con);
            File myObj = new File("MEDIA/"+id+"/"+curr+".png");
            myObj.createNewFile();
            Path path = Paths.get(myObj.toURI());
            Files.write(path, bytes);
            InputStream input = new FileInputStream(myObj);
            BufferedImage originalImage = ImageIO.read(input);
            Image newResizedImage = originalImage.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
            String s = myObj.getAbsolutePath();
            String fileExtension = s.substring(s.lastIndexOf(".") + 1);
            ImageIO.write(convertToBufferedImage(newResizedImage),
                    fileExtension, myObj);
        }
    }
    public static BufferedImage convertToBufferedImage(Image img) {;

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }
//        @RequestMapping(value = "/api/render/file",method=RequestMethod.GET)
//        public ResponseEntity<Object> multiUploadFileModel() throws IOException{
//                File b =new File("serveur/");
//                File[] tab=b.listFiles();
//                byte[][] pae=new byte[tab.length][];
//                List<String> encodedString=new ArrayList<>();
//                for(int i=0;i<tab.length;i++){
//                    pae[i] = Files.readAllBytes(tab[i].toPath());   
//                    encodedString.add(Base64.getEncoder().encodeToString(pae[i]));
//
//                }
//            return new ResponseEntity<>(encodedString, HttpStatus.OK);
//        }        
    @RequestMapping(value = "/api/upload/file/{id}",method=RequestMethod.POST)
    public ResponseEntity<Object> multiUploadFileModel(@PathVariable int id,@RequestParam("File") MultipartFile file) {
            ObjectMapper map=new ObjectMapper();
            try{
                System.out.println((file)+"    cccccc     "+id);
                if(file!=null){
                    Connection con=new Connexion().getConnect();
                    saveUploadedFile(file,id,con);        
                }
                return new ResponseEntity<>("Successfully uploaded!", HttpStatus.OK);
            }
            catch(Exception ae){
                ae.printStackTrace();
               return new ResponseEntity<>(ae.getMessage(),HttpStatus.BAD_REQUEST);                
            }
    }   
   	@GetMapping("/enchere/{id}")
	public Enchere getEnchere(@PathVariable int id) throws Exception{
            Enchere pdd=ProduitDao.getEnchere(id);
            return pdd;
	}
        
        @GetMapping("/solde/{id}")
	public double getEnchere(@PathVariable String id) throws Exception{
            Client c = Client.FindByToken(id);
            
            return new Compte().solde(c.getId());
	}
}