import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewProfileGUI implements Runnable {
    private ArrayList<String> userInfo;
    private boolean myProfile;
    public ViewProfileGUI(ArrayList<String> userInfo, boolean myProfile) {
        this.userInfo = userInfo;
        this.myProfile = myProfile;
    }
    public void run() {
        JFrame frame = new JFrame();
        if (myProfile) {
            frame.setTitle("My Profile");
        } else {
            frame.setTitle("Viewing Profile");
        }
        frame.setLayout(new FlowLayout());
        int usernameLength = userInfo.get(0).length();
        if (usernameLength >= 7) {
            int frameWidth = usernameLength * 60;
            frame.setSize(frameWidth, 480);
        } else {
            frame.setSize(360, 480);
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel label1 = new JLabel(userInfo.get(0));
        label1.setFont(new Font("Sans Serif", Font.BOLD, 96));
        frame.add(label1);

        JLabel label2 = new JLabel("Age: " + userInfo.get(1));
        label2.setFont(new Font("Arial", Font.BOLD, 48));
        frame.add(label2);

        JLabel label3 = new JLabel("Gender: " + userInfo.get(2));
        label3.setFont(new Font("Arial", Font.BOLD, 48));
        frame.add(label3);

        JLabel label4 = new JLabel("Following: " + userInfo.get(3));
        label4.setFont(new Font("Arial", Font.BOLD, 48));
        frame.add(label4);

        JLabel label5 = new JLabel("Posts: " + userInfo.get(4));
        label5.setFont(new Font("Arial", Font.BOLD, 48));
        frame.add(label5);

        JButton backButton = new JButton("Back");
        frame.add(backButton);
    }

}