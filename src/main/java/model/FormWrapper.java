/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Mendrika
 */
public class FormWrapper {
    private MultipartFile image;
    private String title;
    private String description;

    public MultipartFile getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    
    
}