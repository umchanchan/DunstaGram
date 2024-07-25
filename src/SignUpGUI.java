import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

public class SignUpGUI extends JComponent implements Runnable {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String user;
    private String pass;
    private String age;
    private String gender;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel genderLabel;
    private JLabel ageLabel;
    private JTextField userField;
    private JTextField genderField;
    private JTextField ageField;
    private JTextField passField;
    private JButton makeAccountButton;
    private JButton cancelButton;
    private JFrame frame;


    public SignUpGUI(ObjectInputStream ois, ObjectOutputStream oos) {
        this.ois = ois;
        this.oos = oos;
    }

    public void run() {

        frame = new JFrame();
        frame.setTitle("Sign Up Menu");
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel instructionLabel = new JLabel("Do not put \"_\" in your username");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(instructionLabel);

        JPanel centerPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        userField = new JTextField(10);
        userLabel = new JLabel("Enter a Username: ");

        passwordLabel = new JLabel("Enter a Password: ");
        passField = new JTextField(10);

        ageLabel = new JLabel("Enter your Age: ");
        ageField = new JTextField(10);

        genderLabel = new JLabel("Enter your Gender: ");
        genderField = new JTextField(10);


        makeAccountButton = new JButton("Sign Up");
        cancelButton = new JButton("Back To Login");

        centerPanel.add(userLabel);
        centerPanel.add(userField);


        centerPanel.add(passwordLabel);
        centerPanel.add(passField);


        centerPanel.add(ageLabel);
        centerPanel.add(ageField);

        centerPanel.add(genderLabel);
        centerPanel.add(genderField);


        centerPanel.add(makeAccountButton);
        centerPanel.add(cancelButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);

        makeAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signUpUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new LoginGUI(ois, oos).run();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error communicating with the server.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setSize(800, 450);
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

    public void signUpUser() {
        user = userField.getText();
        pass = passField.getText();
        age = ageField.getText();
        gender = genderField.getText();
        try {
            writeObject();

            String response = (String) ois.readObject();
            if (response.equals("Success")) {
                JOptionPane.showMessageDialog(frame, "Signup successful, please login.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                SwingUtilities.invokeLater(new LoginGUI(ois, oos));
            } else if (response.equals("Fail")) {
                JOptionPane.showMessageDialog(frame, "Username is already taken, please try another.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else if (response.equals("Invalid Int")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (response.equals("Invalid")) {
                JOptionPane.showMessageDialog(frame, "Age can't be smaller than 0",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            } else if (response.equals("Empty")) {
                JOptionPane.showMessageDialog(frame, "One of the fields is empty, please fill them all in.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Error communicating with the server.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void writeObject() throws IOException {
        oos.writeObject("signUp");
        oos.writeObject(user);
        oos.writeObject(pass);
        oos.writeObject(age);
        oos.writeObject(gender);
        oos.flush();
    }
}
