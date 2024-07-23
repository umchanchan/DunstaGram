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

  /*  public SignUpGUI() { //just so i can test

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SignUpGUI());

    }*/
    public void run() {

       /* try {
            //bfr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          //  pw = new PrintWriter(clientSocket.getOutputStream(), true);
            pw = new PrintWriter("Hello");

        } catch (Exception e) { //IOException
                e.printStackTrace();
                return;
            }*/

        JFrame frame = new JFrame();
        frame.setTitle("Sign Up Menu");
        Container content = frame.getContentPane();
        content.setLayout(new FlowLayout());

        frame.setSize(360, 250);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JTextField userField = new JTextField(10);
        JButton userButton = new JButton("Enter");
        JLabel userLabel = new JLabel("Username");
        userLabel.setText("Enter a Username: ");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setText("Enter a Password: ");
        JTextField passField = new JTextField(10);
        JButton passButton = new JButton("Enter");

        JLabel ageLabel = new JLabel("Age");
        ageLabel.setText("Enter your Age:       ");
        JTextField ageField = new JTextField(10);
        JButton ageButton = new JButton("Enter");

        JLabel genderLabel = new JLabel("Gender");
        genderLabel.setText("Enter your Gender: ");
        JTextField genderField = new JTextField(10);
        JButton genderButton = new JButton("Enter");

        JButton makeAccountButton = new JButton("Make Account"); //NO LISTENER FOR THIS YET

        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JPanel agePanel = new JPanel();
        JPanel genderPanel = new JPanel();
        JPanel accPanel = new JPanel();

        userPanel.add(userLabel);
        userPanel.add(userField);
        userPanel.add(userButton);
        frame.add(userPanel);

        passPanel.add(passwordLabel);
        passPanel.add(passField);
        passPanel.add(passButton);
        frame.add(passPanel);

        agePanel.add(ageLabel);
        agePanel.add(ageField);
        agePanel.add(ageButton);
        frame.add(agePanel);

        genderPanel.add(genderLabel);
        genderPanel.add(genderField);
        genderPanel.add(genderButton);
        frame.add(genderPanel);

        accPanel.add(makeAccountButton);
        frame.add(accPanel);

            //pw.println("SignUp");
           // pw.println("Chan");
          //  pw.println("1122");
          //  pw.println("mm");
          //  pw.println("Male");
            //pw.flush();

            //String line = bfr.readLine();
            //System.out.println(line);

        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                counter++;
                user = String.valueOf(userField.getText());


            }
        });

        passButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                counter++;
                pass = String.valueOf(passField.getText());
            }
        });

        ageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    age = Integer.valueOf(ageField.getText());
                    counter++;
                } catch (NumberFormatException e1) {
                    showError("Please enter a valid age!");
                }
            }
        });

        genderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                counter++;
                gender = String.valueOf(genderField.getText());
            }
        });

        makeAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (counter < 4) {
                        showError("Please fill out all fields!");
                    } else {
                        writeObject();
                        frame.dispose();
                    }

                } catch (IOException ex) {
                    return;
                }
            }
        });

    }

    public void showError(String s) {
        JOptionPane.showMessageDialog(null, s, "Error", JOptionPane.ERROR_MESSAGE);
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
