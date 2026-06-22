/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package stack1;
import java.awt.Font;
import java.awt.Image;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author User
 */
public class hw_stack2 extends javax.swing.JFrame {
    
    // --- Stack declarations ---
    Stack<Integer> STACKA = new Stack<>();
    Stack<Integer> STACKB = new Stack<>();
    Stack<Integer> STACKC = new Stack<>();

    int ITEM_A, ITEM_B, ITEM_C;
    static int SIZE; // Max stack size

    // --- Stack helpers ---
    public boolean isEmpty(String whichStack) {
        switch (whichStack) {
            case "A": return STACKA.isEmpty();
            case "B": return STACKB.isEmpty();
            case "C": return STACKC.isEmpty();
            default: return true;
        }
    }

    public boolean isFull(String whichStack) {
        switch (whichStack) {
            case "A": return STACKA.size() >= SIZE;
            case "B": return STACKB.size() >= SIZE;
            case "C": return STACKC.size() >= SIZE;
            default: return true;
        }
    }

    // --- Push operation ---
    public boolean Push(String whichStack) {
        try {
            switch (whichStack) {
                case "A":
                    STACKA.push(ITEM_A);
                    updateTable(jTable4, STACKA, "A");
                    return true;
                case "B":
                    STACKB.push(ITEM_B);
                    updateTable(jTable5, STACKB, "B");
                    return true;
                case "C":
                    STACKC.push(ITEM_C);
                    updateTable(jTable6, STACKC, "C");
                    return true;
                default:
                    JOptionPane.showMessageDialog(this,
                        "Invalid stack name: " + whichStack,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Unexpected error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // --- Pop operation ---
    public boolean Pop(String whichStack) {
        try {
            switch (whichStack) {
                case "A":
                    ITEM_A = STACKA.pop();
                    updateTable(jTable4, STACKA, "A");
                    return true;
                case "B":
                    ITEM_B = STACKB.pop();
                    updateTable(jTable5, STACKB, "B");
                    return true;
                case "C":
                    ITEM_C = STACKC.pop();
                    updateTable(jTable6, STACKC, "C");
                    return true;
                default:
                    JOptionPane.showMessageDialog(this,
                        "Invalid stack name: " + whichStack,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Stack underflow or error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // --- Update JTable to reflect stack contents ---
    private void updateTable(javax.swing.JTable table, Stack<Integer> stack, String name) {
        for (int i = 0; i < SIZE; i++) {
            int row = SIZE - 1 - i;
            if (i < stack.size()) {
                table.setValueAt(stack.get(i), row, 0);
            } else {
                table.setValueAt(null, row, 0);
            }
        }
    }

    // --- Push and validate ---
    public void pushAndCheck(String whichStack) {
        Stack<Integer> stack = null;
        int currentItem = 0;

        switch (whichStack) {
            case "A": stack = STACKA; currentItem = ITEM_A; break;
            case "B": stack = STACKB; currentItem = ITEM_B; break;
            case "C": stack = STACKC; currentItem = ITEM_C; break;
        }

        if (stack != null && !stack.isEmpty() && currentItem > stack.peek()) {
            JOptionPane.showMessageDialog(this,
                "ITEM (" + currentItem + ") is greater than top of stack (" + stack.peek() + ").\n"
                + "Please insert a smaller number.",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = Push(whichStack);
        if (success) {
            JOptionPane.showMessageDialog(this,
                currentItem + " was pushed successfully to Stack " + whichStack,
                "Push Successful",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to push " + currentItem + " to Stack " + whichStack + ".",
                "Push Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
        private void setupTable(javax.swing.JTable table, String title, int size) {
            DefaultTableModel model = new DefaultTableModel(new Object[size][1], new String[]{title}) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return Integer.class;
                }
            };
            table.setModel(model);

            JTableHeader header = table.getTableHeader();
            header.setFont(new Font("Monospaced", Font.PLAIN, 14));
            DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
        }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(hw_stack2.class.getName());

    /**
     * Creates new form hw_stack1
     */
    public hw_stack2(int size) {
        
        SIZE = size;
        
        // Background image
        Image bgImage = null;
        
        // Set background image
        try {
            bgImage = new ImageIcon(getClass().getResource("/stack1/colorful-bg.jpg")).getImage();
            
        } catch (Exception e) {
            System.err.println("Error: Background image not found. Please check the file path.");
        }
        
        BackgroundPanel backgroundPanel = new BackgroundPanel(bgImage);
        setContentPane(backgroundPanel);
        
        Image icon = null;
        // Set window icon image
        try {
            icon = new ImageIcon(getClass().getResource("/stack1/icon2.png"))
                    .getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);;
        } catch (Exception e) {
            System.err.println("Error: Window icon image not found. Please check the file path.");
        }
        
        setIconImage(icon);
        initComponents();
        setTitle("Reverse Number Stack Simulation");
        
        setupTable(jTable4, "Stack A", SIZE);
        setupTable(jTable5, "Stack B", SIZE);
        setupTable(jTable6, "Stack C", SIZE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        roundPanel1 = new stack1.RoundPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        roundPanel2 = new stack1.RoundPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        roundPanel3 = new stack1.RoundPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        roundPanel4 = new stack1.RoundPanel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        roundPanel5 = new stack1.RoundPanel();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        roundPanel6 = new stack1.RoundPanel();
        jButton4 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Stack A"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        roundPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable5.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Stack B"
            }
        ));
        jScrollPane5.setViewportView(jTable5);

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable6.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Stack C"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton1.setText("Push");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jButton11.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton11.setText("<--");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton12.setText("-->");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton13.setText("<--");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton14.setText("-->");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        roundPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton3.setText("Push");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        roundPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jButton4.setText("Push");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(roundPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(roundPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(roundPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(roundPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roundPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roundPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(jButton12))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(136, 136, 136)
                                .addComponent(jButton14)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(jButton11))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addComponent(jButton13)))))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            ITEM_A = Integer.parseInt(jTextField1.getText());

            if (isFull("A")) {
                JOptionPane.showMessageDialog(this,
                    "Stack A Overflow! Cannot push more items.",
                    "Stack Overflow",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            pushAndCheck("A");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter an integer for Stack A.",
                "Number Format Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Unexpected error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            ITEM_B = Integer.parseInt(jTextField2.getText());

            if (isFull("B")) {
                JOptionPane.showMessageDialog(this,
                    "Stack B Overflow! Cannot push more items.",
                    "Stack Overflow",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            pushAndCheck("B");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter an integer for Stack B.",
                "Number Format Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Unexpected error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            ITEM_C = Integer.parseInt(jTextField3.getText());

            if (isFull("C")) {
                JOptionPane.showMessageDialog(this,
                    "Stack C Overflow! Cannot push more items.",
                    "Stack Overflow",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            pushAndCheck("C");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid input! Please enter an integer for Stack C.",
                "Number Format Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Unexpected error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        // Move top item from Stack A to Stack B
        if (isEmpty("A")) {
        JOptionPane.showMessageDialog(this,
            "Stack A Underflow! No item to move.",
            "Stack Underflow",
            JOptionPane.ERROR_MESSAGE);
        return;
        }
        if (isFull("B")) {
            JOptionPane.showMessageDialog(this,
                "Stack B Overflow! Cannot move item.",
                "Stack Overflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        ITEM_B = STACKA.peek();  // Copy top value before popping
        Pop("A");
        pushAndCheck("B");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        // Move top item from Stack B to Stack C
                if (isEmpty("B")) {
            JOptionPane.showMessageDialog(this,
                "Stack B Underflow! No item to move.",
                "Stack Underflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isFull("C")) {
            JOptionPane.showMessageDialog(this,
                "Stack C Overflow! Cannot move item.",
                "Stack Overflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        ITEM_C = STACKB.peek();
        Pop("B");
        pushAndCheck("C");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        // Move top item from Stack B to Stack A
        if (isEmpty("B")) {
            JOptionPane.showMessageDialog(this,
                "Stack B Underflow! No item to move.",
                "Stack Underflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isFull("A")) {
            JOptionPane.showMessageDialog(this,
                "Stack A Overflow! Cannot move item.",
                "Stack Overflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        ITEM_A = STACKB.peek();
        Pop("B");
        pushAndCheck("A");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        // Move top item from Stack C to Stack B
        if (isEmpty("C")) {
            JOptionPane.showMessageDialog(this,
                "Stack C Underflow! No item to move.",
                "Stack Underflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isFull("B")) {
            JOptionPane.showMessageDialog(this,
                "Stack B Overflow! Cannot move item.",
                "Stack Overflow",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        ITEM_B = STACKC.peek();
        Pop("C");
        pushAndCheck("B");
    }//GEN-LAST:event_jButton13ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new hw_stack2(SIZE).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTable jTable6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private stack1.RoundPanel roundPanel1;
    private stack1.RoundPanel roundPanel2;
    private stack1.RoundPanel roundPanel3;
    private stack1.RoundPanel roundPanel4;
    private stack1.RoundPanel roundPanel5;
    private stack1.RoundPanel roundPanel6;
    // End of variables declaration//GEN-END:variables
}
