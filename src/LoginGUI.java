import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
                        JOptionPane.showMessageDialog(frame, "Login Successful!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
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
        frame.setLayout(new GridLayout(4, 4, 5, 5));


        userField = new JTextField(10);
        JLabel userLabel = new JLabel("Enter your username: ");


        frame.add(userLabel);
        frame.add(userField);


        passField = new JTextField(10);
        JLabel passLabel = new JLabel("Enter your password: ");


        frame.add(passLabel);
        frame.add(passField);

        loginButton = new JButton("Login");
        frame.add(loginButton);

        signupButton = new JButton("Don't have an account?");
        frame.add(signupButton);

        loginButton.addActionListener(actionListener);
        signupButton.addActionListener(actionListener);

        frame.setSize(800, 450);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                try {
                    oos.writeObject("Exit");
                    oos.flush();
                    oos.close();
                    ois.close();
                    frame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
