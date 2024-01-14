/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package college_fee_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class DBConnection {
    
  public static Connection getConnection(){
      Connection con=null;
      try {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
     con=DriverManager.getConnection("jdbc:derby://localhost:1527/fees_management","root","root");
      }
      catch (ClassNotFoundException | SQLException e){
      }
      return con;
  }  
}
