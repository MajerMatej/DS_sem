package BE;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    /*public static void main(String[] args) {
        createConnection("kojda3", "kajokojda");
        ArrayList<String> test = new ArrayList<>();
        int numOfColumns = getQueryResult("select * from store_table", test);
        testOutput(test, numOfColumns);
        closeConnection();
    }*/
    private static Connection con;

    public static boolean createConnection(String username, String password) {
        try{

            Class.forName("oracle.jdbc.driver.OracleDriver");

            con=DriverManager.getConnection("jdbc:oracle:thin:@asterix.fri.uniza.sk:1521:orcl", username, password);
            Statement stmt=con.createStatement();

        }catch(Exception e){ System.out.println(e);}

        return false;
    }

    public static int getQueryResult(String query, ArrayList<String> result) {
        //ArrayList<String> result = new ArrayList<String>();
        int columnsNumber = 0;
        try {
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();

            columnsNumber = rsmd.getColumnCount();
            while(rs.next())
            {
                for(int i = 0;i < columnsNumber; i++) {
                    result.add(rs.getString(i + 1));
                }
            }
        }catch(Exception e){ System.out.println(e);}

        return columnsNumber;
    }

    public static void testOutput(ArrayList<String> results, int numOfColumns) {
        for(int i = 0; i < results.size(); i++)
        {
            System.out.printf(results.get(i));
            if(i % numOfColumns == numOfColumns - 1) System.out.println('\n');
        }
    }

    public static void closeConnection() {
        try
        {
            con.close();
        } catch(Exception e){ System.out.println(e);}
    }
}
