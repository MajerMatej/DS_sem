package FE;

import BE.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;

public class AppFormAdmin extends JFrame {
    private AppController controller;
    private JPanel rootPanel;
    private JLabel loggedUserLabel;
    private JLabel currentDateLabel;
    private JButton logoutButton;
    private JTextField statCount;
    private JButton fetchStatisticsButton;
    private JButton findAllAlbumsButton;
    private JButton findAllSongsButton;
    private JList list1;
    private JLabel findResultLabel;
    private JPanel picturePanel;
    private JLabel pictureLabel;
    private JLabel genre;
    private JLabel author;
    private JLabel songTitle;
    private JLabel releaseDate;
    private JComboBox statistics;
    private ArrayList<Song> songList;
    private ArrayList<Album> albumList;
    private Object currentlySelected;
    private JFrame frame;

    AppFormAdmin(AppController controller) {
        this.controller = controller;
        add(rootPanel);
        setTitle("Music Store");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.loggedUserLabel.setText("Username: " + controller.getlUser().getNickname());
        this.currentDateLabel.setText("Logged since: " + new Date(System.currentTimeMillis()).toString());
        clearOutput();
        this.setVisible(true);
        ComboItem cItem = new ComboItem("Most songs users", "Most songs users");
        statistics.addItem(cItem);
        statistics.addItem(new ComboItem("Longest songs", "Longest songs"));
        statistics.addItem(new ComboItem("Sold count by days", "Sold count by days"));

        statistics.setSelectedItem(cItem);
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
                        ArrayList<Store> stores = controller.getStoresBySongID(((Song) item).getId());
                        String output = "";

                        if (stores != null && stores.size() > 0) {
                            for (int i = 0; i < stores.size(); i++) {
                                output += stores.get(i).toString();
                            }
                            JOptionPane.showMessageDialog(frame,
                                    output,
                                    "Detail",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame,
                                    "This song is currently not in any store",
                                    "Detail",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
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
        fetchStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String value = ((ComboItem) statistics.getSelectedItem()).getValue();
                if (value.equals("Most songs users")) {
                    if (statCount.getText().equals("")) {
                        JOptionPane.showMessageDialog(frame,
                                "Number of records not specified",
                                "Detail",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        ArrayList<NickAndCount> users =
                                controller.getFirstXUserWithMostOrders(Integer.parseInt(statCount.getText()));
                        DefaultListModel dlm = new DefaultListModel();
                        for (int i = 0; i < users.size(); i++) {
                            dlm.addElement(users.get(i));
                        }
                        list1.setModel(dlm);
                    }
                } else if (value.equals("Longest songs")) {
                    if (statCount.getText().equals("")) {
                        JOptionPane.showMessageDialog(frame,
                                "Number of records not specified",
                                "Detail",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        ArrayList<Song> songs =
                                controller.getFirstXLongestSongs(Integer.parseInt(statCount.getText()));
                        DefaultListModel dlm = new DefaultListModel();
                        for (int i = 0; i < songs.size(); i++) {
                            dlm.addElement(songs.get(i));
                        }
                        list1.setModel(dlm);
                    }
                } else if (value.equals("Sold count by days")) {
                    ArrayList<OrderAndCount> songs =
                            controller.getOrdersInDay();
                    DefaultListModel dlm = new DefaultListModel();
                    for (int i = 0; i < songs.size(); i++) {
                        dlm.addElement(songs.get(i));
                    }
                    list1.setModel(dlm);
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
}
