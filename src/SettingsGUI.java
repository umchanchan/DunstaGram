import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static java.awt.Window.getWindows;

public class SettingsGUI implements ISettingsGUI, Runnable {
    private Profile profile;
    private ObjectInputStream in;
    private ObjectOutputStream out;


    public SettingsGUI(Profile p, ObjectInputStream in, ObjectOutputStream out) {
        profile = p;
        this.in = in;
        this.out = out;
    }


    public void run() {
        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setTitle("Settings");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Window listener that closes the window
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    out.writeObject("Exit");
                    out.flush();
                    out.close();
                    in.close();
                    frame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

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

        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 24));
        Dimension back2 = new Dimension(180, 40);
        back.setSize(back2);
        back.setPreferredSize(back2);
        frame.add(back);


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
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                SwingUtilities.invokeLater(new ViewProfileGUI(userInfo));


            }
        });

        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?",
                        "Logout", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        frame.dispose();
                        SwingUtilities.invokeLater(new LoginGUI(in, out));

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        });

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(new MainGUI(profile, in, out));
            }
        });

        editProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new EditProfileGUI(profile, in, out));
            }
        });

        blockList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new BlockListGUI(in, out));

            }
        });


    }
}
