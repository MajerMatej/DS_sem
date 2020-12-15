package BE;


import java.util.ArrayList;

public class AppController {
    private DBConnection conn;
    private LogedUser lUser= new LogedUser();

    public AppController() {
        conn = new DBConnection();
        conn.createConnection("kojda3", "kajokojda");
    }

    public boolean loginAutentification(String nickname, String pass) {
        ArrayList<String> result = new ArrayList<>();
        String query = "Select * from user_table where username LIKE '" + nickname
                + "' and password LIKE '" + pass + "';";
        conn.getQueryResult(query, result);
        if(result.size() == 0) return false;
        //TODO: parse result to user data
        lUser.setUser_id(Integer.parseInt(result.get(1)));
        lUser.setUsername(result.get(2));
        lUser.setSurname(result.get(3));
        lUser.setNickname(result.get(5));
        if(result.get(6) == "a")lUser.setType(userType.ADMIN);
        if(result.get(6) == "u")lUser.setType(userType.USER);

        return true;
    }

    public boolean registration(String name, String surname, String nickname, String pass1, String pass2) {
        if(pass1 != pass2) return false;

        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(user_id) from user_table;";
        conn.getQueryResult(query, result);
        int id = (result.size() == 0) ? 1 : Integer.parseInt(result.get(1) + 1);

        query = "Insert into user_table values(" + id + ", " + name + ", " + surname + ", "
                + pass1 + ", " + nickname +  ", u);";
        conn.getQueryResult(query, result);
        return true;
    }

    public void closeConnection() {
        conn.closeConnection();
    }
}
