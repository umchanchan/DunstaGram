import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EditProfileGUI implements IBlockListGUI, Runnable {
    private Profile p;
    private JTextField age;
    private JTextField gender;
    private String password;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public EditProfileGUI(Profile p, ObjectInputStream ois, ObjectOutputStream oos) {
        this.p = p;
        this.oos = oos;
        this.ois = ois;
        password = "";
    }


    public void run() {
        JFrame fields = new JFrame("Editing my Profile");
        fields.setSize(340, 340);
        fields.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel ageLabel = new JLabel("Enter your age: ");
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        Dimension d = new Dimension(0, 40);
        ageLabel.setSize(d);
        JLabel genderLabel = new JLabel("Enter your gender: ");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        age = new JTextField(4);
        age.setFont(new Font("Arial", Font.PLAIN, 18));
        age.setSize(d);
        age.setPreferredSize(d);
        gender = new JTextField(6);
        gender.setSize(d);
        gender.setPreferredSize(d);
        gender.setFont(new Font("Arial", Font.PLAIN, 18));
        fields.add(ageLabel);
        fields.add(age);
        fields.add(genderLabel);
        fields.add(gender);


        JButton done = new JButton("Submit");
        done.setFont(new Font("Arial", Font.BOLD, 18));
        Dimension d1 = new Dimension(200, 50);
        done.setSize(d1);
        done.setPreferredSize(d1);

        JButton changePassword = new JButton("Change Password");
        changePassword.setFont(new Font("Arial", Font.BOLD, 18));
        changePassword.setSize(d1);
        changePassword.setPreferredSize(d1);

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.BOLD, 18));
        back.setSize(d1);
        back.setPreferredSize(d1);

        fields.add(changePassword);
        fields.add(back);
        fields.add(done);
        fields.setVisible(true);
        fields.setLocationRelativeTo(null);
        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String outcome;
                try {
                    oos.writeObject("editProfile");
                    oos.writeObject(p.getUsername());
                    oos.writeObject(age.getText());
                    oos.writeObject(gender.getText());
                    oos.writeObject(password);

                    outcome = (String) ois.readObject();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                if (outcome.equals("Invalid")) {
                    JOptionPane.showMessageDialog(null, "Please check your inputs!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (outcome.equals("Success")) {

                    if (!(age.getText() == null || age.getText().isEmpty())) {
                        p.setAge(Integer.parseInt(age.getText()));
                    }

                    if (!(gender.getText() == null || gender.getText().isEmpty())) {
                        p.setGender(gender.getText());
                    }
                    JOptionPane.showMessageDialog(null, "Successfully changed edited fields!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    fields.dispose();
                }

            }

        });

        changePassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pass = changePasswordGUI();
                if (pass == null || pass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Invalid; no password entered");
                } else {
                    boolean correct;
                    try {
                        correct = confirmPasswordGUI(pass);
                    } catch (NullPointerException nul) {
                        correct = false;
                    }
                    if (!correct) {
                        JOptionPane.showMessageDialog(null, "Password does not match.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Successfully entered!");
                        password = pass;
                    }
                }

            }

        });
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fields.dispose();

            }

        });



    }

    public String changePasswordGUI() {
        return JOptionPane.showInputDialog(null, "Enter your new password", "Changing Password",
                JOptionPane.QUESTION_MESSAGE);
    }

    public boolean confirmPasswordGUI(String pass) {
        String newPass = JOptionPane.showInputDialog(null, "Please confirm your new password",
                "Changing Password", JOptionPane.QUESTION_MESSAGE);
        return newPass.equals(pass);
    }

}
