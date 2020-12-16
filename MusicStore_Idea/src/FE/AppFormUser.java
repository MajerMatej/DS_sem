package FE;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

import BE.Album;
import BE.AppController;
import BE.LoggedUser;
import BE.Song;

public class AppFormUser extends JFrame {
    private AppController controller;
    private JPanel rootPanel;
    private JButton findAlbumButton;
    private JButton findAllAlbumsButton;
    private JTextField textField1;
    private JCheckBox unownedOnlyCheckBox;
    private JLabel loggedUserLabel;
    private JLabel currentDateLabel;
    private JPanel picturePanel;
    private JTextField textField2;
    private JTextField textField3;
    private JButton findByReleaseButton;
    private JList list1;
    private JButton myCollectionButton;
    private JButton logoutButton;
    private JTextField textField4;
    private JButton findSongButton;
    private JButton findAllSongsButton;
    private JLabel genre;
    private JLabel releaseDate;
    private JLabel songTitle;
    private JLabel author;
    private JLabel pictureLabel;
    private JList yourSongsList;
    private JList yourAlbumsList;
    private JLabel findResultLabel;
    private JLabel songResultLabel;
    private JLabel albumResultLabel;
    private JLabel ySongTitle;
    private JLabel yGenre;
    private JLabel yReleaseDate;
    private JLabel yAuthor;
    private JPanel customPicturePanel;
    private JLabel customPictureLabel;
    private JFrame frame;
    private ArrayList<Album> albumList;
    private ArrayList<Song> albumsSongList;
    private ArrayList<Song> songList;
    private Object currentlySelected;
    private ArrayList<Album> customAlbumList;
    private ArrayList<Song> customSongList;

