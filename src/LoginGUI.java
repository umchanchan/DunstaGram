import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class LoginGUI implements Runnable {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JTextField userField;
    private JTextField passField;
    private Profile user;
    private JButton signupButton;
    private JButton loginButton;
    private JFrame frame;


    public LoginGUI(ObjectInputStream ois, ObjectOutputStream oos) throws IOException {
        this.ois = ois;
        this.oos = oos;
        oos.flush();
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                try {
                    String username = userField.getText();
                    String pass = passField.getText();

                    oos.writeObject("login");
                    oos.writeObject(username);
                    oos.writeObject(pass);
                    oos.flush();

                    Object response = ois.readObject();
                    if (response instanceof Profile) {
                        user = (Profile) response;
                        System.out.println(user.getUsername());
                        JOptionPane.showMessageDialog(frame, "Login Success!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);

                        SwingUtilities.invokeLater(new MainGUI(user, ois, oos));

                    } else if (response instanceof String) {
                        JOptionPane.showMessageDialog(frame, "Please check your username or password",
                                "Login Fail", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Error communicating with the server.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else if (e.getSource() == signupButton) {
                SwingUtilities.invokeLater(new SignUpGUI(ois, oos));
                frame.dispose();
            }
        }
    };


    public void run() {
        frame = new JFrame();
        frame.setTitle("Login Menu");
        Container content = frame.getContentPane();
        content.setLayout(new GridLayout(4, 2, 5,5));


        userField = new JTextField(10);
        JLabel userLabel = new JLabel("Username");
        userLabel.setText("Enter your username: ");

        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(userField);
        content.add(userPanel);

        passField = new JTextField(10);
        JLabel passLabel = new JLabel("Password");
        passLabel.setText("Enter your password: ");

        JPanel passPanel = new JPanel();
        passPanel.add(passLabel);
        passPanel.add(passField);
        content.add(passPanel);

        loginButton = new JButton("Login");
        content.add(loginButton);

        signupButton = new JButton("Don't have an account?");
        content.add(signupButton);

        loginButton.addActionListener(actionListener);
        signupButton.addActionListener(actionListener);

        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
