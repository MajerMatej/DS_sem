package FE;

import BE.AppController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AppFormRegistration extends JFrame {
    private JPanel rootPanel;
    private JTextField usernameInput;
    private JTextField passwordInput;
    private JTextField confirmPasswordInput;
    private JButton registerButton;
    private AppController controller;
    AppFormRegistration(AppController controller) {
        this.controller = controller;
        add(rootPanel);
        setTitle("Music Store Registration");
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
    }
}
