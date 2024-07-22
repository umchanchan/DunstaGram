import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class SignUpGUI extends JComponent implements Runnable {
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private String user;
    private String pass;
    private int age;
    private String gender;
    private int counter = 0;

    public SignUpGUI(ObjectInputStream ois, ObjectOutputStream oos) {
        this.ois = ois;
        this.oos = oos;
    }

    /* public SignUpGUI() { //just so i can test
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SignUpGUI());
    } */

    public void run() {
        JFrame frame = new JFrame();
        frame.setTitle("Sign Up Menu");
        Container content = frame.getContentPane();
        content.setLayout(new FlowLayout());

        frame.setSize(360, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JTextField userField = new JTextField(10);
        JLabel userLabel = new JLabel("Username");
        userLabel.setText("Enter a Username: ");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setText("Enter a Password: ");
        JTextField passField = new JTextField(10);

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setText("Enter your Age:       ");
        JTextField ageField = new JTextField(10);

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setText("Enter your Gender: ");
        JTextField genderField = new JTextField(10);

        JButton makeAccountButton = new JButton("Make Account"); //NO LISTENER FOR THIS YET

        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel agePanel = new JPanel();
        JPanel genderPanel = new JPanel();
        JPanel accPanel = new JPanel();

        userPanel.add(userLabel);
        userPanel.add(userField);
        frame.add(userPanel);

        passPanel.add(passwordLabel);
        passPanel.add(passField);
        frame.add(passPanel);

        agePanel.add(ageLabel);
        agePanel.add(ageField);
        frame.add(agePanel);

        genderPanel.add(genderLabel);
        genderPanel.add(genderField);
        frame.add(genderPanel);

        accPanel.add(makeAccountButton);
        frame.add(accPanel);

        // pw.println("SignUp");
        // pw.println("Chan");
        // pw.println("1122");
        // pw.println("mm");
        // pw.println("Male");
        // pw.flush();

        // String line = bfr.readLine();
        // System.out.println(line);

        makeAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve and validate inputs
                    user = userField.getText();
                    pass = passField.getText();
                    try {
                        age = Integer.parseInt(ageField.getText());
                    } catch (NumberFormatException ex) {
                        showError();
                        return;
                    }
                    gender = genderField.getText();

                    if (user.isEmpty() || pass.isEmpty() || gender.isEmpty()) {
                        showError();
                    } else {
                        writeObject();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void showError() {
        JOptionPane.showMessageDialog(null, "Invalid input! Please ensure all fields are filled correctly.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void writeObject() throws IOException {
        System.out.println("HEre");
        oos.writeObject(user);
        oos.writeObject(pass);
        oos.writeObject(Integer.toString(age));
        oos.writeObject(gender);
        oos.flush();
    }
}
