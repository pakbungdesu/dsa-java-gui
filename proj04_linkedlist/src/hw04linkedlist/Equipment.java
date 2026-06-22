/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw04linkedlist;

/**
 *
 * @author User
 */
public class Equipment {
    private final int id;
    private final String name;
    private final String category;
    private final float price;
    private final String image;
    
    Equipment(int id, String name, String category, float price){
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.image = name.replace(" ", "_") + ".png";
    }
    
    public int getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public String getCategory(){
        return category;
    }
    
    public Float getPrice(){
        return price;
    }
    
    public String getFileName(){
        return image;
    }
}
