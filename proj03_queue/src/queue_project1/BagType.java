/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queue_project1;

/**
 *
 * @author User
 */
public class BagType {
    private String typeName;
    private float maxWeight;
    private int finePerKg;

    // Constructor
    public BagType(String typeName, float maxWeight, int finePerKg) {
        this.typeName = typeName;
        this.maxWeight = maxWeight;
        this.finePerKg = finePerKg;
    }

    // Get methods
    public String getTypeName() {
        return typeName;
    }

    public float getMaxWeight() {
        return maxWeight;
    }

    public int getFinePerKg() {
        return finePerKg;
    }
}

