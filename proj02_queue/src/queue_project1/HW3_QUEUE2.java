/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package queue_project1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author User
 */
public class HW3_QUEUE2 extends javax.swing.JFrame {
    int clickCount = 0;
    int FRONT = -1,REAR = -1, N;
    Bag QUEUE[];
    private JPopupMenu infoPopup = new JPopupMenu();
    private JLabel popupLabel = new JLabel();
    
    // BagType
    BagType travelBag = new BagType("Travel Bag", 23, 150);
    BagType giftBox = new BagType("Gift Box", 15, 100);
    BagType sportBag = new BagType("Sports Equipment", 32, 200);
    BagType carryOn = new BagType("Carry-on Bag", 7, 300);
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HW3_QUEUE2.class.getName());

    /**
     * Creates new form hw3_queue
     */
    
    public boolean isFull(){
      if((FRONT==0 && REAR==N)|| (FRONT==REAR+1)){       
            return true; //Can not insert
       }
            return false;
    }
    
    public boolean isEmpty(){
        if(FRONT == -1) return true;
        return false;
    }
    
    public Bag QDELETE(){
        if(isEmpty()){
            String info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
                + "Queue Underflow! Cannot delete more bags."
                + "</span></html>";
                    
            JOptionPane.showMessageDialog(this,
            info,
            "Error",
            JOptionPane.ERROR_MESSAGE);
            return null; 
        }
        Bag ITEM=QUEUE[FRONT];
        if(FRONT==REAR){
            FRONT=-1;
            REAR=-1;
        }else{
            if(FRONT==N){
               FRONT=0; 
            }else{
                FRONT++;
            }
        }     
        return ITEM;
    }
  
    public boolean QINSERT(Bag ITEM){     
      if(isFull()){
          String info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
            + "Queue Overflow! Cannot insert more bags."
            + "</span></html>";
          
         JOptionPane.showMessageDialog(this,
            info,
            "Error",
            JOptionPane.ERROR_MESSAGE);
         return false; // Can not insert
      } else {
        if(isEmpty()){ // NULL concept
            FRONT=0; 
            REAR=0;
        }else {
           if(REAR==N){
               REAR=0;
           } else {
               REAR++;
           }  
        }
        QUEUE[REAR]=ITEM;
        return true;
      }
    }
    
    public void showThisData(JLabel label, int i){
        if (QUEUE[i] != null) {
                String imagePath = "/images/" + QUEUE[i].getTicketNo() + ".png";
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                    Image img = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                } catch (Exception e) {
                    label.setIcon(null);
                    System.err.println("Image not found: " + imagePath);
                }
            } else {
                label.setIcon(null);
            }
    }
    
     public void showDatainQueue(JLabel[] labels){
        // clear all icons first
        for(int k=0; k<labels.length; k++){
            labels[k].setIcon(null);
        }

        if(isEmpty()){
            System.out.println("Queue is empty — nothing to show.");
            return;
        }

        int labelPos = 0;

        if(FRONT <= REAR){
            for(int i=FRONT; i<=REAR && labelPos<labels.length; i++){
                showThisData(labels[labelPos], i);
                labelPos++;
            }
        } else {
            for(int i=FRONT; i<=N && labelPos<labels.length; i++){
                showThisData(labels[labelPos], i);
                labelPos++;
            }
            for(int i=0; i<=REAR && labelPos<labels.length; i++){
                showThisData(labels[labelPos], i);
                labelPos++;
            }
        }
    }

    
    public void fixDimension(){
        jLabel1.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel1.setMinimumSize(new java.awt.Dimension(131, 170));
        jLabel1.setMaximumSize(new java.awt.Dimension(131, 170));
        
        jLabel2.setPreferredSize(new java.awt.Dimension(170, 135));
        jLabel2.setMinimumSize(new java.awt.Dimension(170, 135));
        jLabel2.setMaximumSize(new java.awt.Dimension(170, 135));
        
        jLabel3.setPreferredSize(new java.awt.Dimension(130, 122));
        jLabel3.setMinimumSize(new java.awt.Dimension(130, 122));
        jLabel3.setMaximumSize(new java.awt.Dimension(130, 122));

        jLabel4.setPreferredSize(new java.awt.Dimension(100, 100));
        jLabel4.setMinimumSize(new java.awt.Dimension(100, 100));
        jLabel4.setMaximumSize(new java.awt.Dimension(135, 126));
        
        jLabel5.setPreferredSize(new java.awt.Dimension(128, 128));
        jLabel5.setMinimumSize(new java.awt.Dimension(128, 128));
        jLabel5.setMaximumSize(new java.awt.Dimension(128, 128));
    }
    
    public void scenario1(){
        // Dequeue 2 items
        Bag firstDequeued = QDELETE();
        monitor();
        Bag secondDequeued = QDELETE();
        monitor();

        String info = firstDequeued.returnInfo(1).replaceAll("</span></html>", "") + "<br><br>" 
               + secondDequeued.returnInfo(2).replaceAll("<html><span style='font-family:MS Reference "
                       + "Sans Serif; font-size:12pt;'>", "");
        
        JOptionPane.showMessageDialog(this,
        info,
        "Delete 2 items Successful",
        JOptionPane.INFORMATION_MESSAGE);

        // Enqueue 2 items
        Bag firstEnqueued = new Bag(sportBag, "TG1006", 35.0f, "Anucha");
        Bag secondEnqueued = new Bag(carryOn, "TG1007", 6.5f, "Benjawan");
        
        QINSERT(firstEnqueued);
        monitor();
        QINSERT(secondEnqueued);
        monitor();

        // Update label icons dynamically based on ticket name
        JLabel[] labels = { jLabel1, jLabel2, jLabel3, jLabel4, jLabel5 };
        fixDimension();
        showDatainQueue(labels);
        
        jLabel6.setText("Scenario 2");
        
        String text = "<html> Afterwards, you prefer to delete one bag and insert <br>"
                + "2 bags. So, queue is overflow! You cannot insert more.<html>";
        jLabel7.setText(text);
    }
    
    public void scenario2(){
        
        // Scenario 2: delete 1, then insert 2
        Bag deletedBag = QDELETE();
        monitor();

        if (deletedBag != null) {
            JOptionPane.showMessageDialog(this,
                deletedBag.returnInfo(1),
                "1 bag deleted",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            String info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
                + "Queue Underflow! Cannot delete more bags."
                + "</span></html>";
                    
            JOptionPane.showMessageDialog(this,
            info,
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }

        // Create 2 new bags
        Bag firstEnqueued = new Bag(carryOn, "TG1008", 7.0f, "Patcharin");
        Bag secondEnqueued = new Bag(travelBag, "TG1009", 25.0f, "Wanchai");

        // Try to insert both
        QINSERT(firstEnqueued);
        monitor();
        
        QINSERT(secondEnqueued);
        monitor();

        // Update the screen
        JLabel[] labels = { jLabel1, jLabel2, jLabel3, jLabel4, jLabel5 };
        showDatainQueue(labels);
        
        jLabel6.setText("Scenario 3");
        
        String text = "<html>Almost finish! Let's delete all bags.<html>";
        jLabel7.setText(text);
    }
    
    public void scenario3() {
        // Scenario 3: delete all bags
        JLabel[] labels = { jLabel1, jLabel2, jLabel3, jLabel4, jLabel5 };
        
        for (int i = 1; i <= 5; i++) {
            Bag deletedBag = QDELETE();
            monitor();

            if (deletedBag != null) {
                // Show bag info in a formatted popup
                
                JOptionPane.showMessageDialog(this,
                        deletedBag.returnInfo(i),
                        "Bag deleted",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Queue is empty
                String info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
                        + "Queue Underflow! Cannot delete more bags."
                        + "</span></html>";

                JOptionPane.showMessageDialog(this,
                        info,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                break; // stop trying to delete more
            } 
            
            // Update the screen
            showDatainQueue(labels);
        }
        
        jLabel6.setText("Final one");
        
        String text = "<html><div align='center'>"
        + "Try deleting a bag from queue.<br>"
        + "It is underflow, because queue is empty!"
        + "</div></html>";
        jLabel7.setText(text);
    }
    
    public void scenario4(){
        Bag deletedBag = QDELETE();
        monitor();

        if (deletedBag != null) {
            // Show bag info in a formatted popup
            String info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
                    + deletedBag.returnInfo(1)  // i = label index for display
                    + "</span></html>";

            JOptionPane.showMessageDialog(this,
                    info,
                    1 + " bag deleted",
                    JOptionPane.INFORMATION_MESSAGE);
        } 
        jLabel6.setText("Goodbye!");
        String text = "<html>You’ve completed my circular queue simulation.<br>"
                + "Thank you for running my small project!</html>";
        jLabel7.setText(text);
        }

    private void showBagInfoPopup(JLabel label, int labelIndex) {
        int queueIndex = (FRONT + (labelIndex - 1)) % (N + 1);
        String info;

        if (!isEmpty() && QUEUE[queueIndex] != null) {
            Bag b = QUEUE[queueIndex];
            info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
                    + b.returnInfo(labelIndex).replaceAll("\n", "<br>")
                    + "</span></html>";
        } else {
            info = "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>"
                    + "No bag in this position."
                    + "</span></html>";
        }

        popupLabel.setText(info);
        infoPopup.show(label, label.getWidth(), 0);
    }
    
    private void addHoverListener(JLabel label, int labelIndex) {
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                showBagInfoPopup(label, labelIndex);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoPopup.setVisible(false);
            }
        });
    }
    
    public void monitor(){
        jLabel12.setText(FRONT + "");
        jLabel13.setText(REAR + "");
        jLabel14.setText(N + "");
    }
    
    public HW3_QUEUE2() {
        int size = 5;
        N = size-1;
        QUEUE = new Bag[size];
             
        Bag[] bagList = {
            new Bag(travelBag, "TG1001", 21.5f, "Somchai"),
            new Bag(travelBag, "TG1002", 26.0f, "Arisa"),
            new Bag(giftBox, "TG1003", 14.0f, "Napat"),
            new Bag(giftBox, "TG1004", 17.2f, "Kanya"),
            new Bag(sportBag, "TG1005", 29.5f, "Suriya"),
        };
        
        for (Bag b : bagList) {
            QINSERT(b);
        }
        
        // Background image
        Image bgImage = null;
        
        // Set background image
        try {
            bgImage = new ImageIcon(getClass().getResource("/images/airport_bg.jpg")).getImage();
            
        } catch (Exception e) {
            System.err.println("Error: Background image not found. Please check the file path.");
        }
        
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgImage);
        setContentPane(backgroundPanel);
        
        Image icon = null;
        // Set window icon image
        try {
            icon = new ImageIcon(getClass().getResource("/images/icon3.png"))
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Error: Window icon image not found. Please check the file path.");
        }

        setIconImage(icon);
        initComponents();
        getContentPane().setLayout(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        popupLabel.setFont(new Font("MS Reference Sans Serif", Font.PLAIN, 14));
        popupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        popupLabel.setVerticalAlignment(SwingConstants.CENTER);
        popupLabel.setBackground(Color.WHITE);
        popupLabel.setOpaque(false);
        popupLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        infoPopup.setBorder(null);
        infoPopup.add(popupLabel);
        infoPopup.setBackground(Color.WHITE);
        infoPopup.setOpaque(false);
        infoPopup.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // Add mouse listeners for each JLabel
        addHoverListener(jLabel1, 1);
        addHoverListener(jLabel2, 2);
        addHoverListener(jLabel3, 3);
        addHoverListener(jLabel4, 4);
        addHoverListener(jLabel5, 5);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        rPanel1 = new queue_project1.RPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        rPanel2 = new queue_project1.RPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TG1001.png"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TG1002.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TG1003.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TG1004.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/TG1005.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel5MouseEntered(evt);
            }
        });

        rPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 48)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Scenario 1");
        jLabel6.setToolTipText("Scenario 1");

        jLabel7.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("<html> Let's say that you would like to delete 2 bags<br>from queue, and insert 2 more bags into queue. </html>");

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Press Here!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout rPanel1Layout = new javax.swing.GroupLayout(rPanel1);
        rPanel1.setLayout(rPanel1Layout);
        rPanel1Layout.setHorizontalGroup(
            rPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rPanel1Layout.createSequentialGroup()
                .addGroup(rPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rPanel1Layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rPanel1Layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        rPanel1Layout.setVerticalGroup(
            rPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        rPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jLabel8.setText("Monitor");

        jLabel9.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel9.setText("FRONT =");

        jLabel10.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel10.setText("REAR  =");

        jLabel11.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel11.setText("N        =");

        jLabel12.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel12.setText("-1");

        jLabel13.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel13.setText("-1");

        jLabel14.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel14.setText("4");

        jLabel15.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel15.setText("Size    =");

        jLabel16.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        jLabel16.setText("5");

        javax.swing.GroupLayout rPanel2Layout = new javax.swing.GroupLayout(rPanel2);
        rPanel2.setLayout(rPanel2Layout);
        rPanel2Layout.setHorizontalGroup(
            rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rPanel2Layout.createSequentialGroup()
                        .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(rPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(rPanel2Layout.createSequentialGroup()
                        .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rPanel2Layout.createSequentialGroup()
                                .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        rPanel2Layout.setVerticalGroup(
            rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(rPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(326, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(302, 302, 302))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(130, 130, 130))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        // TODO add your handling code here:
        showBagInfoPopup(jLabel1, 1);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
        showBagInfoPopup(jLabel2, 2);
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
        showBagInfoPopup(jLabel3, 3);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        showBagInfoPopup(jLabel4, 4);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseEntered
        // TODO add your handling code here:
        showBagInfoPopup(jLabel5, 5);
    }//GEN-LAST:event_jLabel5MouseEntered

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        clickCount++;

        if (clickCount == 1) {
            // Run scenario 1 on first click
            scenario1();
        } 
        else if (clickCount == 2) {
            // Run scenario 2 on second click
            scenario2();
        }
        else if (clickCount == 3) {
            // Run scenario 3 on third click
            scenario3();
        }
        else if (clickCount == 4) {
            // Run scenario 4 on fourth click
            scenario4();
        } 
        else {
            // After all scenarios are done
            JOptionPane.showMessageDialog(this,
                "<html><span style='font-family:MS Reference Sans Serif; font-size:12pt;'>All scenarios completed!</span></html>",
                "End of Simulation",
                JOptionPane.INFORMATION_MESSAGE);

            jButton1.setEnabled(false);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new HW3_QUEUE2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private queue_project1.RPanel rPanel1;
    private queue_project1.RPanel rPanel2;
    // End of variables declaration//GEN-END:variables
}
