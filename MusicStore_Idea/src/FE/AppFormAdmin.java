package FE;

import BE.AppController;
import BE.LoggedUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

public class AppFormAdmin extends JFrame{
    private AppController controller;
    private JPanel rootPanel;
    private JTextField textField4;
    private JButton findUserSSongsButton;
    private JButton findAlbumButton;
    private JButton findAllSongsButton;
    private JList list1;
    private JPanel picture;
    private JLabel loggedUserLabel;
    private JLabel currentDateLabel;
    private JButton logoutButton;
    private JComboBox statistics;
    private JTextField textField1;
    private JList list2;

    AppFormAdmin(AppController controller) {
        this.controller = controller;
        add(rootPanel);
        setTitle("Music Store");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.loggedUserLabel.setText(controller.getlUser().getNickname());
        this.currentDateLabel.setText("Logged since " + new Date(System.currentTimeMillis()).toString());
        this.setVisible(true);
        statistics.addItem(new ComboItem("Visible String 1", "Value 1"));
        statistics.addItem(new ComboItem("Visible String 2", "Value 2"));
        statistics.addItem(new ComboItem("Visible String 3", "Value 3"));
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
    }
}
