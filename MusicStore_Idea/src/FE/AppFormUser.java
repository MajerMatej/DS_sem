package FE;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import BE.AppController;
import BE.LoggedUser;

public class AppFormUser extends JFrame {
    private AppController controller;
    private JPanel rootPanel;
    private JButton findAlbumButton;
    private JButton findAllButton;
    private JTextField textField1;
    private JCheckBox unownedOnlyCheckBox;
    private JLabel loggedUserLabel;
    private JLabel currentDateLabel;
    private JPanel picture;
    private JTextField textField2;
    private JTextField textField3;
    private JButton findByReleaseButton;
    private JList list1;
    private JButton myCollectionButton;
    private JButton logoutButton;
    private JTextField textField4;
    private JButton findSongButton;

    AppFormUser(AppController controller) {
        this.controller = controller;
        add(rootPanel);
        setTitle("Music Store");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.loggedUserLabel.setText(controller.getlUser().getNickname());
        this.currentDateLabel.setText("Logged since " + new Date(System.currentTimeMillis()).toString());
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
    }

}
