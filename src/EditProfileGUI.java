import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EditProfileGUI implements Runnable {
    private Profile p;
    private JTextField age;
    private JTextField gender;
    private JTextField password;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public EditProfileGUI(Profile p, ObjectInputStream ois, ObjectOutputStream oos) {
        this.p = p;
        this.oos = oos;
        this.ois = ois;
    }


    public void run() {
        JFrame fields = new JFrame("Editing my Profile");
        fields.setSize(340, 200);
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
        fields.setVisible(true);

        JButton done = new JButton("Submit");
        done.setFont(new Font("Arial", Font.BOLD, 18));
        Dimension d1 = new Dimension(200, 50);
        done.setSize(d1);
        done.setPreferredSize(d1);


        fields.add(done);

        done.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String outcome;
                try {
                    oos.writeObject("editProfile");
                    oos.writeObject(p.getUsername());
                    oos.writeObject(age.getText());
                    oos.writeObject(gender.getText());
                    outcome = (String) ois.readObject();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                if (outcome.equals("Invalid")) {
                    JOptionPane.showMessageDialog(null, "Invalid inputs!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (outcome.equals("Success")) {

                    p.setAge(Integer.parseInt(age.getText()));
                    p.setGender(gender.getText());
                    JOptionPane.showMessageDialog(null, "Successfully changed fields!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    fields.dispose();
                }

            }

        });



    }

}
