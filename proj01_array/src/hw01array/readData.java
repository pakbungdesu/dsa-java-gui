/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hw01array;
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

public class readData {
    
    final String COMMA_DELIMITER = ",";
    String names[] = new String[100];
    String categories[] = new String[100];
    Float prices[] = new Float[100];
    String del_name, del_category;
    Float del_price;
    int N = 0, K = -1;
    
    public void readFile(String dir){
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);

                if(i > 0){ // Prevent reading headers
                    names[i-1] = values[1];
                    categories[i-1] = values[2];
                    prices[i-1] = Float.valueOf(values[3]);
                }
                i++;
            }
            N = i-1; // Update length of the file
        } catch (IOException ex) {
            System.err.println("Unexpected error: " + ex.getMessage());
        }
    }
    
// Aj. Chou Algor    
//    public static void Insert(int ITEM){
//       int J=N;
//       while(J>=K){
//           LA[J+1]=LA[J];
//           J--;
//       }
//       LA[K]=ITEM;
//       N++;
//    }
    
     public Boolean insertRecord(String name, String type, Float price) {
        try {
            int J = N;
            while (J >= K) {
                names[J + 1] = names[J];
                categories[J + 1] = categories[J];
                prices[J + 1] = prices[J];
                J--;
            }
            names[K] = name;
            categories[K] = type;
            prices[K] = price;
            N++;
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Index out of bounds while inserting record. " + e.getMessage());
            return false;
        } catch (NullPointerException e) {
            System.err.println("Error: One of the arrays is not initialized. " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }

// Aj. Chou Algor
//    public static void Delete(){ // (int LA[], int N, int K, int ITEM )
//       int ITEM=LA[K];
//       for (int J=K;J<N-1;J++){
//           LA[J]=LA[J+1];
//       }      
//       N--;
//    }
     
    public void deleteRecord(int K) throws Exception {
        try {
            del_name = names[K];
            del_category = categories[K];
            del_price = prices[K];

            for (int J = K; J < N - 1; J++) {
                names[J] = names[J + 1];
                categories[J] = categories[J + 1];
                prices[J] = prices[J + 1];
            }
            N--;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Index out of bounds while deleting record. " + e.getMessage());
            throw e;  // rethrow so caller can handle it
        } catch (NullPointerException e) {
            System.err.println("Error: One of the arrays is not initialized. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw e;
        }
    }



    public void deleteCategory(String category) throws Exception {
        try {
            del_category = category;
            for (int j = 0; j < N; j++) {
                if (categories[j] != null && categories[j].equalsIgnoreCase(del_category)) {
                    this.deleteRecord(j);
                    j--; // adjust index after shifting
                }
            }
        } catch (NullPointerException e) {
            System.err.println("Error: Category array contains null values. " + e.getMessage());
            throw e; // rethrow to let caller handle it
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Index out of bounds while deleting category. " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            throw e;
        }
    }

    
    public String getName(int index){
        return names[index];
    }
    
    public String getCategory(int index){
        return categories[index];
    }
    
    public Float getPrice(int index){
        return prices[index];
    }
    
    public String getFileName(int index){
        return names[index].replace(" ", "_") + ".png";
    }
    
    public int getLenFile(){
        return N;
    }
    
    public boolean saveFile(String dir) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(dir))) {
            // Write header
            pw.println("ID,Name,Category,Price");

            // Write data
            for (int j = 0; j < N; j++) {
                pw.printf("%d,%s,%s,%.2f%n", 
                          j, 
                          names[j], 
                          categories[j], 
                          prices[j]);
            }
            return true;
        } catch (IOException ex) {
            System.err.println("Failed to save file: " + ex.getMessage());
            return false;
        }
    }


    public static void main(String[] args) throws FileNotFoundException {
        
  }
}
