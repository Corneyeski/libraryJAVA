/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.model;

/**
 *
 * @author alanv
 */
public class Author {
    
    private int ida;
    private String name;
    private String surnamje;
    private String country;

    public Author() {
    }

    public Author(int ida, String name, String surnamje, String country) {
        this.ida = ida;
        this.name = name;
        this.surnamje = surnamje;
        this.country = country;
    }

    public int getIda() {
        return ida;
    }

    public void setIda(int ida) {
        this.ida = ida;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnamje() {
        return surnamje;
    }

    public void setSurnamje(String surnamje) {
        this.surnamje = surnamje;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Author{" + "ida=" + ida + ", name=" + name + ", surnamje=" + surnamje + ", country=" + country + '}';
    }
}
