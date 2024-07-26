import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class ViewProfileGUI implements IViewProfileGUI, Runnable {
    private ArrayList<String> userInfo;

    public ViewProfileGUI(ArrayList<String> userInfo) {
        this.userInfo = userInfo;

    }
    public void run() {
        showProfile();
    }

    private void showProfile() {
        //Credits (for GUI): Utkarsh Bali
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon profilePicIcon = new ImageIcon("default_profile_pic.png");
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

        JOptionPane.showMessageDialog(null, profilePanel, "My Profile",
                JOptionPane.INFORMATION_MESSAGE);
    }



}