/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package college_fee_management;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Dell
 */
public class UpdateFeesDetails extends javax.swing.JFrame {

    /**
     * Creates new form AddFees
     */
    public UpdateFeesDetails() {
        initComponents();
        displayCashfirst();
        fillComboBox();
        
        int receiptNo = getReceiptNo();
        txt_receiptNo.setText(Integer.toString(receiptNo));
        
        setRecords();
    }
    public void displayCashfirst()
    {
    lbl_ddno.setVisible(false);
    lbl_chequeno.setVisible(false);
    lbl_bankname.setVisible(false);
    
    txt_dd_no.setVisible(false);
    txt_cheque_no.setVisible(false);
    txt_bankname.setVisible(false);
    
    }
    public boolean validation(){
        if(txt_receivedfrom.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Student Name");
        return false;
    }
        
        if(datechooser.getDate()== null){
            JOptionPane.showMessageDialog(this,"Please select the date");
        return false;
        }
        if(txt_amount.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Amount ( In numbers)");
        return false;
         }
        if (combo_paymentmode.getSelectedItem().toString().equalsIgnoreCase("cheque")){
          if(txt_cheque_no.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Cheque No.");
        return false;
          }
        if(txt_bankname.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Bank Name.");
        return false;
         } 
        }
           if (combo_paymentmode.getSelectedItem().toString().equalsIgnoreCase("dd")){ 
            if(txt_dd_no.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter DD No.");
            return false;
            } 
            if(txt_bankname.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Bank Name.");
        return false;
        }
           }
           if (combo_paymentmode.getSelectedItem().toString().equalsIgnoreCase("card")){ 
            if(txt_bankname.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Please Enter Bank Name.");
        return false;
        }
           }
    return true;
    
           }
        
    
    
    public void fillComboBox(){
    try{
         Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
     Connection con=(Connection) DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","root");
    PreparedStatement pst=con.prepareStatement("select cname from course");
    ResultSet rs=pst.executeQuery();
    while (rs.next()){
        combo_course.addItem(rs.getString("cname"));
        
        
        }
    }catch(ClassNotFoundException | SQLException e){
        e.toString();
        System.out.println(e.getMessage());
      }
    }
    public int getReceiptNo(){
    int receiptNo = 0;
    try{
        Connection con =DBConnection.getConnection();
         PreparedStatement pst=con.prepareStatement("select max(reciept_no) from fees_Details");
        ResultSet rs= pst.executeQuery();
        if(rs.next()==true){
         receiptNo =   rs.getInt(1);
        }
        
    }catch (SQLException e) {
    }
    return receiptNo+1;
    }
    
