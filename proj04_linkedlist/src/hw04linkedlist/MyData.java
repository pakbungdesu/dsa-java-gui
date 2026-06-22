/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw04linkedlist;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author User
 */

public class MyData {
    
    final String COMMA_DELIMITER = ",";
    static DNode START = null;
    static DNode LAST = null;
    int N = 0;
    
    public void readFile(String dir) throws Exception{
        int i = 0;
        DNode CURR = null;
        // reset
        START = null; LAST = null;
        
        try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(i == 0){
                    i++; // header
                } else {
                    String[] values = line.split(COMMA_DELIMITER);               
                    DNode mynode = new DNode();
                    Equipment myeq = new Equipment(Integer.parseInt(values[0]), 
                            values[1], 
                            values[2], 
                            Float.parseFloat(values[3]));
                    
                    mynode.INFOR = myeq;
                    
                    if(START == null){                  
                        START = mynode;
                        CURR = START;
                    } else {
                        CURR.FORW = mynode;
                        mynode.BACK = CURR;
                        CURR = mynode;
                    }
                    i++;
                }            
            }
            LAST = CURR;
            N = i-1; // Update length of the file
            
        } catch (Exception ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
            throw ex;
        }
    }
    
// ------------------- INSERT -------------------
    
// Aj. Chou Algor     
//    public static void INSTWL(DNode LOCA, DNode LOCB, DNode NEW){      
//       LOCA.FORW = NEW; NEW.FORW = LOCB;
//       LOCB.BACK = NEW; NEW.BACK = LOCA;       
//    }
    
     public void insertNode(DNode NEW) {
        if (NEW == null) return;

        if (START == null) {   // Empty list
            START = NEW;
            LAST = NEW;
            NEW.BACK = null;
            NEW.FORW = null;
        } else {               // Append at end
            LAST.FORW = NEW;
            NEW.BACK = LAST;
            NEW.FORW = null;
            LAST = NEW;
        }
        N++; 
    }
     
// ------------------- DELETE -------------------

// Aj. Chou Algor
//    public static void DELTWL(DNode LOC){      
//       LOC.BACK.FORW = LOC.FORW;
//       LOC.FORW.BACK = LOC.BACK;
//    }
     
    public void deleteRecord(DNode LOC) throws Exception {
        if (LOC == null) throw new Exception("Cannot delete null node");

        // Adjust previous node
        if (LOC.BACK != null) {
            LOC.BACK.FORW = LOC.FORW;
        } else {
            // Deleting first node
            START = LOC.FORW;
        }

        // Adjust next node
        if (LOC.FORW != null) {
            LOC.FORW.BACK = LOC.BACK;
        } else {
            // Deleting last node
            LAST = LOC.BACK;
        }

        LOC.BACK = null;
        LOC.FORW = null;

        N--; // Update length
    }

    
//    public static void ForwardTraversingList(){      
//        DNode PTR=null;
//        PTR=START;
//        while(PTR!=null){
//            System.out.println(PTR.INFOR);  //Apply PROCESS to INFOR[PTR]
//            PTR=PTR.FORW;
//        }
//    }
    
// ------------------- SEARCH -------------------
    
    public DNode searchId(int id){
        DNode PTR = START;
        DNode LOC = null;
        try {
            while(PTR != null){
                // Apply PROCESS to INFOR[PTR]
                if(PTR.INFOR.getId() == id){
                    LOC = PTR;
                    return LOC;
                }
                PTR=PTR.FORW;
            }
        } catch (NullPointerException e) {
            System.err.println("Deletion Error: Encountered a null value while "
                    + "checking node data." + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw e;
        }
        return LOC;
    }
    
    public DNode searchName(String name){
        DNode PTR = START;
        DNode LOC = null;
        try {       
            while(PTR != null){
                // Apply PROCESS to INFOR[PTR]
                if(PTR.INFOR.getName() == null ? name == null : PTR.INFOR.getName().equals(name)){
                    LOC = PTR;
                    return LOC;
                }
                PTR=PTR.FORW;
            }
        } catch (NullPointerException e) {
            System.err.println("Deletion Error: Encountered a null value while checking node data." + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw e;
        }
        return LOC;
    }
    
    
    // ------------------- SEARCH -------------------
    
    public void deletePriceRange(int rangeIndex) throws Exception{
        DNode PTR = START;
        while (PTR != null) {
            float price = PTR.INFOR.getPrice();
            boolean inRange = switch (rangeIndex) {
                case 0 -> price < 50.0;
                case 1 -> price >= 50.0 && price < 100.0;
                case 2 -> price >= 100.0 && price < 150.0;
                default -> price >= 150.0;
            };
            if (inRange) {
                deleteRecord(PTR);
            }
            PTR = PTR.FORW;
        }
    }
    
    public void deleteCategory(String category) throws Exception{
        DNode PTR = START;
        try {        
            while(PTR != null){
                // Apply PROCESS to INFOR[PTR]
                if(PTR.INFOR.getCategory() == null ? category == null : PTR.INFOR.getCategory().equals(category)){
                    deleteRecord(PTR);
                }
                PTR=PTR.FORW;
            }
        } catch (NullPointerException e) {
            System.err.println("Deletion Error: Encountered a null value while checking node data." + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw e;
        }
    }
    
    
    public boolean saveFile(String dir) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dir))) {
            // Write header
            pw.println("ID,Name,Category,Price");
            DNode PTR = START; 

            // Write data
            while(PTR != null){
                // Apply PROCESS to INFOR[PTR]
                pw.printf("%d,%s,%s,%.2f%n", 
                  PTR.INFOR.getId(), 
                  PTR.INFOR.getName(), 
                  PTR.INFOR.getCategory(), 
                  PTR.INFOR.getPrice());
                PTR=PTR.FORW;
            }
            return true;
        } catch (IOException ex) {
            System.err.println("Failed to save file: " + ex.getMessage());
            return false;
        }
    }
    
    public int getLenFile(){
        return N;
    }
    
    public DNode getStart(){
        return START;
    }
    
    public DNode getEnd(){
        return LAST;
    }
    
    public void setStart(DNode node) {
        try {
            START = node;
            if (node != null) {
                node.BACK = null;  // The first node has no previous node
            }
        } catch (Exception e) {
            System.err.println("Error setting START node: " + e.getMessage());
        }
    }

    public void setEnd(DNode node) {
        try {
            LAST = node;
            if (node != null) {
                node.FORW = null;  // The last node has no next node
            }
        } catch (Exception e) {
            System.err.println("Error setting END node: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        
  }
}
