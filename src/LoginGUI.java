import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginGUI implements Runnable {
    private String user;
    private String pass;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public LoginGUI(ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        this.ois = ois;
        this.oos = oos;
    }


    public void run() {
        JFrame frame = new JFrame();
        frame.setTitle("Login Menu");
        Container content = frame.getContentPane();
        content.setLayout(new FlowLayout());

        frame.setSize(360, 180);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JTextField userField = new JTextField(10);
        JButton userButton = new JButton("Enter");
        JLabel userLabel = new JLabel("Username");
        userLabel.setText("Enter your username: ");

        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(userField);
        userPanel.add(userButton);
        content.add(userPanel);

        JTextField passField = new JTextField(10);
        JButton passButton = new JButton("Enter");
        JLabel passLabel = new JLabel("Password");
        passLabel.setText("Enter your password: ");

        JPanel passPanel = new JPanel();
        passPanel.add(passLabel);
        passPanel.add(passField);
        passPanel.add(passButton);
        content.add(passPanel);

        JButton loginButton = new JButton("Login");
        content.add(loginButton);

        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                user = String.valueOf(userField.getText());



            }
        });

        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                pass = String.valueOf(passField.getText());

            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


    }

    public void showError() {
        JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
