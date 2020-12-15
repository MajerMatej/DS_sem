package BE;


import java.util.ArrayList;

public class AppController {
    private DBConnection conn;
    private LoggedUser lUser = new LoggedUser();

    public AppController() {
        conn = new DBConnection();
        conn.createConnection("kojda3", "kajokojda");
    }

    public boolean loginAutentification(String nickname, String pass) {
        ArrayList<String> result = new ArrayList<>();
        String query = "Select * from user_table where nickname LIKE '" + nickname + "' and user_password LIKE '" + pass + "'";
        conn.getQueryResult(query, result);
        if (result.size() == 0) return false;
        //TODO: parse result to user data
        lUser.setUser_id(Integer.parseInt(result.get(0)));
        lUser.setUsername(result.get(1));
        lUser.setSurname(result.get(2));
        lUser.setNickname(result.get(4));
        if (result.get(5).equals("a"))
            lUser.setType(userType.ADMIN);
        if (result.get(5).equals("u"))
            lUser.setType(userType.USER);

        return true;
    }

    public boolean registration(String name, String surname, String nickname, String pass1, String pass2) {
        if (!pass1.equals(pass2)) return false;

        ArrayList<String> result = new ArrayList<>();
        String query = "Select  nickname from user_table where nickname LIKE '" + nickname + "'";
        conn.getQueryResult(query, result);
        if (result.size() > 0)
            return false;
        result.clear();

        query = "Select max(user_id) from user_table";
        conn.getQueryResult(query, result);
        int id = (result.size() == 0) ? 1 : Integer.parseInt(result.get(0)) + 1;
        result.clear();


        query = "Insert into user_table values(" + id + ", '" + name + "', '" + surname + "', '"
                + pass1 + "', '" + nickname + "', 'u')";
        conn.getQueryResult(query, result);
        conn.commit();
        return true;
    }

    public DBConnection getConn() {
        return conn;
    }

    public void setlUser(LoggedUser lUser) {
        this.lUser = lUser;
    }

    public LoggedUser getlUser() {
        return lUser;
    }

    public void closeConnection() {
        conn.closeConnection();
    }
}
