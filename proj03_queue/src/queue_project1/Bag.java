/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queue_project1;

/**
 *
 * @author User
 */
public class Bag {
    BagType bagType;
    String ticketNo;
    Float weight;
    String ownerName;
    
    Bag(BagType bagType, String ticketNo, Float weight, String ownerName){
        this.bagType = bagType;
        this.ticketNo = ticketNo;
        this.weight = weight;
        this.ownerName = ownerName;
    }
    
    public BagType getBagType() {
        return bagType;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public Float getWeight() {
        return weight;
    }

    public String getOwnerName() {
        return ownerName;
    }
    
    public String returnInfo(int number){
        String res1 = "Bag no. " + number + " (" + ticketNo + ")\n"
                + "Category: " + this.getBagType().getTypeName() + "\n"
                + "Owner: " + this.ownerName + "\n"
                + "Weight: " + this.weight + " / Maximum Weight: " 
                + this.getBagType().getMaxWeight() + "\n";
        
        String res2 = null;
        Float fine = null;
        Float exceed = this.weight - this.getBagType().getMaxWeight();
        if(exceed > 0){
            fine = exceed * this.getBagType().getFinePerKg();
        }
        
        if(fine != null){
            res2 = "Weight exceeding " + exceed + " Kg. Fine = " + fine + " Baht.";
        } else {
            res2 = "Weight not exceeding. Fine = 0 Baht.";
        }
        
        String info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
        + (res1 + res2).replaceAll("\n", "<br>")
        + "</span></html>";
        return info;
    }
}
