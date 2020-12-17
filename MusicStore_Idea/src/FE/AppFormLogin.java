package FE;

import BE.AppController;
import BE.userType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppFormLogin extends  JFrame{
    private AppController controller;
    private JPanel rootPanel;
    private JTextField password;
    private JTextField nickname;
    private JButton loginButton;
    private JButton registrationButton;
    private JLabel messageLabel;
    private JPanel loginPanel;
    private JPanel borderPanel;

    AppFormLogin(AppController controller) {
        this.controller = controller;
        loginPanel.setBackground(new Color(150,150,150));
        add(rootPanel);
        setTitle("Music Store Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        registrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new AppFormRegistration(controller);
                dispose();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(controller.loginAutentification(nickname.getText(),password.getText())){
                    if(controller.getlUser().getType() == userType.USER){
                        dispose();
                        new AppFormUser(controller);
                    }
                    if(controller.getlUser().getType() == userType.ADMIN){
                        dispose();
                        new AppFormAdmin(controller);
                    }
                } else {
                    messageLabel.setText("Incorrect username or password!");
                }
            }
        });
    }

}
