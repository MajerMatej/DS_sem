package FE;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private JTextField nameOfAlbum;
    private JLabel loggedUserLabel;
    private JLabel currentDateLabel;
    private JPanel picturePanel;
    private JFormattedTextField releaseDateFrom;
    private JFormattedTextField releaseDateTo;
    private JButton findByReleaseButton;
    private JList list1;
    private JButton myCollectionButton;
    private JButton logoutButton;
    private JTextField nameOfSong;
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
    private JButton getSongButton;
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
        this.loggedUserLabel.setText("Username: " + controller.getlUser().getNickname());
        this.currentDateLabel.setText("Logged since: " + new Date(System.currentTimeMillis()).toString());
        clearOutput();
        clearCustomOutput();
        findResultLabel.setText("Albums / Songs");
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        // Define the number factory.
        DateFormatter fft = new DateFormatter(df);
        DefaultFormatterFactory factory = new DefaultFormatterFactory(fft);
        releaseDateFrom.setFormatterFactory(factory);
        releaseDateTo.setFormatterFactory(factory);
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
                        currentlySelected = (Album) item;
                        genre.setText("Genre: " + ((Album) item).getGenre());
                        songTitle.setText("Title: " + ((Album) item).getTitle());
                        releaseDate.setText("Release date: " + ((Album) item).getRelease_date());
                        author.setText("Author: " + (((Album) item).getSongs().get(0).getAuthor().getAuthor_surname() + " "
                                + ((Album) item).getSongs().get(0).getAuthor().getAuthor_name()));

                        BufferedImage image = controller.getImage(((Album) item).getPicture_id());
                        pictureLabel.setIcon(new ImageIcon(image));
                    } else if (item instanceof Song) {
                        currentlySelected = (Song) item;
                        songTitle.setText("Title: " + ((Song) item).getTitle());
                        genre.setText("Duration: " + ((Song) item).getFormatedLength());
                        author.setText("Author: " + ((Song) item).getAuthor().getAuthor_surname() + " "
                                + ((Song) item).getAuthor().getAuthor_name());
                        Album album = controller.getAlbumByID(((Song) item).getAlbum_id());
                        if (album != null) {
                            releaseDate.setText("Album: " + album.getTitle());
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
                Song item = (Song) dlm.getElementAt(index);
                ySongTitle.setText("Title: " + ((Song) item).getTitle());
                yGenre.setText("Duration: " + ((Song) item).getFormatedLength());
                yAuthor.setText("Author: " + ((Song) item).getAuthor().getAuthor_surname() + " "
                        + ((Song) item).getAuthor().getAuthor_name());
                Album album = controller.getAlbumByID(((Song) item).getAlbum_id());
                if (album != null) {
                    yReleaseDate.setText("Album: " + album.getTitle());
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
                    yGenre.setText("Genre: " + ((Album) item).getGenre());
                    ySongTitle.setText("Title:" + ((Album) item).getTitle());
//                        author.setText(((Album) item).getAuthor());
                    yReleaseDate.setText("Release date: " + ((Album) item).getRelease_date());
                    BufferedImage image = controller.getImage(((Album) item).getPicture_id());
                    customPictureLabel.setIcon(new ImageIcon(image));
                }
            }
        });
        getSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currentlySelected instanceof Song) {
                    if(controller.insertOrder(((Song) currentlySelected).getId(),controller.getlUser().getUser_id())){
                        JOptionPane.showMessageDialog(
                                frame,
                                "Song successfully added to your collection",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        myCollectionButton.doClick();
                    } else {
                        JOptionPane.showMessageDialog(
                                frame,
                                "Song is already in your collection",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (currentlySelected instanceof Album) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "You can only get songs, not the whole album",
                            "Sorry",
                            JOptionPane.WARNING_MESSAGE);
                } else if (currentlySelected == null) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please select a song to get",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        findAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!nameOfAlbum.getText().equals("")) {
                    clearOutput();
                    albumList = controller.getAlbumsBySubstring(nameOfAlbum.getText());
                    findResultLabel.setText("Albums");
                    DefaultListModel dlm = new DefaultListModel();
                    if (albumList.size() > 0) {
                        for (int i = 0; i < albumList.size(); i++) {
                            dlm.addElement(albumList.get(i));
                        }
                    } else {
                        dlm.addElement("No album matches search options");
                    }
                    list1.setModel(dlm);
                } else {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Search field is empty",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        findSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                clearOutput();
                if (!nameOfSong.getText().equals("")) {
                    songList = controller.getSongsBySubstring(nameOfSong.getText());
                    findResultLabel.setText("Songs");
                    DefaultListModel dlm = new DefaultListModel();
                    if (songList.size() > 0) {
                        for (int i = 0; i < songList.size(); i++) {
                            dlm.addElement(songList.get(i));
                        }
                    } else {
                        dlm.addElement("No song matches search options");
                    }
                    list1.setModel(dlm);
                } else {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Search field is empty",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        findByReleaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String from = releaseDateFrom.getText();
                String to = releaseDateTo.getText();


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
