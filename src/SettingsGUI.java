import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static java.awt.Window.getWindows;

public class SettingsGUI implements Runnable {
    private Profile profile;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private MainGUI obj;

    public SettingsGUI(ObjectInputStream in, ObjectOutputStream out, Profile p, MainGUI obj) {
        profile = p;
        this.in = in;
        this.out = out;
        this.obj = obj;
    }


    public void run() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
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
                System.out.println(userInfo);
                SwingUtilities.invokeLater(new ViewProfileGUI(userInfo, true));


            }
        });

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?",
                        "Logout", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    obj.logout();
                    try {
                        out.writeObject("Exit");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                }


            }
        });


    }
}
