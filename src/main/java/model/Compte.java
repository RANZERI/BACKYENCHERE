/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import connectivity.Connexion;
import java.sql.Date;
import java.sql.Statement;

/**
 *
 * @author randretsa
 */
public class Compte {
    int id;
    double credit;
    Date date;
    String clientid;
    boolean estvalider;

    public void chargerCompte() throws Exception{
        Statement stmt = null;
        String sql = null;
        Connexion connexion = null;
        try {
            connexion = new Connexion();
            stmt = connexion.getConnect().createStatement();
            sql = "insert into Compte(credit,date,clientid,estvalider) values("+this.credit+",current_date,"+this.clientid+",false)";
            stmt.execute(sql);
        } catch (Exception e) {
            //TODO: handle exception
            throw e;
        }
        finally{
            stmt.close();
            connexion.getConnect().close();
        }
    }
            
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public boolean isEstvalider() {
        return estvalider;
    }

    public void setEstvalider(boolean estvalider) {
        this.estvalider = estvalider;
    }
    
    
}