    AppFormUser(AppController controller) {
        this.controller = controller;
        add(rootPanel);
        setTitle("Music Store");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.loggedUserLabel.setText(controller.getlUser().getNickname());
        this.currentDateLabel.setText("Logged since " + new Date(System.currentTimeMillis()).toString());
        clearOutput();
        clearCustomOutput();
        findResultLabel.setText("Albums / Songs");
        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JFrame frame = (JFrame) e.getSource();

                int result = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit the application?",
                        "Exit Application",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                controller.setlUser(new LoggedUser());
                dispose();
                new AppFormLogin(controller);
            }
        });
        findAllAlbumsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearOutput();
                findResultLabel.setText("Albums");
                albumList = new ArrayList<>();
                albumList = (controller.getAllAlbums());
                DefaultListModel dlm = new DefaultListModel();
                for (int i = 0; i < albumList.size(); i++) {
                    dlm.addElement(albumList.get(i));
                }
                list1.setModel(dlm);


            }
        });
        findAllSongsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearOutput();
                findResultLabel.setText("Songs");
                songList = new ArrayList<>();
                songList = (controller.getAllSongs());
                DefaultListModel dlm = new DefaultListModel();
                for (int i = 0; i < songList.size(); i++) {
                    dlm.addElement(songList.get(i));
                }
                list1.setModel(dlm);


            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = list1.locationToIndex(e.getPoint());
                ListModel dlm = list1.getModel();
                Object item = dlm.getElementAt(index);
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                    if (item instanceof Album) {
                        JOptionPane.showMessageDialog(frame,
                                ((Album) item).toStringSongs(),
                                "Detail",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else if (item instanceof Song) {
                        JOptionPane.showMessageDialog(frame,
                                item,
                                "Detail",
                                JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            }
        });

        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = list1.locationToIndex(e.getPoint());
                ListModel dlm = list1.getModel();
                Object item = dlm.getElementAt(index);
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    if (item instanceof Album) {
                        ((Album) item).setSongs(controller.getSongsByAlbum(((Album) item).getAlbum_id()));
//                        ((Album) item).setAuthor_name(((Album) item).getSongs().get(0).get);
                        currentlySelected = (Album) item;
                        genre.setText(((Album) item).getGenre());
                        songTitle.setText(((Album) item).getTitle());
//                        author.setText(((Album) item).getAuthor());
                        releaseDate.setText(((Album) item).getRelease_date());


                        BufferedImage image = controller.getImage(((Album) item).getPicture_id());
                        pictureLabel.setIcon(new ImageIcon(image));
                    } else if (item instanceof Song) {
                        currentlySelected = (Song) item;
                        songTitle.setText(((Song) item).getTitle());
                        genre.setText("Duration: " + ((Song) item).getFormatedLength());
                        author.setText(((Song) item).getAuthor().getAuthor_surname() + " "
                                + ((Song) item).getAuthor().getAuthor_name());
                        Album album = controller.getAlbumByID(((Song) item).getAlbum_id());
                        if (album != null) {
                            releaseDate.setText(album.getTitle());
                            BufferedImage image = controller.getImage((album.getPicture_id()));
                            pictureLabel.setIcon(new ImageIcon(image));
                        } else {
                            pictureLabel.setIcon(null);
                            releaseDate.setText("Single");
                        }
                    }
                }
            }
        });

        myCollectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearCustomOutput();
                customAlbumList = new ArrayList<>();
                customAlbumList = (controller.getAlbumsByUserID(controller.getlUser().getUser_id()));
                DefaultListModel dlm = new DefaultListModel();
                if (customAlbumList != null) {
                    for (int i = 0; i < customAlbumList.size(); i++) {
                        dlm.addElement(customAlbumList.get(i));
                    }
                    yourAlbumsList.setModel(dlm);
                } else {
                    dlm.addElement(String.valueOf("You currently don't own any albums."));
                    yourAlbumsList.setModel(dlm);
                }
                customSongList = new ArrayList<>();
                customSongList = (controller.getSongsByUserID(controller.getlUser().getUser_id()));
                DefaultListModel dlmS = new DefaultListModel();

                if (customSongList != null) {
                    for (int i = 0; i < customSongList.size(); i++) {
                        dlmS.addElement(customSongList.get(i));
                    }
                    yourSongsList.setModel(dlmS);
                } else {
                    dlmS.addElement(String.valueOf("You currently don't own any albums."));
                    yourSongsList.setModel(dlmS);
                }
            }
        });
        yourSongsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = yourSongsList.locationToIndex(e.getPoint());
                ListModel dlm = yourSongsList.getModel();
                Song item = (Song)dlm.getElementAt(index);
                ySongTitle.setText(((Song) item).getTitle());
                yGenre.setText("Duration: " + ((Song) item).getFormatedLength());
                yAuthor.setText(((Song) item).getAuthor().getAuthor_surname() + " "
                        + ((Song) item).getAuthor().getAuthor_name());
                Album album = controller.getAlbumByID(((Song) item).getAlbum_id());
                if (album != null) {
                    yReleaseDate.setText(album.getTitle());
                    BufferedImage image = controller.getImage((album.getPicture_id()));
                    customPictureLabel.setIcon(new ImageIcon(image));
                } else {
                    customPictureLabel.setIcon(null);
                    yReleaseDate.setText("Single");
                }
            }
        });
        yourAlbumsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = yourAlbumsList.locationToIndex(e.getPoint());
                ListModel dlm = yourAlbumsList.getModel();
                Object item = dlm.getElementAt(index);
                if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
//                        ((Album) item).setAuthor_name(((Album) item).getSongs().get(0).get);
                    yGenre.setText(((Album) item).getGenre());
                    ySongTitle.setText(((Album) item).getTitle());
//                        author.setText(((Album) item).getAuthor());
                    yReleaseDate.setText(((Album) item).getRelease_date());
                    BufferedImage image = controller.getImage(((Album) item).getPicture_id());
                    customPictureLabel.setIcon(new ImageIcon(image));
                }
            }
        });
    }


    private void clearOutput() {
        genre.setText("");
        author.setText("");
        songTitle.setText("");
        releaseDate.setText("");
        pictureLabel.setIcon(null);
    }

    private void clearCustomOutput() {
        yGenre.setText("");
        yAuthor.setText("");
        ySongTitle.setText("");
        yReleaseDate.setText("");
        customPictureLabel.setIcon(null);
    }
}
