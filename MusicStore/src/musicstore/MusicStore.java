package musicstore;

import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author mmaje
 */
public class MusicStore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{  
            //step1 load the driver class  
            Class.forName("oracle.jdbc.driver.OracleDriver");  

            //step2 create  the connection object  
            Connection con=DriverManager.getConnection(  
            "jdbc:oracle:thin:@localhost:1521:xe","matej_majer","akosamas");  

            //step3 create the statement object  
            Statement stmt=con.createStatement();  

            //step4 execute query  
            ResultSet rs=stmt.executeQuery("select * from os_udaje");  
            while(rs.next())  
            System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3) 
                    + " " + rs.getString(4)+"  "+rs.getString(5)+"  "+rs.getString(6));  

            //step5 close the connection object  
            con.close();  

            }catch(Exception e){ System.out.println(e);}  
  
        
    }
    
}
