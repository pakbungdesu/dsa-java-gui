/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package hw04linkedlist;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author User
 */
public class linkedlist1 extends javax.swing.JFrame {
    static MyData data;
    static DefaultTableModel tableModel = null;
    static File uploadedImageFile = null;
    static JTable myTable = null;
    static JScrollPane scrollPane = null;
    static ImageIcon foundImage = null;
    static String[] option = {"Under 50$", "50$-100$", "100$-150$", "Above 150$"};
    static String[] columnNames = {"ID", "Product Name", "Category", "Price"};
    static int insert_idx = 0, recordCount = 0;
    static DNode showNode = null;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(linkedlist1.class.getName());

    /**
     * Creates new form array01
     */
    public linkedlist1() {

        UIManager.put("OptionPane.messageFont", new Font("MS Reference Sans Serif", Font.PLAIN, 16));
        UIManager.put("OptionPane.buttonFont", new Font("MS Reference Sans Serif", Font.BOLD, 14));
        
        Image bgImage = null;
        
        // Set background image
        try {
            bgImage = new ImageIcon(getClass().getResource("/hw04linkedlist/ball.jpeg")).getImage();
            
        } catch (Exception e) {
            System.err.println("Error: Background image not found. Please check the file path.");
        }
        
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgImage);
        
        Image icon = null;
        // Set window icon image
        try {
            icon = new ImageIcon(getClass().getResource("/hw04linkedlist/icon2.png"))
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Error: Window icon image not found. Please check the file path.");
        }
        
        
        // Now, call initComponents(). This will create all the components
        initComponents();
        backgroundPanel.add(roundedPanel1);
        backgroundPanel.add(roundedPanel3);
        backgroundPanel.setLayout(null);
        this.setIconImage(icon);
        this.setContentPane(backgroundPanel);
        
        
        // ---------------- Import CSV Menu ----------------
        JMenuItem importItem = new JMenuItem("Import CSV");
        jMenu1.add(importItem);
        
