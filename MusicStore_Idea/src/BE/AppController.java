package BE;


import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
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

    public ArrayList<Album> getAllAlbums() {
        return getAlbums("Select  * from album");
    }

    public Album getAlbumByID(int album_id) {
        ArrayList<String> result = new ArrayList<>();
        String query = "Select  * from album where album_id = " + album_id;
        int numOfColumns = conn.getQueryResult(query, result);
        if(result.size() == 0) return null;

        int id = 0;
        int pic_id = 0;
        String title = "";
        String genre = "";
        String release_date = "";
        for(int i = 0; i < result.size(); i++) {
            switch (i % numOfColumns)
            {
                case 0: id = Integer.parseInt(result.get(i));
                    break;
                case 1: pic_id = Integer.parseInt(result.get(i));
                    break;
                case 2: title =result.get(i);
                    break;
                case 3: genre = result.get(i);
                    break;
                case 4: release_date = result.get(i).substring(0,10);
                    break;
            }
        }
        Album al = new Album(id, pic_id, title, genre, release_date);
        return al;
    }

    private ArrayList<Album> getAlbums(String query) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Album> resultAlb = new ArrayList<>();
        int numOfColumns = conn.getQueryResult(query, result);
        if(numOfColumns == 0) return null;
        int id = 0;
        int pic_id = 0;
        String title = "";
        String genre = "";
        String release_date = "";
        for(int i = 0; i < result.size(); i++) {
            switch (i % numOfColumns)
            {
                case 0: id = Integer.parseInt(result.get(i));
                    break;
                case 1: pic_id = Integer.parseInt(result.get(i));
                    break;
                case 2: title =result.get(i);
                    break;
                case 3: genre = result.get(i);
                    break;
                case 4: release_date = result.get(i).substring(0,10);
                    break;
            }

            if(i % numOfColumns == numOfColumns - 1) {
                Album al = new Album(id, pic_id, title, genre, release_date);
                resultAlb.add(al);
            }
        }

        return resultAlb;
    }

    public BufferedImage getImage(int id) {
        return conn.getImage(id);
    }

    public ArrayList<Song> getSongsByAlbum(int album_id) {

        return getSongs("select sg.song_id, sg.album_id, sg.author_id, sg.song_length, sg.title, " +
                "aut.author_id, aut.author_name, aut.surname, aut.nationality " +
                "from song sg " +
                "join author aut on(sg.author_id = aut.author_id) " +
                "where sg.album_id = " + album_id);
    }

    public ArrayList<Song> getAllSongs() {
        return getSongs("select sg.song_id, sg.album_id, sg.author_id, sg.song_length, sg.title, " +
                "aut.author_id, aut.author_name, aut.surname, aut.nationality " +
                "from song sg " +
                "join author aut on(sg.author_id = aut.author_id)");
    }

    private ArrayList<Song> getSongs(String query) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Song> resultSong = new ArrayList<>();
        int numOfColumns = conn.getQueryResult(query, result);
        if(result.size() == 0) return null;
        int id = 0;
        int alb_id = 0;
        int author_id = 0;
        int song_length = 0;
        String title = "";
        int au_id = 0;
        String author_name = "";
        String author_surname = "";
        String nationality = "";
        for(int i = 0; i < result.size(); i++) {
            switch (i % numOfColumns)
            {
                case 0: id = Integer.parseInt(result.get(i));
                    break;
                case 1: alb_id = Integer.parseInt(result.get(i));
                    break;
                case 2: author_id = Integer.parseInt(result.get(i));
                    break;
                case 3: song_length = Integer.parseInt(result.get(i));
                    break;
                case 4: title = result.get(i);
                    break;
                case 5: au_id =  Integer.parseInt(result.get(i));
                    break;
                case 6: author_name = result.get(i);
                    break;
                case 7: author_surname = result.get(i);
                    break;
                case 8: nationality = result.get(i);
                    break;
            }

            if(i % numOfColumns == numOfColumns - 1) {
                Author author = new Author(au_id,author_name, author_surname, nationality);
                Song song = new Song(id, alb_id, author_id, song_length, title);
                song.setAuthor(author);
                resultSong.add(song);
            }
        }

        return resultSong;
    }

    public ArrayList<Album> getAlbumsBySubstring(String substring) {
        return getAlbums("select * from album where lower(title) LIKE lower('%" + substring + "%')");
    }

    public ArrayList<Song> getSongsBySubstring(String substring) {
        return getSongs("select * from song where lower(title) LIKE lower('%" + substring + "%')");
    }

    public ArrayList<Song> getSongsByUserID(int user_id) {
        return getSongs("select sg.song_id, sg.album_id, sg.author_id, sg.song_length, sg.title from song sg " +
                "join order_table ot on(sg.song_id = ot.song_id) " +
                "where user_id = " + user_id);
    }

    public ArrayList<Album> getAlbumsByUserID(int user_id) {
        return getAlbums("select distinct alb.album_id, alb.picture_id, alb.title, alb.genre, alb.release_date from album alb " +
                "join song on(alb.album_id = song.album_id) " +
                "join order_table using(song_id) " +
                "where user_id = " + user_id);
    }

    private ArrayList<Store> getStores(String query) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Store> resultStore = new ArrayList<>();
        int numOfColumns = conn.getQueryResult(query, result);
        if(result.size() == 0) return null;
        int id = 0;
        String store_name = "";
        String store_city = "";
        String store_street = "";
        for(int i = 0; i < result.size(); i++) {
            switch (i % numOfColumns)
            {
                case 0: id = Integer.parseInt(result.get(i));
                    break;
                case 1: store_name = result.get(i);
                    break;
                case 2: store_city = result.get(i);
                    break;
                case 3: store_street = result.get(i);
                    break;
            }

            if(i % numOfColumns == numOfColumns - 1) {
                Store store = new Store(id, store_name, store_city, store_street);
                resultStore.add(store);
            }
        }

        return resultStore;
    }

    public ArrayList<Store> getStoresBySongID(int song_id) {
        return getStores("select st.store_id, st.store_name, st.city, st.street \n" +
                "from store_table st join registry rg on(st.store_id = rg.store_id) \n" +
                "join song sg on(rg.song_id = sg.song_id) where rg.song_id = " + song_id);
    }

    public boolean insertOrder(int song_id, int user_id) {
        ArrayList<String> result = new ArrayList<>();

       conn.getQueryResult("select * from order_table where song_id = "+ song_id + " and user_id = " + user_id, result);

        if(result.size() != 0) return false;
        result.clear();
        int order_id = 1;
        if(conn.getQueryResult("select max(order_id) from order_table where user_id = " + user_id, result) > 1) {

            order_id = Integer.parseInt(result.get(0)) + 1;
        }
        result.clear();

        String query = "Insert into order_table values ("
                + song_id + ", " + user_id + ", " + order_id + ", sysdate)";
        conn.getQueryResult(query, result);

        return true;
    }

    public ArrayList<Album> getAlbumsBetweenDates(String date1, String date2) {
        return getAlbums("select * from album " +
                "where release_date between '" + date1 + "' and '" + date2 + "'");
    }

    public ArrayList<Song> getFirstXLongestSongs(int count) {
        return getSongs("select * from song " +
                "order by song_length desc fetch first "
                + count + " rows only");
    }

    public ArrayList<NickAndCount> getFirstXUserWithMostOrders(int count) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<NickAndCount> resultNC = new ArrayList<>();

        String query = "select nickname, count(*) as pocet_objednavok from user_table "
                + "join order_table using(user_id) "
                + "group by nickname order by pocet_objednavok desc "
                + "fetch first " + count + " rows only";

        int numOfColumns = conn.getQueryResult(query, result);
        if(result.size() == 0) return null;
        String nickname = "";
        int countOfOrders = 0;
        for(int i = 0; i < result.size(); i++) {
            switch (i % numOfColumns)
            {
                case 0: nickname = result.get(i);
                    break;
                case 1: countOfOrders = Integer.parseInt(result.get(i));
                    break;
            }

            if(i % numOfColumns == numOfColumns - 1) {
                NickAndCount nC = new NickAndCount(nickname, countOfOrders);
                resultNC.add(nC);
            }
        }

        return resultNC;
    }
}
