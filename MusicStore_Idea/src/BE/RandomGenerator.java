package BE;
import java.util.ArrayList;
import java.util.Random;

public class RandomGenerator<randomNumber> {

    private DBConnection conn;
    private String[] stringUsername;
    private String[] stringSurname;
    private Random rand;

    public RandomGenerator() {
        conn = new DBConnection();
        conn.createConnection("kojda3", "kajokojda");
        this.stringUsername = new String[]{ "Oliver", "Jack", "Harry", "Jacob", "Noah", "Liam",
                "Mason", "Charlie", "Kyle", "Thomas", "Joe", "George", "Reece", "Oscar", "Rhys", "James",
                "William", "Joseph", "Michael", "David"};

        this.stringSurname = new String[]{ "Barker", "Morton", "Rhodes", "Sims", "Mitchell", "Jimenez",
                "Davison", "Castro", "Parsons", "Higgins", "Armstrong", "Frost", "Matthews", "Houston",
                "Gomez", "Parker", "Mejia", "Gibbs", "Stewart", "Christie"};
        rand = new Random();
    }

    public boolean generateUser() {
        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(user_id) from user_table";
        conn.getQueryResult(query, result);
        int userid = Integer.parseInt(result.get(0));



        userid++;
        int randomNumber = rand.nextInt(20);
        String username = stringUsername[randomNumber];
        randomNumber = rand.nextInt(20);
        String surname = stringSurname[randomNumber];
        String nickname = surname + userid;
        String passwrd = "heslo";

        System.out.println("Random user: " + userid + " " + username + " " + surname + " " + nickname + " " + passwrd);

        //query = "Insert into user_table values(" + userid + ", '" + username + "', '" + surname + "', '"
                //+ passwrd + "', '" + nickname + "', 'u')";
        //conn.getQueryResult(query, result);
        //conn.commit();
        return true;
    }

    public boolean generateAuthor() {
        String[] nationalities = new String[]{"SVK", "CZE", "USA", "ENG", "RUS", "GER"};
        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(author_id) from author";
        conn.getQueryResult(query, result);
        int authorid = Integer.parseInt(result.get(0));

        for (int i = 0; i < 100; i++) {
            authorid++;
            int randomNumber = rand.nextInt(20);
            String author_name = stringUsername[randomNumber];
            randomNumber = rand.nextInt(20);
            String surname = stringSurname[randomNumber];
            randomNumber = rand.nextInt(6);
            String nationality = nationalities[randomNumber];


            query = "Insert into author values(" + authorid + ", '" + author_name + "', '" + surname + "', '"
                    + nationality + "')";
            conn.getQueryResult(query, result);

        }
        conn.commit();
        return true;
    }

    public boolean generateStore() {
        String[] cities = new String[]{"Tokyo", "Delhi", "Shanghai", "Dhaka", "Osaka",
                "Cairo", "Mumbai", "Beijing", "Dhaka"};
        String[] storeNames = new String[]{"Soul_Sound", "Vector_Volume", "Trusted_Voices",
                "Smooth_Sounds", "Sonic_Sounds", "New_Wave", "Sticks_and_Strings", "Trap_Therapy"};
        String[] streets = new String[]{"Champs-Elysees", "Lombard_Street", "Abbey_Road",
                "Fifth_Avenue", "Santa_Monica_Boulevard", "Beale_Street", "Bourbon_Street",
                "Via_Monte_Napoleone", "Hollywood_Walk_of_Fame", "Wall_Street"};
        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(store_id) from store_table";
        conn.getQueryResult(query, result);
        int storeId = Integer.parseInt(result.get(0));

        for (int i = 0; i < 30; i++) {
            storeId++;
            int randomNumber = rand.nextInt(8);
            String randomStoreName = storeNames[randomNumber];
            randomNumber = rand.nextInt(9);
            String randomCity = cities[randomNumber];
            randomNumber = rand.nextInt(10);
            String randomStreet = streets[randomNumber];


            query = "Insert into store_table values(" + storeId + ", '" + randomStoreName + "', '" + randomCity + "', '"
                    + randomStreet + "')";
            conn.getQueryResult(query, result);
        }
        conn.commit();
        return true;
    }