    public String updateData(){
  
        String status = "";
       
int recieptNo = Integer.parseInt(txt_receiptNo.getText());
String studentName = txt_receivedfrom.getText();
int rollNo= Integer.parseInt(txt_roll_no.getText()); 
String paymentMode = combo_paymentmode.getSelectedItem().toString();
String chequeNo = txt_cheque_no.getText();
String bankName = txt_bankname.getText();
String ddNo = txt_dd_no.getText();
String courseName = txt_coursename.getText();
float totalAmount = Float.parseFloat(txt_total.getText()); 
SimpleDateFormat dateFormat = new SimpleDateFormat ("YYYY-MM-dd");
String date = dateFormat.format (datechooser.getDate());
float initialAmount = Float.parseFloat(txt_amount.getText());
String totalInWords=txt_total_in_word.getText();
String remark = txt_remark.getText();
int year1 = Integer.parseInt(txt_year1.getText());
int year2= Integer.parseInt(txt_year2.getText());
    
try{
        Connection con =DBConnection.getConnection();
         PreparedStatement pst = con.prepareStatement("update fees_details set student_name=?,roll_no=?,payment_mode=?,"
                 + "cheque_no=?,bank_name=?,dd_no=?,course_name=?,total_amount=?,date=?,"
                 + "amount=?,total_in_words=?,remark=?,year1=?,year2=? where reciept_no=?");
        
        pst.setString(1,studentName);
        pst.setInt(2,rollNo);
        pst.setString(3,paymentMode);
        pst.setString(4,chequeNo);
        pst.setString(5,bankName);
        pst.setString(6,ddNo);
        pst.setString(7,courseName);
    pst.setFloat(8,totalAmount);
        pst.setString(9,date);
        pst.setFloat(10,initialAmount);
        pst.setString(11,totalInWords);
    pst.setString(12,remark);
    pst.setInt(13,year1);
        pst.setInt(14, year2);
        pst.setInt(15,recieptNo);

        
        
    int rowCount = pst.executeUpdate();
    if (rowCount == 1){
        status = "success";
    }else{
        status="failed";
    }
}catch (SQLException e){
}
    return status;
   }
     public void setRecords(){
    try{
         Connection con =DBConnection.getConnection();
    PreparedStatement pst=con.prepareStatement("select * from fees_details order by reciept_no desc fetch first 1 rows only");
    ResultSet rs=pst.executeQuery();
    rs.next();
    txt_receiptNo.setText(rs.getString("reciept_no"));
    datechooser.setDate(rs.getDate("date"));
    combo_paymentmode.setSelectedItem(rs.getString("payment_mode"));
    txt_cheque_no.setText(rs.getString("cheque_no"));
    txt_dd_no.setText (rs.getString("dd_no"));
    txt_bankname.setText(rs.getString("bank_name"));
    txt_receivedfrom.setText (rs.getString("student_name"));
    txt_year1.setText(rs.getString("year1"));
    txt_year2.setText(rs.getString("year2"));
    combo_course.setSelectedItem(rs.getString("course_name"));
    txt_roll_no.setText (rs.getString("roll_no"));
    txt_amount.setText(rs.getString("amount"));
    txt_total.setText(rs.getString("total_amount"));
    txt_total_in_word.setText (rs.getString("total_in_words"));
    txt_coursename.setText (rs.getString("course_name"));
    txt_remark.setText(rs.getString("remark"));
    
        }catch(SQLException e){
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

        panelsideBar = new javax.swing.JPanel();
        panelHome5 = new javax.swing.JPanel();
        btnhome5 = new javax.swing.JLabel();
        panelHome = new javax.swing.JPanel();
        btnhome = new javax.swing.JLabel();
        panelHome1 = new javax.swing.JPanel();
        btnhome1 = new javax.swing.JLabel();
        panelHome2 = new javax.swing.JPanel();
        btnhome2 = new javax.swing.JLabel();
        panelHome3 = new javax.swing.JPanel();
        btnhome3 = new javax.swing.JLabel();
        panelHome6 = new javax.swing.JPanel();
        btnhome6 = new javax.swing.JLabel();
        panelParent = new javax.swing.JPanel();
        lbl_chequeno = new javax.swing.JLabel();
        lbl_receiptno = new javax.swing.JLabel();
        lbl_ddno = new javax.swing.JLabel();
        lbl_bankname = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        lbl_paymentmode = new javax.swing.JLabel();
        combo_paymentmode = new javax.swing.JComboBox<>();
        txt_receiptNo = new javax.swing.JTextField();
        txt_cheque_no = new javax.swing.JTextField();
        datechooser = new com.toedter.calendar.JDateChooser();
        panelchild = new javax.swing.JPanel();
        lbl_receivedfrom = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_year1 = new javax.swing.JTextField();
        txt_year2 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lbl_enrollementno = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        combo_course = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_course = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_receivedfrom = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txt_total_in_word = new javax.swing.JTextField();
        txt_coursename = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_remark = new javax.swing.JTextArea();
        txt_total = new javax.swing.JTextField();
        txt_amount = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        btn_print = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txt_roll_no = new javax.swing.JTextField();
        txt_bankname = new javax.swing.JTextField();
        txt_dd_no = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelsideBar.setBackground(new java.awt.Color(0, 102, 102));
        panelsideBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelHome5.setBackground(new java.awt.Color(0, 102, 102));
        panelHome5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome5.setForeground(new java.awt.Color(255, 255, 255));
        panelHome5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome5.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome5.setForeground(new java.awt.Color(255, 255, 255));
        btnhome5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/back-button.png"))); // NOI18N
        btnhome5.setText("Back");
        btnhome5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhome5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome5MouseExited(evt);
            }
        });
        panelHome5.add(btnhome5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 280, -1));

        panelsideBar.add(panelHome5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 570, 330, 70));

        panelHome.setBackground(new java.awt.Color(0, 102, 102));
        panelHome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome.setForeground(new java.awt.Color(255, 255, 255));
        panelHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome.setForeground(new java.awt.Color(255, 255, 255));
        btnhome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/logout.png"))); // NOI18N
        btnhome.setText("  Logout");
        btnhome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhomeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhomeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhomeMouseExited(evt);
            }
        });
        panelHome.add(btnhome, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 280, -1));

        panelsideBar.add(panelHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 690, 330, 70));

        panelHome1.setBackground(new java.awt.Color(0, 102, 102));
        panelHome1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome1.setForeground(new java.awt.Color(255, 255, 255));
        panelHome1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome1.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome1.setForeground(new java.awt.Color(255, 255, 255));
        btnhome1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/home.png"))); // NOI18N
        btnhome1.setText("  HOME");
        btnhome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhome1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome1MouseExited(evt);
            }
        });
        panelHome1.add(btnhome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 280, -1));

        panelsideBar.add(panelHome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 330, 70));

        panelHome2.setBackground(new java.awt.Color(0, 102, 102));
        panelHome2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome2.setForeground(new java.awt.Color(255, 255, 255));
        panelHome2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome2.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome2.setForeground(new java.awt.Color(255, 255, 255));
        btnhome2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/search2.png"))); // NOI18N
        btnhome2.setText("Search Record");
        btnhome2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhome2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome2MouseExited(evt);
            }
        });
        panelHome2.add(btnhome2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 280, -1));

        panelsideBar.add(panelHome2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 330, 70));

        panelHome3.setBackground(new java.awt.Color(0, 102, 102));
        panelHome3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome3.setForeground(new java.awt.Color(255, 255, 255));
        panelHome3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome3.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome3.setForeground(new java.awt.Color(255, 255, 255));
        btnhome3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/edit2.png"))); // NOI18N
        btnhome3.setText("Edit Course");
        btnhome3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome3MouseExited(evt);
            }
        });
        panelHome3.add(btnhome3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 320, -1));

        panelsideBar.add(panelHome3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 330, 70));

        panelHome6.setBackground(new java.awt.Color(0, 102, 102));
        panelHome6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome6.setForeground(new java.awt.Color(255, 255, 255));
        panelHome6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome6.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome6.setForeground(new java.awt.Color(255, 255, 255));
        btnhome6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/view all record.png"))); // NOI18N
        btnhome6.setText("View All Record");
        btnhome6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhome6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome6MouseExited(evt);
            }
        });
        panelHome6.add(btnhome6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 280, -1));

        panelsideBar.add(panelHome6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 470, 330, 70));

        getContentPane().add(panelsideBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 1040));

        panelParent.setBackground(new java.awt.Color(0, 153, 153));
        panelParent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_chequeno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_chequeno.setText("Cheque No. :");
        panelParent.add(lbl_chequeno, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        lbl_receiptno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_receiptno.setText("Receipt no : SGP-");
        panelParent.add(lbl_receiptno, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        lbl_ddno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_ddno.setText("DD no. :");
        panelParent.add(lbl_ddno, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        lbl_bankname.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_bankname.setText("Bank Name :");
        panelParent.add(lbl_bankname, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        lbl_date.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_date.setText("Date :");
        panelParent.add(lbl_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 20, -1, -1));

        lbl_paymentmode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_paymentmode.setText("Mode Of Payment :");
        panelParent.add(lbl_paymentmode, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        combo_paymentmode.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_paymentmode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DD", "Cheque", "Cash", "Card" }));
        combo_paymentmode.setSelectedIndex(2);
        combo_paymentmode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_paymentmodeActionPerformed(evt);
            }
        });
        panelParent.add(combo_paymentmode, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 150, -1));

        txt_receiptNo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_receiptNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_receiptNoActionPerformed(evt);
            }
        });
        panelParent.add(txt_receiptNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 60, -1));

        txt_cheque_no.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_cheque_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cheque_noActionPerformed(evt);
            }
        });
        panelParent.add(txt_cheque_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 150, -1));
        panelParent.add(datechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, 140, 30));

        panelchild.setBackground(new java.awt.Color(0, 153, 153));
        panelchild.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_receivedfrom.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_receivedfrom.setText("Received from :");
        panelchild.add(lbl_receivedfrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, 20));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("The following payment in the college office for the year ");
        panelchild.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        txt_year1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_year1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year1ActionPerformed(evt);
            }
        });
        panelchild.add(txt_year1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 70, -1));

        txt_year2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_year2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_year2ActionPerformed(evt);
            }
        });
        panelchild.add(txt_year2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 60, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("to");
        panelchild.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 60, -1, -1));

        lbl_enrollementno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_enrollementno.setText("Enrollment No. :");
        panelchild.add(lbl_enrollementno, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 110, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Amount(Rs)");
        panelchild.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 220, -1, -1));

        combo_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseActionPerformed(evt);
            }
        });
        panelchild.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, 290, -1));
        panelchild.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 590, 240, 30));
        panelchild.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 1270, 30));

        lbl_course.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_course.setText("Course :");
        panelchild.add(lbl_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setText("Sr.No.");
        panelchild.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Heads");
        panelchild.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 220, -1, -1));

        txt_receivedfrom.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_receivedfrom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_receivedfromActionPerformed(evt);
            }
        });
        panelchild.add(txt_receivedfrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, 410, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Total in words :");
        panelchild.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 470, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Reciever Signature");
        panelchild.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 600, -1, -1));

        txt_total_in_word.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_total_in_word.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_total_in_wordActionPerformed(evt);
            }
        });
        panelchild.add(txt_total_in_word, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 460, 470, -1));

        txt_coursename.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_coursename.setText("    ");
        txt_coursename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_coursenameActionPerformed(evt);
            }
        });
        panelchild.add(txt_coursename, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 290, 370, -1));

        txt_remark.setColumns(20);
        txt_remark.setFont(new java.awt.Font("Monospaced", 0, 17)); // NOI18N
        txt_remark.setRows(5);
        jScrollPane1.setViewportView(txt_remark);

        panelchild.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 540, 460, 70));

        txt_total.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });
        panelchild.add(txt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 460, 230, -1));

        txt_amount.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_amountActionPerformed(evt);
            }
        });
        panelchild.add(txt_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 290, 230, -1));
        panelchild.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 1270, 30));
        panelchild.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 440, 390, 30));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Total -");
        panelchild.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 470, -1, -1));

        btn_print.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        panelchild.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 680, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Remark :");
        panelchild.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 560, -1, -1));

        txt_roll_no.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        panelchild.add(txt_roll_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 100, 120, 30));

        panelParent.add(panelchild, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 1310, 800));

        txt_bankname.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_bankname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_banknameActionPerformed(evt);
            }
        });
        panelParent.add(txt_bankname, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 420, -1));

        txt_dd_no.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_dd_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dd_noActionPerformed(evt);
            }
        });
        panelParent.add(txt_dd_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, 150, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Update Fees");
        panelParent.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, -1, -1));
        panelParent.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 50, 340, 20));

        getContentPane().add(panelParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 1310, 1040));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnhomeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseEntered
 Color clr=new Color(0,153,153);
       panelHome.setBackground(clr);
    }//GEN-LAST:event_btnhomeMouseEntered

    private void btnhomeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseExited
 Color clr=new Color(0,102,102);
       panelHome.setBackground(clr);
    }//GEN-LAST:event_btnhomeMouseExited

    private void btnhome1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome1MouseEntered
        Color clr=new Color(0,153,153);
       panelHome1.setBackground(clr);
    }//GEN-LAST:event_btnhome1MouseEntered

    private void btnhome1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome1MouseExited
       Color clr=new Color(0,102,102);
       panelHome1.setBackground(clr);
    }//GEN-LAST:event_btnhome1MouseExited

    private void btnhome2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome2MouseEntered
     Color clr=new Color(0,153,153);
       panelHome2.setBackground(clr);
    }//GEN-LAST:event_btnhome2MouseEntered

    private void btnhome2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome2MouseExited
      Color clr=new Color(0,102,102);
       panelHome2.setBackground(clr);
    }//GEN-LAST:event_btnhome2MouseExited

    private void btnhome3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome3MouseEntered
       Color clr=new Color(0,153,153);
       panelHome3.setBackground(clr);
    }//GEN-LAST:event_btnhome3MouseEntered

    private void btnhome3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome3MouseExited
        Color clr=new Color(0,102,102);
       panelHome3.setBackground(clr);
    }//GEN-LAST:event_btnhome3MouseExited

    private void btnhome5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome5MouseEntered
       Color clr=new Color(0,153,153);
       panelHome5.setBackground(clr);
    }//GEN-LAST:event_btnhome5MouseEntered

    private void btnhome5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome5MouseExited
        Color clr=new Color(0,102,102);
       panelHome5.setBackground(clr);
    }//GEN-LAST:event_btnhome5MouseExited

    private void btnhome6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome6MouseEntered
       Color clr=new Color(0,153,153);
       panelHome6.setBackground(clr);
    }//GEN-LAST:event_btnhome6MouseEntered

    private void btnhome6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome6MouseExited
        Color clr=new Color(0,102,102);
       panelHome6.setBackground(clr);
    }//GEN-LAST:event_btnhome6MouseExited

    private void btnhome5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome5MouseClicked
