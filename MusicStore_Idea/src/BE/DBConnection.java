package BE;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    /*public static void main(String[] args) {
        createConnection("kojda3", "kajokojda");
        ArrayList<String> test = new ArrayList<>();
       addPicture(5, "darth-vader.png");
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

    public static void addPicture(int id, String name) {
        try {
        File file = new File(name);

            FileInputStream fis = new FileInputStream(file);
            String INSERT_PICTURE = "INSERT INTO pictures(picture_id, picture_data) VALUES (?, ?)";

        PreparedStatement ps = con.prepareStatement(INSERT_PICTURE);{
            ps.setInt(1, id);
            ps.setBinaryStream(2, fis, (int) file.length());
            ps.executeUpdate();
            con.commit();
        }} catch(Exception e){ System.out.println(e);}
    }

    public static BufferedImage getImage(int id) {
        try {
            Statement stmt = con.createStatement();
            String query = "select picture_data from pictures where picture_id = " + id;

            ResultSet rs = stmt.executeQuery(query);

            java.sql.Blob blob = rs.getBlob(1);
            InputStream in = blob.getBinaryStream();
            BufferedImage image = ImageIO.read(in);
            return image;
        }catch(Exception e){ System.out.println(e);}
        return null;
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

    public void commit(){
        String query = "commit";
        this.getQueryResult(query,new ArrayList<>() );
    }
}