    public boolean generateAlbum() {
        String[] albumTitle = new String[]{"Bold_as_Love", "Born_in_the_USA", "Kid_A",
                "Yellow_Brick", "Brick_Road", "Elvis", "Stand!", "Big_Pink", "Otis_Blue",
                "Low", "1999", "Dummy", "Odelay", "Tommy", "Superfly", "Exodus", "Ten",
                "Legend", "Graceland", "Back_in_Black", "The Band", "Led_Zeppelin_II"};
        String[] genres = new String[]{"Rock", "Pop", "Jazz", "Metal", "Electronic",
                "Funk", "Blues", "Country", "Latin", "Punk"};

        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(album_id) from album";
        conn.getQueryResult(query, result);
        int albumId = Integer.parseInt(result.get(0));
        query = "Select max(picture_id) from pictures";
        conn.getQueryResult(query, result);
        int pictureIdMax = Integer.parseInt(result.get(0));

        for(int i = 0; i < 250; i++) {
        albumId++;
        int pictureId = rand.nextInt((pictureIdMax)) + 1;
        int randomNumber = rand.nextInt(20);
        String randomTitle = albumTitle[randomNumber];
        randomNumber = rand.nextInt(10);
        String randomGenre = genres[randomNumber];
        int randomDay = rand.nextInt((30)) + 1;
        int randomMonth = rand.nextInt((12)) + 1;
        int randomYear = rand.nextInt((2010 - 1995 + 1)) + 1995;


        String wholeDate = randomDay + "." + randomMonth + "." + randomYear;
        query = "Insert into album values(" + albumId + ", " + pictureId + ", '" + randomTitle + "', '" + randomGenre + "', to_date('"
                + wholeDate + "', 'DD.MM.YYYY'))";
        conn.getQueryResult(query, result);
        }
        conn.commit();
        return true;
    }

    public boolean generateSong() {
        String[] songnames = new String[]{"One", "Imagine", "Bohemian_Rhapsody",
                "Hey Jude", "Rolling_Stone", "The_Queen", "London_Calling",
                "The_Twist", "Live_Forever", "Creep", "Respect", "Dancing_Queen",
                "Purple_Haze", "Hallelujah", "Stand_By_Me", "Yesterday", "Best_Love",
                "When_Doves_Cry", "River", "Best_of"};
        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(song_id) from song";
        conn.getQueryResult(query, result);
        int songId = Integer.parseInt(result.get(0));

        query = "Select max(album_id) from album";
        conn.getQueryResult(query, result);
        int albumId = Integer.parseInt(result.get(1));

        query = "Select max(author_id) from author";
        conn.getQueryResult(query, result);
        int authorId = Integer.parseInt(result.get(2));
        for (int i = 0; i < 300; i++) {
            songId++;
            int randomAlbumId = rand.nextInt((albumId)) + 1;
            int randomAuthorId = rand.nextInt((authorId)) + 1;
            int songLength = rand.nextInt((396 - 213 + 1)) + 213;
            int randomNumber = rand.nextInt(20);
            String songTitle = songnames[randomNumber];
            query = "Insert into song values(" + songId + ", " + randomAlbumId + ", " + randomAuthorId + ", " + songLength + ", '" + songTitle + "')";
            conn.getQueryResult(query, result);
        }
        conn.commit();

        return true;
    }

    public boolean generateRegistry() {
        ArrayList<String> result = new ArrayList<>();
        String query = "Select max(song_id) from song";
        conn.getQueryResult(query, result);
        int songId = Integer.parseInt(result.get(0));

        query = "Select max(store_id) from store_table";
        conn.getQueryResult(query, result);
        int storeId = Integer.parseInt(result.get(1));
        for (int i = 0; i < 200; i++) {
            int randomSong = rand.nextInt((songId)) + 1;
            int randomStore = rand.nextInt((storeId)) + 1;
            query = "Insert into registry values(" + randomSong + ", " + randomStore + ")";
            conn.getQueryResult(query, result);
        }
        conn.commit();
        return true;
    }


}