Home a=new Home();
       a.setVisible(true);
       this.dispose();  
    }//GEN-LAST:event_btnhome5MouseClicked

    private void txt_receivedfromActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_receivedfromActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_receivedfromActionPerformed

    private void txt_receiptNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_receiptNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_receiptNoActionPerformed

    private void txt_cheque_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cheque_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cheque_noActionPerformed

    private void txt_year1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year1ActionPerformed

    private void txt_year2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_year2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_year2ActionPerformed

    private void txt_banknameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_banknameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_banknameActionPerformed

    private void txt_total_in_wordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_total_in_wordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_total_in_wordActionPerformed

    private void txt_coursenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_coursenameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_coursenameActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void txt_amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_amountActionPerformed
   float  amnt = Float.parseFloat(txt_amount.getText());
   float total = amnt;
   txt_total.setText(Float.toString(total));
   txt_total_in_word.setText(NumberToWordsConverter.convert((int)total));
    }//GEN-LAST:event_txt_amountActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
if (validation()==true);
String result = updateData();
if(result.equals("success")){
    JOptionPane.showMessageDialog(this,"Record updated succesfully");
    PrintReceipt p = new PrintReceipt();
    p.setVisible(true);
    this.dispose();
    }else{JOptionPane.showMessageDialog(this,"Record updation failed");
           displayCashfirst();
    }//GEN-LAST:event_btn_printActionPerformed
    }
    private void txt_dd_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dd_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dd_noActionPerformed

    private void combo_paymentmodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_paymentmodeActionPerformed
      if (combo_paymentmode.getSelectedIndex()== 0){
          lbl_ddno.setVisible(true);
          txt_dd_no.setVisible(true);
          lbl_chequeno.setVisible(false);
          txt_cheque_no.setVisible(false);
          lbl_bankname.setVisible(true);
          txt_bankname.setVisible(true);
      }
      if (combo_paymentmode.getSelectedIndex()== 1){
          lbl_ddno.setVisible(false);
          txt_dd_no.setVisible(false);
          lbl_chequeno.setVisible(true);
          txt_cheque_no.setVisible(true);
          lbl_bankname.setVisible(true);
          txt_bankname.setVisible(true);
      }
       if (combo_paymentmode.getSelectedIndex()== 2){
          lbl_ddno.setVisible(false);
          txt_dd_no.setVisible(false);
          lbl_chequeno.setVisible(false);
          txt_cheque_no.setVisible(false);
          lbl_bankname.setVisible(false);
          txt_bankname.setVisible(false);
       }
      if (combo_paymentmode.getSelectedItem().equals("Card")){
          lbl_ddno.setVisible(false);
          txt_dd_no.setVisible(false);
          lbl_chequeno.setVisible(false);
          txt_cheque_no.setVisible(false);
          lbl_bankname.setVisible(true);
          txt_bankname.setVisible(true);
    }//GEN-LAST:event_combo_paymentmodeActionPerformed
    }
    private void combo_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseActionPerformed
   txt_coursename.setText(combo_course.getSelectedItem().toString());
    }//GEN-LAST:event_combo_courseActionPerformed

    private void btnhome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome1MouseClicked
       Home a=new Home();
       a.setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnhome1MouseClicked

    private void btnhome2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome2MouseClicked
    SearchRecord a=new SearchRecord();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnhome2MouseClicked

    private void btnhomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhomeMouseClicked
