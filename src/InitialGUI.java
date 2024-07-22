import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InitialGUI implements Runnable {
    ObjectInputStream in;
    ObjectOutputStream out;

    public InitialGUI(ObjectInputStream ois, ObjectOutputStream oos) {
        in = ois;
        out = oos;
    }

    public void run() {
        JFrame frame = new JFrame();
        frame.setTitle("Login Menu");
        Container content = frame.getContentPane();
        content.setLayout(new FlowLayout());

        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        Panel window = new Panel();
        JLabel welcomeLabel = new JLabel("Welcome to Dunstagram!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        window.add(welcomeLabel);
        content.add(window);

        JButton signUpButton = new JButton("Sign Up");
        content.add(signUpButton);
        JButton loginButton = new JButton("Login");
        content.add(loginButton);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                try {
                    out.writeObject("signUp");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    out.writeObject("login");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



    }

}
