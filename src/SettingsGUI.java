import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SettingsGUI implements Runnable {
    private Profile profile;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public SettingsGUI(ObjectInputStream in, ObjectOutputStream out, Profile p) {
        profile = p;
        this.in = in;
        this.out = out;

    }


    public void run() {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setTitle("Settings");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JButton viewProfile = new JButton("View My Profile");
        viewProfile.setFont(new Font("Arial", Font.PLAIN, 24));
        Dimension view = new Dimension(240, 60);
        viewProfile.setSize(view);
        viewProfile.setPreferredSize(view);
        frame.add(viewProfile);

        JButton editProfile = new JButton("Edit My Profile");
        editProfile.setFont(new Font("Arial", Font.PLAIN, 24));
        Dimension edit = new Dimension(240, 60);
        editProfile.setSize(edit);
        editProfile.setPreferredSize(edit);
        frame.add(editProfile);

        JButton blockList = new JButton("Manage Block List");
        blockList.setFont(new Font("Arial", Font.PLAIN, 24));
        Dimension block = new Dimension(240, 60);
        blockList.setSize(block);
        blockList.setPreferredSize(block);
        frame.add(blockList);

        JButton logout = new JButton("Logout");
        logout.setFont(new Font("Arial", Font.PLAIN, 24));
        Dimension log = new Dimension(240, 60);
        logout.setSize(log);
        logout.setPreferredSize(log);
        frame.add(logout);

        viewProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    out.writeObject("viewProfile");
                    out.writeObject(profile);
                    out.flush();

                } catch (IOException ex) {
                    return;
                }

                ArrayList<String> userInfo;

                try {
                    userInfo = (ArrayList<String>) in.readObject();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });




    }
}