Login_page a= new Login_page();
      a.setVisible(true);
      this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnhomeMouseClicked

    private void btnhome6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome6MouseClicked
Home a=new Home();
       a.setVisible(true);
       this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnhome6MouseClicked
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UpdateFeesDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UpdateFeesDetails().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_print;
    private javax.swing.JLabel btnhome;
    private javax.swing.JLabel btnhome1;
    private javax.swing.JLabel btnhome2;
    private javax.swing.JLabel btnhome3;
    private javax.swing.JLabel btnhome5;
    private javax.swing.JLabel btnhome6;
    private javax.swing.JComboBox<String> combo_course;
    private javax.swing.JComboBox<String> combo_paymentmode;
    private com.toedter.calendar.JDateChooser datechooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lbl_bankname;
    private javax.swing.JLabel lbl_chequeno;
    private javax.swing.JLabel lbl_course;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_ddno;
    private javax.swing.JLabel lbl_enrollementno;
    private javax.swing.JLabel lbl_paymentmode;
    private javax.swing.JLabel lbl_receiptno;
    private javax.swing.JLabel lbl_receivedfrom;
    private javax.swing.JPanel panelHome;
    private javax.swing.JPanel panelHome1;
    private javax.swing.JPanel panelHome2;
    private javax.swing.JPanel panelHome3;
    private javax.swing.JPanel panelHome5;
    private javax.swing.JPanel panelHome6;
    private javax.swing.JPanel panelParent;
    private javax.swing.JPanel panelchild;
    private javax.swing.JPanel panelsideBar;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_bankname;
    private javax.swing.JTextField txt_cheque_no;
    private javax.swing.JTextField txt_coursename;
    private javax.swing.JTextField txt_dd_no;
    private javax.swing.JTextField txt_receiptNo;
    private javax.swing.JTextField txt_receivedfrom;
    private javax.swing.JTextArea txt_remark;
    private javax.swing.JTextField txt_roll_no;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total_in_word;
    private javax.swing.JTextField txt_year1;
    private javax.swing.JTextField txt_year2;
    // End of variables declaration//GEN-END:variables
}
