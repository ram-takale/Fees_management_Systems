/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package college_fee_management;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static org.apache.poi.hemf.hemfplus.record.HemfPlusRecordType.object;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Dell
 */
public class GenerateReport extends javax.swing.JFrame {

    /**
     * Creates new form GenerateReport
     */
    DefaultTableModel model;
    public GenerateReport() {
        initComponents();
        fillComboBox();
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
public void setRecordsToTable(){
    String cname = combo_course.getSelectedItem().toString();
    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    String fromDate = dateFormat.format(dateChooser_from.getDate());
    String toDate = dateFormat.format(dateChooser_to.getDate());
    
    Float amountTotal = 0.0f;
try{
    Connection con =DBConnection.getConnection();
    PreparedStatement pst =con.prepareStatement("select * from fees_details where date between ? and ? and course_name = ?");
   pst.setString(1,fromDate);
   pst.setString(2,toDate);
   pst.setString(3,cname);
    ResultSet rs =pst.executeQuery();
    
    while(rs.next()){
        
    String recieptNo=rs.getString("reciept_no");
    String rollNo =rs.getString("roll_no");
    String studentname =rs.getString("student_name");
    String courseName =rs.getString("course_name");
    float amount =rs.getFloat("total_amount");
    String reMark =rs.getString("remark");
    
    amountTotal = amountTotal + amount;
    
    Object[] obj = {recieptNo,rollNo,studentname,courseName,amount,reMark};
    model=(DefaultTableModel)tbl_feesdetails.getModel();
    model.addRow(obj);
    }

 lbl_course.setText(cname);
    lbl_totalamount.setText(amountTotal.toString());
    lbl_totalinwords.setText(NumberToWordsConverter.convert(amountTotal.intValue()));
    
    
}catch(Exception e){
    e.printStackTrace();
    }
}
    public void clearTable(){
        DefaultTableModel model = (DefaultTableModel)tbl_feesdetails.getModel();
        model.setRowCount(1);
    }
public void ExportToExcel(){
XSSFWorkbook wb = new XSSFWorkbook();
XSSFSheet ws = wb.createSheet();
DefaultTableModel model = (DefaultTableModel)tbl_feesdetails.getModel();
TreeMap<String,Object[]> map = new TreeMap<>();

    map.put("0",new Object[]{model.getColumnName(0),model.getColumnName(1),model.getColumnName(2),model.getColumnName(3),
    model.getColumnName(4),model.getColumnName(5),});

    for(int i=1; i<model.getRowCount(); i++){
        
        map.put(Integer.toString(i),new Object[]{model.getValueAt(i,0),model.getValueAt(i,1),model.getValueAt(i,2),model.getValueAt(i,3),
        model.getValueAt(i,4),model.getValueAt(i,5),});
    }
for (Map.Entry<String,Object[]>entry : map.entrySet()){
String key = entry.getKey();
Object[] value = entry.getValue();
    System.out.println(Arrays.toString(value));
}
   Set<String> id = map.keySet();
  
   XSSFRow fRow;
   
   int rowId = 0;
   
   for(String key : id){
   fRow = ws.createRow(rowId++);
   
   Object[] value = map.get(key);
   int cellId = 0;
  
   for (Object object : value){
       
       XSSFCell cell  = fRow.createCell(cellId++);
       cell.setCellValue(object.toString());
   }
   }
  
   try
   ( FileOutputStream fos = new FileOutputStream(new File(txt_filepath.getText()))){
   wb.write(fos);
 JOptionPane.showMessageDialog(this,"file exported successfully :"+txt_filepath.getText());
   }
   catch (Exception e){
   e.printStackTrace();
   }
   
   
}   
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelsideBar1 = new javax.swing.JPanel();
        panelHome6 = new javax.swing.JPanel();
        btnhome6 = new javax.swing.JLabel();
        panelHome7 = new javax.swing.JPanel();
        btnhome7 = new javax.swing.JLabel();
        panelHome1 = new javax.swing.JPanel();
        btnhome1 = new javax.swing.JLabel();
        panelHome2 = new javax.swing.JPanel();
        btnhome2 = new javax.swing.JLabel();
        panelHome3 = new javax.swing.JPanel();
        btnhome3 = new javax.swing.JLabel();
        panelHome5 = new javax.swing.JPanel();
        btnhome5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        combo_course = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dateChooser_to = new com.toedter.calendar.JDateChooser();
        dateChooser_from = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        btn_print = new javax.swing.JButton();
        txt_filepath = new javax.swing.JTextField();
        btn_submit = new javax.swing.JButton();
        btn_browse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_feesdetails = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_course = new javax.swing.JLabel();
        lbl_totalamount = new javax.swing.JLabel();
        lbl_totalinwords = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelsideBar1.setBackground(new java.awt.Color(0, 102, 102));
        panelsideBar1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelHome6.setBackground(new java.awt.Color(0, 102, 102));
        panelHome6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome6.setForeground(new java.awt.Color(255, 255, 255));
        panelHome6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome6.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome6.setForeground(new java.awt.Color(255, 255, 255));
        btnhome6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/back-button.png"))); // NOI18N
        btnhome6.setText("Back");
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
        panelHome6.add(btnhome6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 280, -1));

