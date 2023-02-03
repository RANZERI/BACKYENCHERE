/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import connectivity.Connexion;
import exception.CustomizeException;
import gestion.Convertisseur;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.Client;

/**
 *
 * @author Mendrika
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Transaction {
    private String id;
    private String time;
    private double montant;
    private Enchere enchere;
    private Client cli;

    public String getId() {
        return id;
    }
    public static Transaction get_last(int id) throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        ResultSet rs = null;
        ArrayList list =null;
        Transaction cc=null;
        try {
            list = new ArrayList();
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "SELECT*FROM \"Transaction\" where idEnchere="+id;
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                cc=new Transaction();
                Client c=new Client();
                Enchere pal=new Enchere();
                c.setId(rs.getInt(5));
                pal.setId(rs.getInt(4));
                cc.setCli(c);
                cc.setEnchere(pal);
                cc.setMontant(rs.getDouble(3));
            }
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
         return cc;

    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }
    public void save() throws Exception{
    Statement stmt=null;
        Connection con=new Connexion().getConnect();
        Client x=Client.FindByToken(cli.getToken());
        try{
            String sql="Insert into \"Transaction\"(time,montant,idEnchere,idClient) values('"+LocalDateTime.now().toString()+"',"+this.getMontant()+","+this.getEnchere().getId()+","+x.getId()+")";
            stmt=con.createStatement();
            System.out.println(sql);
            ObjectMapper map=new ObjectMapper();
            Enchere re=Enchere.findById((this.getEnchere().getId()));
            int i=0;
            if(re.getDate_fin().isAfter(LocalDateTime.now())){  
                System.out.println(Transaction.get_last((this.getEnchere().getId()))+"      ccccc");
                System.out.println(re.getPrix_depart()+"      ccccc     "+this.getMontant());
                if(Transaction.get_last((this.getEnchere().getId()))==null && re.getPrix_depart()<=this.getMontant()){            
                    stmt.executeUpdate(sql);           
                    i=1;
                }
                else{
                    if(Transaction.get_last((this.getEnchere().getId())).getCli().getId()!=this.getCli().getId()){
                        if( re.getPrix_depart()<=this.getMontant() && Transaction.get_last((this.getEnchere().getId())).getMontant()<this.getMontant()){
                            stmt.executeUpdate(sql); 
                            i=1;
                        }                       
                    }
                }
                if(i!=1){
                    throw new CustomizeException("Transaction invalide!");
                }
            }
            else{
               throw new CustomizeException("Enchere deja terimine");
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
    }
    public void setTime(String time) {
        this.time = time;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Enchere getEnchere() {
        return enchere;
    }

    public void setEnchere(Enchere enchere) {
        this.enchere = enchere;
    }

    public Client getCli() {
        return cli;
    }

    public void setCli(Client cli) {
        this.cli = cli;
    }
    
    
}
