import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ViewProfileGUI implements IViewProfileGUI, Runnable {
    private ArrayList<String> userInfo;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ViewProfileGUI(ArrayList<String> userInfo, ObjectInputStream in, ObjectOutputStream out) {
        this.userInfo = userInfo;
        this.in = in;
        this.out = out;

    }
    public void run() {
        showProfile();
    }

    private void showProfile() {
        //Credits (for GUI): Utkarsh Bali
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String path = "default_profile_pic.png";
        String userPic = "ProfilePictures/" + userInfo.getFirst() + ".png";

        if (dirExists(userPic)) {
            path = userPic;
        }
        ImageIcon profilePicIcon = new ImageIcon(path);
        Image image = profilePicIcon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel profilePic = new JLabel(scaledIcon);
        profilePic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        profilePanel.add(profilePic);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        profilePanel.add(new JLabel("Username: " + userInfo.get(0)));
        profilePanel.add(new JLabel("Age: " + userInfo.get(1)));
        profilePanel.add(new JLabel("Gender: " + userInfo.get(2)));
        profilePanel.add(new JLabel("Following: " + userInfo.get(3)));
        profilePanel.add(new JLabel("Posts: " + userInfo.get(4)));

        JPanel spacer = new JPanel();
        Dimension dimension = new Dimension(profilePanel.getWidth(), 20);
        spacer.setSize(dimension);
        spacer.setPreferredSize(dimension);
        profilePanel.add(spacer);
        JButton uploadPicture = new JButton("Upload Picture");
        profilePanel.add(uploadPicture);
        uploadPicture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new UploadPicture(in, out, userInfo.get(0)));



            }
        });
        JOptionPane.showMessageDialog(null, profilePanel, "My Profile",
                JOptionPane.INFORMATION_MESSAGE);


    }

    private boolean dirExists(String file) {
        Path parent = Paths.get(file).getParent();
        return parent != null && Files.isDirectory(parent);
    }






}