        panelsideBar1.add(panelHome6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 680, 330, 70));

        panelHome7.setBackground(new java.awt.Color(0, 102, 102));
        panelHome7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome7.setForeground(new java.awt.Color(255, 255, 255));
        panelHome7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome7.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome7.setForeground(new java.awt.Color(255, 255, 255));
        btnhome7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/logout.png"))); // NOI18N
        btnhome7.setText("  Logout");
        btnhome7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhome7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome7MouseExited(evt);
            }
        });
        panelHome7.add(btnhome7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 280, -1));

        panelsideBar1.add(panelHome7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 790, 330, 70));

        panelHome1.setBackground(new java.awt.Color(0, 102, 102));
        panelHome1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome1.setForeground(new java.awt.Color(255, 255, 255));
        panelHome1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelHome1MouseEntered(evt);
            }
        });
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

        panelsideBar1.add(panelHome1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, 330, 70));

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

        panelsideBar1.add(panelHome2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 330, 70));

        panelHome3.setBackground(new java.awt.Color(0, 102, 102));
        panelHome3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome3.setForeground(new java.awt.Color(255, 255, 255));
        panelHome3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome3.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome3.setForeground(new java.awt.Color(255, 255, 255));
        btnhome3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/new-user.png"))); // NOI18N
        btnhome3.setText(" New Registration");
        btnhome3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnhome3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnhome3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnhome3MouseExited(evt);
            }
        });
        panelHome3.add(btnhome3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 310, 70));

        panelsideBar1.add(panelHome3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 330, 70));

        panelHome5.setBackground(new java.awt.Color(0, 102, 102));
        panelHome5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        panelHome5.setForeground(new java.awt.Color(255, 255, 255));
        panelHome5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnhome5.setFont(new java.awt.Font("Sylfaen", 0, 30)); // NOI18N
        btnhome5.setForeground(new java.awt.Color(255, 255, 255));
        btnhome5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/view all record.png"))); // NOI18N
        btnhome5.setText("View All Record");
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
        panelHome5.add(btnhome5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 280, -1));

        panelsideBar1.add(panelHome5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 580, 330, 70));

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/college_fee_management/images/list.png"))); // NOI18N
        jLabel11.setText("Report");
        panelsideBar1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));
        panelsideBar1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 440, 10));

        getContentPane().add(panelsideBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 540, 1010));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("To -");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, -1, -1));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("From -");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, -1, -1));

        combo_course.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_courseActionPerformed(evt);
            }
        });
        jPanel1.add(combo_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 220, -1));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Select Course :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, -1));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Select Course :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, -1, -1));
        jPanel1.add(dateChooser_to, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 120, 30));
        jPanel1.add(dateChooser_from, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 130, 30));

        jButton1.setBackground(new java.awt.Color(51, 51, 51));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Export to Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, -1, -1));

        btn_print.setBackground(new java.awt.Color(51, 51, 51));
        btn_print.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_print.setForeground(new java.awt.Color(255, 255, 255));
        btn_print.setText("Print");
        btn_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printActionPerformed(evt);
            }
        });
        jPanel1.add(btn_print, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 100, -1));

        txt_filepath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_filepathActionPerformed(evt);
            }
        });
        jPanel1.add(txt_filepath, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 340, 30));

        btn_submit.setBackground(new java.awt.Color(51, 51, 51));
        btn_submit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_submit.setForeground(new java.awt.Color(255, 255, 255));
        btn_submit.setText("Submit");
        btn_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_submitActionPerformed(evt);
            }
        });
        jPanel1.add(btn_submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 210, -1, -1));

        btn_browse.setBackground(new java.awt.Color(51, 51, 51));
        btn_browse.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_browse.setForeground(new java.awt.Color(255, 255, 255));
        btn_browse.setText("Browse");
        btn_browse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_browseActionPerformed(evt);
            }
        });
        jPanel1.add(btn_browse, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, -1, -1));

        tbl_feesdetails.setAutoCreateRowSorter(true);
        tbl_feesdetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, null, null));
        tbl_feesdetails.setFont(new java.awt.Font("Times New Roman", 0, 20)); // NOI18N
        tbl_feesdetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Receipt No", "Enrollment No.", "Student Name", "Course", "Amount", "Remark"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_feesdetails.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tbl_feesdetails.setRowHeight(18);
        tbl_feesdetails.setSelectionBackground(new java.awt.Color(255, 255, 255));
        tbl_feesdetails.setUpdateSelectionOnSort(false);
        tbl_feesdetails.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(tbl_feesdetails);
        if (tbl_feesdetails.getColumnModel().getColumnCount() > 0) {
            tbl_feesdetails.getColumnModel().getColumn(5).setPreferredWidth(18);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 1300, 580));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.black, null, null));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total Amount in words :");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Course Selected :");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Total Amount Collected :");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lbl_course.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lbl_course.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_course, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, 320, 30));

        lbl_totalamount.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lbl_totalamount.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_totalamount, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 330, 30));

        lbl_totalinwords.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        lbl_totalinwords.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(lbl_totalinwords, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 520, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 10, 620, 230));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 1330, 1010));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnhome6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome6MouseClicked
    Home home = new Home();
    home.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_btnhome6MouseClicked

    private void btnhome6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome6MouseEntered
        Color clr=new Color(0,153,153);
        panelHome6.setBackground(clr);
    }//GEN-LAST:event_btnhome6MouseEntered

    private void btnhome6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome6MouseExited
        Color clr=new Color(0,102,102);
        panelHome6.setBackground(clr);
    }//GEN-LAST:event_btnhome6MouseExited

    private void btnhome7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome7MouseEntered
        Color clr=new Color(0,153,153);
        panelHome7.setBackground(clr);
    }//GEN-LAST:event_btnhome7MouseEntered

    private void btnhome7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome7MouseExited
        Color clr=new Color(0,102,102);
        panelHome7.setBackground(clr);
    }//GEN-LAST:event_btnhome7MouseExited

    private void btnhome1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome1MouseClicked
        Home a=new Home();
        a.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnhome1MouseClicked

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

    private void btnhome3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome3MouseClicked
        AddFees a=new AddFees();
        a.setVisible(true);
        this.dispose();

        // TODO add your handling code here:
    }//GEN-LAST:event_btnhome3MouseClicked

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
        panelHome6.setBackground(clr);
    }//GEN-LAST:event_btnhome5MouseEntered

    private void btnhome5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome5MouseExited
        Color clr=new Color(0,102,102);
        panelHome6.setBackground(clr);
    }//GEN-LAST:event_btnhome5MouseExited

    private void panelHome1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelHome1MouseEntered
      Color clr=new Color (0,153,153);
      panelHome1.setBackground(clr);
    }//GEN-LAST:event_panelHome1MouseEntered

    private void combo_courseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_courseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_courseActionPerformed

    private void btn_browseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_browseActionPerformed

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(this);
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String date = dateFormat.format(new Date());
        
        try{
            File f = fileChooser.getSelectedFile();
            String path = f.getAbsolutePath();
            path = path+"_"+date+".xlsx";
            txt_filepath.setText(path);
            
        }catch(Exception e){
           e.printStackTrace();
        }
        
        
    }//GEN-LAST:event_btn_browseActionPerformed

    private void btnhome7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome7MouseClicked
