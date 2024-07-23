import javax.swing.*;
import java.io.*;

public class ApplicationGUI implements Runnable{
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Profile myProfile;

    public ApplicationGUI(ObjectInputStream in, ObjectOutputStream out, Profile myProfile) {
        this.in = in;
        this.out = out;
        this.myProfile = myProfile;
    }

    public void run() {
        JFrame frame = new JFrame("Dunstagram");
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