        importItem.addActionListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open CSV File");
            int userSelection = fileChooser.showOpenDialog(linkedlist1.this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                try {
                    data = new MyData();
                    data.readFile(filePath);
                    recordCount = data.getLenFile();
                    insert_idx = data.getEnd().INFOR.getId() + 1;
                    if (recordCount > 0) {
                        JOptionPane.showMessageDialog(linkedlist1.this, "File imported successfully!\nTotal records: " + recordCount, "Import Successful", JOptionPane.INFORMATION_MESSAGE);
                        insertID(insert_idx);
                        showNode = data.getStart();
                        showImage(showNode);
                        // Use new showDisplay (it will automatically get correct data from linked list)
                        showDisplay(jLabel6, showNode.INFOR.getId() + "", "Failed to update ID label in jPanel6: ");
                        showDisplay(jLabel7, showNode.INFOR.getName() + "", "Failed to update Name label in jPanel7: ");
                        showDisplay(jLabel8, showNode.INFOR.getCategory() + "", "Failed to update Category label in jPanel8: ");
                        showDisplay(jLabel9, showNode.INFOR.getPrice() + "", "Failed to update Price label in jPanel9: ");
                        showDisplay(jLabel12, String.valueOf(insert_idx) + " (Default)", "Failed to update default insert_idx in jPanel12: ");
                    } else {
                        JOptionPane.showMessageDialog(linkedlist1.this, "Failed to import file.\nThe file may be empty or invalid.", "Import Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    // Handles any unexpected exception
                    JOptionPane.showMessageDialog(linkedlist1.this, "An error occurred while importing the file:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(linkedlist1.this, "No file selected. Import cancelled.", "Cancelled", JOptionPane.WARNING_MESSAGE);
            }
        });

        // ---------------- Save All Menu ----------------
        JMenuItem saveItem = new JMenuItem("Save All");
        jMenu1.add(saveItem);
        saveItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save CSV File");

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Add .csv extension if missing
                if (!filePath.endsWith(".csv")) {
                    filePath += ".csv";
                }

                if (data.saveFile(filePath)) {
                    JOptionPane.showMessageDialog(this, "File saved successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to save file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    // ------------ DELETE PART ------------ 
    private void displayDeleteID(int searchID){
        // Delete by ID
        if (data == null || data.getStart() == null) {
            JOptionPane.showMessageDialog(this, "No data available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DNode wantToDelete = data.searchId(searchID);

        if (wantToDelete != null) {
            String imagePath = "src/images/" + wantToDelete.INFOR.getFileName();
            foundImage = new ImageIcon(imagePath);
            Image scaledImg = foundImage.getImage().getScaledInstance(257, 231, Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(scaledImg);

            int choice = JOptionPane.showConfirmDialog(this,
                "Name '" + wantToDelete.INFOR.getName() + "' found in records."
                + "\nID: " + wantToDelete.INFOR.getId()
                + "\nName: " + wantToDelete.INFOR.getName()
                + "\nCategory: " + wantToDelete.INFOR.getCategory()
                + "\nPrice: " + wantToDelete.INFOR.getPrice() + "$"
                + "\n\nAre you sure you want to delete this record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                newIcon
            );

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    data.deleteRecord(wantToDelete);
                    recordCount = data.getLenFile();
                    insert_idx = data.getEnd().INFOR.getId() + 1;
                    insertID(insert_idx);
                    JOptionPane.showMessageDialog(this, "Record deleted successfully.");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error deleting record: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Deletion cancelled.");
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "ID '" + searchID + "' not found in records.",
                "Search Result",
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

        
    private void displayDeleteName(String searchName){
        
        DNode wantToDelete = data.searchName(searchName);
        
        if (wantToDelete != null) {
            String imagePath = "src/images/" + wantToDelete.INFOR.getFileName();
            foundImage = new ImageIcon(imagePath);
            Image scaledImg = foundImage.getImage().getScaledInstance(257, 231, Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(scaledImg);

            int choice = JOptionPane.showConfirmDialog(this,
                "Name '" + wantToDelete.INFOR.getName() + "' found in records."
                + "\nID: " + wantToDelete.INFOR.getId()
                + "\nName: " + wantToDelete.INFOR.getName()
                + "\nCategory: " + wantToDelete.INFOR.getCategory()
                + "\nPrice: " + wantToDelete.INFOR.getCategory() + "$"
                + "\n\nAre you sure you want to delete this record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                newIcon
            );

            // Check user choice
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    data.deleteRecord(wantToDelete);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                            "Unexpected error: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                recordCount = data.getLenFile();
                insert_idx = data.getEnd().INFOR.getId() + 1;
                insertID(insert_idx);
                JOptionPane.showMessageDialog(this, "Record deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Deletion cancelled.");
            }

        } else {
            JOptionPane.showMessageDialog(this,
                searchName + " not found in records.",
                "Search record for deleting",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void displayScrollDelCategory(String delCategory) {
        scrollPane.setPreferredSize(new Dimension(450, 200));

        // Ask for confirmation with scrollPane content
        int choice;
        choice = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Records for category '" + delCategory + "'. Are you sure you want to delete these records?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            try {
                // Attempt deletion
                data.deleteCategory(delCategory);

                // Refresh record state
                recordCount = data.getLenFile();
                insert_idx = data.getEnd().INFOR.getId() + 1;
                insertID(insert_idx);

                JOptionPane.showMessageDialog(this, 
                    "Records for category '" + delCategory + "' deleted successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "An error occurred while deleting records: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Deletion cancelled for category '" + delCategory + "'.");
        }
    }
    
    
    private void displayDelScrollPriceRange(int rangeIndex) {
        
        int row = searchPriceRange(rangeIndex);
        
        if(row != 0){
            scrollPane.setPreferredSize(new Dimension(450, 200));

            // Confirmation dialog with scrollPane content
            int choice = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Records for price " + option[rangeIndex] + 
                ". Are you sure you want to delete these records?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    data.deletePriceRange(rangeIndex);

                    // Refresh record count and UI state
                    recordCount = data.getLenFile();
                    insert_idx = data.getEnd().INFOR.getId() + 1;
                    insertID(insert_idx);

                    JOptionPane.showMessageDialog(this, 
                        "Records for price " + option[rangeIndex] + " deleted successfully.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Unexpected error while deleting price range: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Deletion cancelled for price " + option[rangeIndex] + ".");
            }
        } else {
             JOptionPane.showMessageDialog(this, 
                        "No records found for price " + option[rangeIndex]);
        }

    }

    // ------------ INSERT PART ------------ 
    
    private void insertID(int idx){
        try{
            jLabel12.setText(String.valueOf(idx) + " (Default)");
        } catch (Exception ex) {
            System.err.println("Failed to update ID label in jPanel2: " + ex.getMessage());
        }
    }
    
    // Use resetInsert after inserting successfully for:
    private void resetInsert(){
        insertID(insert_idx);

        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox1.setSelectedItem("Ball Sports");
        jLabel17.setIcon(null);
    }
    
    private boolean saveImage(String productName) {
        try {
            // make sure label has an image
            if (jLabel17.getIcon() == null) {
                JOptionPane.showMessageDialog(this, 
                        "No image uploaded yet.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // convert Icon -> BufferedImage
            Icon icon = jLabel17.getIcon();
            BufferedImage bImg = new BufferedImage(
                    icon.getIconWidth(),
                    icon.getIconHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );
            Graphics g = bImg.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();

            // build output path (make sure "images" folder exists)
            String imageFileName = productName.replaceAll("\\s+", "_") + ".png";
            File outputFile = new File("src/images/" + imageFileName); 
            javax.imageio.ImageIO.write(bImg, "png", outputFile);

            JOptionPane.showMessageDialog(this, 
                    "Image saved successfully!\n" + outputFile.getAbsolutePath());
            return true;
        } catch (HeadlessException | IOException ex) {
            JOptionPane.showMessageDialog(this, 
                    "Failed to save image: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    
    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "jpg", "png", "gif"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            uploadedImageFile = fileChooser.getSelectedFile();  // <-- Keep original file

            // Show scaled image
            ImageIcon icon = new ImageIcon(uploadedImageFile.getAbsolutePath());
            Image scaledImg = icon.getImage().getScaledInstance(
                    jLabel17.getWidth(),
                    jLabel17.getHeight(),
                    Image.SCALE_SMOOTH
            );
            jLabel17.setIcon(new ImageIcon(scaledImg));
        }
    }
    
    
     // ------------ DISPLAY PART ------------ 
    
    private void showDisplay(javax.swing.JLabel label, String textFromPtr, String errorMessage){
        try {
            label.setText(textFromPtr);

        } catch (Exception ex) {
            System.err.println(errorMessage + ex.getMessage());
            label.setText("Error");
        }
    }
    
    private void showImage(DNode ptr) {
        try {
            if (ptr == null || ptr.INFOR == null) {
                System.err.println("Image not found.");
                jLabel1.setIcon(null);
                return;
            }

            String imageName = ptr.INFOR.getFileName();
            java.net.URL imageURL = getClass().getResource("/images/" + imageName);
            if (imageURL == null) {
                System.err.println("Image not found in resources: /images/" + imageName);
                jLabel1.setIcon(null);
                return;
            }

            ImageIcon icon = new ImageIcon(imageURL);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(
                    jLabel1.getWidth(),
                    jLabel1.getHeight(),
                    Image.SCALE_SMOOTH
            );
            jLabel1.setIcon(new ImageIcon(scaledImg));

        } catch (Exception ex) {
            System.err.println("Failed to update Image label: " + ex.getMessage());
        }
    }
    
    // ------------ SORT PART ------------ 
    
    public int sortAndMakeScrollPane(boolean byName, boolean nameAsc,
                                 boolean byCategory, boolean categoryAsc,
                                 boolean byPrice, boolean priceAsc) {

            if (recordCount == 0 || data.getStart() == null) {
                return 0;
            }

            // Extract data from the linked list into an ArrayList
            java.util.List<Equipment> list = new java.util.ArrayList<>();
            DNode ptr = data.getStart();
            while (ptr != null) {
                if (ptr.INFOR != null) {
                    list.add(ptr.INFOR);
                }
                ptr = ptr.FORW;
            }

            // Comparator logic
            java.util.Comparator<Equipment> equipmentComparator = (e1, e2) -> {
                int result = 0;

                if (byName) {
                    result = e1.getName().compareToIgnoreCase(e2.getName());
                    if (!nameAsc) result = -result;
                }

                if (result == 0 && byCategory) {
                    result = e1.getCategory().compareToIgnoreCase(e2.getCategory());
                    if (!categoryAsc) result = -result;
                }

                if (result == 0 && byPrice) {
                    result = Float.compare(e1.getPrice(), e2.getPrice());
                    if (!priceAsc) result = -result;
                }

                return result;
            };

            //Bubble Sort
            int n = list.size();
            if (n > 1) { // Only sort if there's more than one element
                boolean swapped;
                for (int i = 0; i < n - 1; i++) {
                    swapped = false;
                    for (int j = 0; j < n - 1 - i; j++) {

                        // Compare two adjacent elements
                        if (equipmentComparator.compare(list.get(j), list.get(j + 1)) > 0) {

                            Equipment temp = list.get(j);
                            list.set(j, list.get(j + 1));
                            list.set(j + 1, temp);
                            swapped = true;
                        }
                    }
                    // If no two elements were swapped by inner loop, then break
                    if (!swapped) break;
                }
            }

            // Build the table model
            tableModel = new DefaultTableModel(columnNames, 0);

            for (Equipment e : list) {
                Object[] row = {
                    e.getId(),
                    e.getName(),
                    e.getCategory(),
                    e.getPrice()
                };
                tableModel.addRow(row);
            }

        myTable = new javax.swing.JTable(tableModel);
        scrollPane = new javax.swing.JScrollPane(myTable);
     
        if(tableModel.getRowCount() != 0){
            myTable = new JTable(tableModel);
            scrollPane = new JScrollPane(myTable);
            return tableModel.getRowCount();
        }
        return 0;
    }

    
    private String concatFilter(boolean byName, boolean nameAsc, boolean byCategory, 
            boolean categoryAsc, boolean byPrice, boolean priceAsc){
        String filter = "";
        if(byName){
            filter += "Name ";
            filter += nameAsc ? "ASC, " : "DESC, ";
        }
        
        if(byCategory){
            filter += "Category ";
            filter += categoryAsc ? "ASC, " : "DESC, ";
        }
        
        if (byPrice) {
            filter += "Price ";
            filter += priceAsc ? "ASC " : "DESC ";
        }

        return filter;
    }
    
    private void displayScrollSort(int rowInPane, String filterAll){
        if (rowInPane != 0) {
            scrollPane.setPreferredSize(new Dimension(450, 200));
            JOptionPane.showMessageDialog(this,
                scrollPane,
                "Sorted records by " + filterAll,
                JOptionPane.PLAIN_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(this,
                "No records found for sorting by " + filterAll,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    // ------------ SEARCH PART ------------ 
    
    private int searchPriceRange(int rangeIndex){
        tableModel = new DefaultTableModel(columnNames, 0);
        DNode PTR = data.getStart();

        while (PTR != null) {
            float price = PTR.INFOR.getPrice();
            boolean inRange = switch (rangeIndex) {
                case 0 -> price < 50.0;
                case 1 -> price >= 50.0 && price < 100.0;
                case 2 -> price >= 100.0 && price < 150.0;
                default -> price >= 150.0;
            };
            if (inRange) {
                tableModel.addRow(new Object[] {
                    PTR.INFOR.getId(),
                    PTR.INFOR.getName(),
                    PTR.INFOR.getCategory(),
                    PTR.INFOR.getPrice()
                });
            }
            PTR = PTR.FORW;
        }

        if(tableModel.getRowCount() != 0){
            myTable = new JTable(tableModel);
            scrollPane = new JScrollPane(myTable);
            return tableModel.getRowCount();
        }
        return 0;
    }
    
    private int searchCategory(String category){
        DNode PTR = data.getStart();
        tableModel = new DefaultTableModel(columnNames, 0);

        while (PTR != null){
            if(PTR.INFOR.getCategory().equals(category)){
                tableModel.addRow(new Object[] {
                    PTR.INFOR.getId(),
                    PTR.INFOR.getName(),
                    PTR.INFOR.getCategory(),
                    PTR.INFOR.getPrice()
                });
            }
            PTR = PTR.FORW;
        }
        
        if(tableModel.getRowCount() != 0){
            myTable = new JTable(tableModel);
            scrollPane = new JScrollPane(myTable);
            return tableModel.getRowCount();
        }
        return 0;
    }
    
    
    private DNode searchName(String name) {
        DNode PTR = data.getStart();
        DNode LOC = null;
        while(PTR != null){
            if (PTR.INFOR.getName().equalsIgnoreCase(name)) {
                try {
                    String imagePath = "src/images/" + PTR.INFOR.getFileName();
                    foundImage = new ImageIcon(imagePath);
                    LOC = PTR;
                    break;
                } catch (Exception ex) {
                    System.err.println("Failed to load image for item '" + name + "': " + ex.getMessage());
                    break;
                }
            }
            PTR = PTR.FORW;
        }
        return LOC;
    }
    
    private void displayScrollSearchCategoryPriceRange(int rowInPane, String tableOfWhat, String searchFor){
        // Searching for category, price range
         if (rowInPane != 0) {
            scrollPane.setPreferredSize(new Dimension(450, 200));
            JOptionPane.showMessageDialog(this,
                scrollPane,
                "Records for " + tableOfWhat + ":  "+ searchFor,
                JOptionPane.PLAIN_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(this,
                "No records found for " + tableOfWhat + ": " + searchFor,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new hw04linkedlist.RoundedPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        roundedPanel3 = new hw04linkedlist.RoundedPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jButton12 = new javax.swing.JButton();
        jComboBox8 = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel16 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(207, 218, 229));
        setIconImages(null);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton1.setText("<");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton2.setText(">");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Product Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 0, 14))); // NOI18N
        jPanel6.setOpaque(false);

        jLabel2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel2.setText("ID :");

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel3.setText("Name :");

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel4.setText("Category :");

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel5.setText("Price :");

        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jButton13.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jButton13.setText("Refresh Display");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18))
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addComponent(jButton13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jButton13)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        roundedPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel10.setText("Name :");

        jLabel11.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel11.setText("ID :");

        jLabel12.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel13.setText("Category :");

        jComboBox1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ball Sports", "Water Sports", "Combat Sports", "Racket Sports", "Fitness", "Cycling", "Others" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel14.setText("Price :");

        jTextField2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel15.setText("Image :");

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("No image uploaded");
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton3.setText("Upload image");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel18.setText("$");

        jButton4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton4.setText("Insert");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 67, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField2)
                        .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4)
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Insert", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel21.setText("ID :");

        jTextField4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N

        jButton9.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton9.setText("Delete by ID");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel22.setText("Name :");

        jTextField5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N

        jButton10.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton10.setText("Delete by Name");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel24.setText("Category :");

        jButton11.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton11.setText("Delete by Category");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel25.setText("Price Range :");

        jComboBox7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Under 50$", "50$ - 100$", "100$ - 150$", "Above 150$" }));

        jButton12.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton12.setText("Delete by Price Range");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jComboBox8.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ball Sports", "Water Sports", "Combat Sports", "Racket Sports", "Fitness", "Cycling", "Others" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(40, 40, 40)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(32, 32, 32)
                                            .addComponent(jTextField5))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(32, 32, 32)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(98, 98, 98)
                                    .addComponent(jButton11))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(99, 99, 99)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(92, 92, 92)
                            .addComponent(jButton12))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGap(114, 114, 114)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10)
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton11)
                .addGap(38, 38, 38)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton12)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Delete", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton7.setText("Start sorting");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jCheckBox1.setText("Sort by Name");

        jCheckBox2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jCheckBox2.setText("Sort by Category");

        jCheckBox3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jCheckBox3.setText("Sort by Price");

        jLabel16.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel16.setText("Select filter (At least one)");

        jComboBox4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ascending", "Descending" }));

        jComboBox5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ascending", "Descending" }));

        jComboBox6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ascending", "Descending" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBox1)
                            .addComponent(jCheckBox3)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox5, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox6, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel16)
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jButton7)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Sort", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton5.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton5.setText("Search by Name");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel19.setText("Name:");

        jTextField3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel20.setText("Category:");

        jComboBox2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ball Sports", "Water Sports", "Combat Sports", "Racket Sports", "Fitness", "Cycling", "Others" }));

        jButton6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton6.setText("Search by Category");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel23.setText("Price Range:");

        jButton8.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton8.setText("Search by Price Range");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jComboBox3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Under 50$", "50$-100$", "100$-150$", "Above 150$", " " }));

        jLabel26.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jLabel26.setText("ID :");

        jTextField6.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N

        jButton14.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        jButton14.setText("Search by ID");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField6)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(37, 37, 37)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(53, 53, 53)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(62, 62, 62)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton5)
                                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(241, 241, 241)))
                .addGap(45, 45, 45))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton14)
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(38, 38, 38)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton8)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Search", jPanel5);

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Monospaced", 0, 13)); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(roundedPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundedPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        int selectedIndex = jComboBox3.getSelectedIndex();
        int rowInPane = searchPriceRange(selectedIndex);

