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
            
            Class.forName("oracle.jdbc.driver.OracleDriver");  
            
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@asterix.fri.uniza.sk:1521:orcl", "kojda3", "***");   
            Statement stmt=con.createStatement();  

            ResultSet rs=stmt.executeQuery("select * from store_table");  
            while(rs.next())  
            System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3) 
                    + " " + rs.getString(4));//+"  "+rs.getString(5)+"  "+rs.getString(6));  

            con.close();  

            }catch(Exception e){ System.out.println(e);}        
    }
}