Login_page a= new Login_page();
      a.setVisible(true);
      this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnhome7MouseClicked

    private void btn_submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_submitActionPerformed
   
      clearTable();
        setRecordsToTable();
    }//GEN-LAST:event_btn_submitActionPerformed

    private void txt_filepathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_filepathActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_filepathActionPerformed

    private void btn_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printActionPerformed
SimpleDateFormat Date_Format = new SimpleDateFormat("yyyy-MM-dd") ;
String datefrom= Date_Format.format(dateChooser_from.getDate());
String dateto = Date_Format.format(dateChooser_to.getDate());

MessageFormat header = new MessageFormat("Report From"+datefrom+"To"+dateto);
MessageFormat footer =new MessageFormat("page{0,number,integer}");
try{
tbl_feesdetails.print(JTable.PrintMode.FIT_WIDTH,header,footer);
}catch
    (Exception e){
    e.getMessage();
    }
            
            


SearchRecord a=new SearchRecord();
        a.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btn_printActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   ExportToExcel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnhome5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome5MouseClicked
ViewAllRecord a=new ViewAllRecord();
        a.setVisible(true);
        this.dispose();            // TODO add your handling code here:
    }//GEN-LAST:event_btnhome5MouseClicked

    private void btnhome2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnhome2MouseClicked
     SearchRecord a=new SearchRecord();
        a.setVisible(true);
        this.dispose();      
    }//GEN-LAST:event_btnhome2MouseClicked
    
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
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerateReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GenerateReport().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_browse;
    private javax.swing.JButton btn_print;
    private javax.swing.JButton btn_submit;
    private javax.swing.JLabel btnhome1;
    private javax.swing.JLabel btnhome2;
    private javax.swing.JLabel btnhome3;
    private javax.swing.JLabel btnhome5;
    private javax.swing.JLabel btnhome6;
    private javax.swing.JLabel btnhome7;
    private javax.swing.JComboBox<String> combo_course;
    private com.toedter.calendar.JDateChooser dateChooser_from;
    private com.toedter.calendar.JDateChooser dateChooser_to;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_course;
    private javax.swing.JLabel lbl_totalamount;
    private javax.swing.JLabel lbl_totalinwords;
    private javax.swing.JPanel panelHome1;
    private javax.swing.JPanel panelHome2;
    private javax.swing.JPanel panelHome3;
    private javax.swing.JPanel panelHome5;
    private javax.swing.JPanel panelHome6;
    private javax.swing.JPanel panelHome7;
    private javax.swing.JPanel panelsideBar1;
    private javax.swing.JTable tbl_feesdetails;
    private javax.swing.JTextField txt_filepath;
    // End of variables declaration//GEN-END:variables

}