        displayScrollSearchCategoryPriceRange(rowInPane, "price range", option[selectedIndex]);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String selectedCategory = String.valueOf(jComboBox2.getSelectedItem());
        int rowInPane = searchCategory(selectedCategory);
        displayScrollSearchCategoryPriceRange(rowInPane, "category", selectedCategory);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        String name = jTextField3.getText();
        DNode foundNode = searchName(name);

        if (foundNode != null) {
            Image scaledImg = foundImage.getImage().getScaledInstance(257, 231, Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(scaledImg);
            JOptionPane.showMessageDialog(this,
                "Name '" + foundNode.INFOR.getName() + "'  found in records."
                + "\nID: " + foundNode.INFOR.getId()
                + "\nName: " + foundNode.INFOR.getName()
                + "\nCategory: " + foundNode.INFOR.getCategory()
                + "\nPrice: " + foundNode.INFOR.getPrice() + "$",
                "Search Result",
                JOptionPane.INFORMATION_MESSAGE, newIcon);
        } else {
            JOptionPane.showMessageDialog(this,
                "Name '" + name + "' not found in records.",
                "Search Result",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        boolean byName = jCheckBox1.isSelected();
        boolean byCategory = jCheckBox2.isSelected();
        boolean byPrice = jCheckBox3.isSelected();

        if(!byName && !byCategory && !byPrice){
            // Show warning if no filter is selected
            JOptionPane.showMessageDialog(
                this,
                "Please select at least one filter option (Name, Category, or Price).",
                "No Filter Selected",
                JOptionPane.WARNING_MESSAGE
            );

        } else {
            String orderName = (String) jComboBox4.getSelectedItem();
            String orderCategory = (String) jComboBox5.getSelectedItem();
            String orderPrice = (String) jComboBox6.getSelectedItem();

            boolean nameAsc = (orderName.equals("Ascending"));
            boolean categoryAsc = (orderCategory.equals("Ascending"));
            boolean priceAsc = (orderPrice.equals("Ascending"));

            int rowInPane = sortAndMakeScrollPane(byName, nameAsc, byCategory, categoryAsc, byPrice, priceAsc);
            String filterAll = concatFilter(byName, nameAsc, byCategory, categoryAsc, byPrice, priceAsc);
            displayScrollSort(rowInPane, filterAll);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        int selectedIndex = jComboBox7.getSelectedIndex();
        int rowInPane = searchPriceRange(selectedIndex);

        if(rowInPane != -1){
            displayDelScrollPriceRange(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this,
                "Price range '" + option[selectedIndex] + "' not found in records.",
                "Deleting Result",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        String selectedCategory = String.valueOf(jComboBox8.getSelectedItem());
        int rowInPane = searchCategory(selectedCategory);

        if(rowInPane != -1){
            displayScrollDelCategory(selectedCategory);
        } else {
            JOptionPane.showMessageDialog(this,
                "Category '" + selectedCategory + "' not found in records.",
                "Deleting Result",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        String deleteName = jTextField5.getText();
        displayDeleteName(deleteName);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:                                           
        try {

            int delID = Integer.parseInt(jTextField4.getText());
            displayDeleteID(delID);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Error: Please enter a valid integer ID.", 
                    "Invalid Input", 
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "An unexpected error occurred: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            String name = jTextField1.getText().trim();
            String type = String.valueOf(jComboBox1.getSelectedItem());
            String priceText = jTextField2.getText().trim();

            // --- Validation ---
            if (name.isEmpty() || type == null || type.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Name and category must not be empty.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (priceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Price cannot be empty.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float price;
            try {
                price = Float.parseFloat(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Price must be a valid number.", 
                    "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- Save Image ---
            boolean successImage = saveImage(name);
            if (!successImage) {
                JOptionPane.showMessageDialog(this, 
                    "Image save failed. Record not inserted.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Equipment newEquip = new Equipment(insert_idx, name, type, price);
            DNode NEW = new DNode();
            NEW.INFOR = newEquip;

            // Insert at LAST
            data.insertNode(NEW);

            recordCount++;
            insert_idx = data.getEnd().INFOR.getId() + 1;

            JOptionPane.showMessageDialog(this, "Record inserted successfully!");

            // Reset input and refresh display
            resetInsert();

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this,
                "Unexpected error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        uploadImage();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        showNode = data.getStart();
        showImage(showNode);
        showDisplay(jLabel6, showNode.INFOR.getId() + "", "Failed to update ID label in jPanel6: ");
        showDisplay(jLabel7, showNode.INFOR.getName() + "", "Failed to update Name label in jPanel7: ");
        showDisplay(jLabel8, showNode.INFOR.getCategory() + "", "Failed to update Category label in jPanel8: ");
        showDisplay(jLabel9, showNode.INFOR.getPrice() + "", "Failed to update Price label in jPanel9: ");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        showNode = showNode.FORW;
        
        if(showNode == null){
            showNode = data.getEnd();
        }
        showImage(showNode);
        showDisplay(jLabel6, showNode.INFOR.getId() + "", "Failed to update ID label in jPanel6: ");
        showDisplay(jLabel7, showNode.INFOR.getName() + "", "Failed to update Name label in jPanel7: ");
        showDisplay(jLabel8, showNode.INFOR.getCategory() + "", "Failed to update Category label in jPanel8: ");
        showDisplay(jLabel9, showNode.INFOR.getPrice() + "", "Failed to update Price label in jPanel9: ");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        showNode = showNode.BACK;
        
        if(showNode == null){
            showNode = data.getStart();
        }
        showImage(showNode);
        showDisplay(jLabel6, showNode.INFOR.getId() + "", "Failed to update ID label in jPanel6: ");
        showDisplay(jLabel7, showNode.INFOR.getName() + "", "Failed to update Name label in jPanel7: ");
        showDisplay(jLabel8, showNode.INFOR.getCategory() + "", "Failed to update Category label in jPanel8: ");
        showDisplay(jLabel9, showNode.INFOR.getPrice() + "", "Failed to update Price label in jPanel9: ");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        int id;
        try {
            id = Integer.parseInt(jTextField6.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid integer ID.", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (data == null || data.getStart() == null) {
            JOptionPane.showMessageDialog(this, "No data available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DNode nodeFound = data.searchId(id);

        if(nodeFound != null){
            try {
                String imagePath = "src/images/" + nodeFound.INFOR.getFileName();
                foundImage = new ImageIcon(imagePath);
                Image scaledImg = foundImage.getImage().getScaledInstance(257, 231, Image.SCALE_SMOOTH);
                ImageIcon newIcon = new ImageIcon(scaledImg);

                JOptionPane.showMessageDialog(this,
                    "ID '" + nodeFound.INFOR.getId() + "' found in records."
                    + "\nID: " + nodeFound.INFOR.getId()
                    + "\nName: " + nodeFound.INFOR.getName()
                    + "\nCategory: " + nodeFound.INFOR.getCategory()
                    + "\nPrice: " + nodeFound.INFOR.getPrice() + "$",
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE, newIcon
                );
            } catch (HeadlessException ex) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "ID '" + id + "' not found in records.",
                "Search Result",
                JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new linkedlist1().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private hw04linkedlist.RoundedPanel roundedPanel1;
    private hw04linkedlist.RoundedPanel roundedPanel3;
    // End of variables declaration//GEN-END:variables
}
