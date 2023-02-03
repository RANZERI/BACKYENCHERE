/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.InsertOneResult;
import java.util.ArrayList;
import java.util.List;
import model.Client;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author randretsa
 */
public class Mongo {
     //static String uri = "mongodb://mongo:D3Ygfaxnv25ykUeh9Gy0@containers-us-west-43.railway.app:6810";
     static String uri = "mongodb://mongo:nmbLWWDMf7g84bXf4PLW@containers-us-west-82.railway.app:7116"; 
    public void saveTransaction(int idenchere,Transaction transact,double montant) throws Exception{
         
        Enchere tobuy = new Enchere().findByIdEnchere(idenchere);
        Client client = Client.FindByToken(transact.getCli().getToken());
        
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            

            
            MongoDatabase database = mongoClient.getDatabase("enchere");
            MongoCollection<Document> collection = database.getCollection("historique");
           
            Document enchere = new Document();
            enchere.append("idenchere", tobuy.getId());
            enchere.append("name", tobuy.getName());
            enchere.append("description",tobuy.getDescription());
            enchere.append("date_debut", tobuy.getDate().toString());
            enchere.append("date_fin", tobuy.getDate_fin().toString());
            enchere.append("montant", montant);
            enchere.append("image", tobuy.getImage());

            Document doc = new Document();
            doc.append("idclient", client.getId());
            doc.append("enchere", enchere);
            
            InsertOneResult result = collection.insertOne(doc);
            System.out.println("Inserted a document with the following id: " 
            + result.getInsertedId().asObjectId().getValue());
            
                
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                
                Document commandResult = database.runCommand(command);
                System.out.println("\n");
                System.out.println("Connected successfully to server.");
            
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
    }
    
    public List<?> Historique(int id){
        
            ArrayList list = new ArrayList();
           try (MongoClient mongoClient = MongoClients.create(uri)) {

            MongoDatabase database = mongoClient.getDatabase("enchere");
            MongoCollection<Document> collection = database.getCollection("historique");
            
            Bson equal = eq("idclient",id); 
            
            collection.find(equal).forEach(doc -> list.add(doc));
            
            /*for(String name : database.listCollectionNames()){
                System.out.println(name);
            }*/
                
            try {
                Bson command = new BsonDocument("ping", new BsonInt64(1));
                
                Document commandResult = database.runCommand(command);
                System.out.println("\n");
                System.out.println("Connected successfully to server.");
            
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
            }
        }
        
        return list;
    }
}
