/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package materiels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import java.io.Serializable;

/**
 *
 * @author Mendrika
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Etat implements Serializable{
    private int id;
    private String nom;

    public Etat(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    
}
