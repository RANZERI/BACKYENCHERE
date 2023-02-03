/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import materiel.Enchere;

/**
 *
 * @author Mendrika
 */
public class Convertisseur {
    public static LocalDate get_dates(String date){
        String[] pa=date.split("-");
        System.out.println(pa[0]+"   fkkf   "+pa[1]+"       "+pa[2]);
        return LocalDate.of(Integer.parseInt(pa[0]), Integer.parseInt(pa[1]), Integer.parseInt(pa[2]));
    }
    public static LocalDateTime get_date(String date){
        String[] pa=date.split(" ");
        String[] pas=pa[1].split(":");
        String[] paa=pa[0].split("-");
        return LocalDateTime.of(Integer.parseInt(paa[0]),Integer.parseInt(paa[1]),Integer.parseInt(paa[2]),Integer.parseInt(pas[0]),Integer.parseInt(paa[1]));
    }
    public static String get_date_between(LocalDateTime uni){
        LocalDateTime tempDateTime = LocalDateTime.from( LocalDateTime.now() );
        long days = tempDateTime.until( uni, ChronoUnit.DAYS );
        tempDateTime = tempDateTime.plusDays( days );
        long hours = tempDateTime.until( uni, ChronoUnit.HOURS );
        tempDateTime = tempDateTime.plusHours( hours );
        long minutes = tempDateTime.until( uni, ChronoUnit.MINUTES );
        tempDateTime = tempDateTime.plusMinutes( minutes );
        long seconds = tempDateTime.until( uni, ChronoUnit.SECONDS );
        String xa=hours+"h - "+minutes+"m - "+seconds+"s";
        if(days!=0){
            xa=days+"j - "+xa;
        }

        if(days<0 || hours<0 || minutes<0 || seconds<0){
            return ("0j - 0h - 0m - 0s");
        }
        else{
            return (xa);
        }
    }
    public static int get_current_sequence(String table,Connection con) throws SQLException{
        int iaa=0;
        PreparedStatement stmt=null;
        try{
            String dona="SELECT last_value from "+table+"_id_seq";
            System.out.println(dona+"   nnn");
            stmt=con.prepareStatement(dona);            
            ResultSet fin=stmt.executeQuery();
            while(fin.next()){
                iaa=fin.getInt(1);
            }
        }
        finally{
             stmt.close();            
        }
        return iaa;
    }
}
