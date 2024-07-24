import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * This class serves a client GUI
 */
import java.util.Scanner;

/**
 *
 */
public class Client implements IClient {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in); //to be deleted
        Socket clientSocket;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        try {
            clientSocket = new Socket("localhost", 3588);

            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());

            oos.flush();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Profile p;

        while(true) {
            InitialGUI c = new InitialGUI(ois, oos);
            SwingUtilities.invokeLater(c);

            String option = (String) ois.readObject();
            if (option.equals("signUp")) {
                SwingUtilities.invokeLater(new SignUpGUI(ois, oos));//any new frame GUI

                String outcome = (String) ois.readObject();
                System.out.println(outcome);

                if (outcome.equals("Success")) {
                    JOptionPane.showMessageDialog(null, "Successfully signed up!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    continue;
                } else {
                    JOptionPane.showMessageDialog(null, "Username already taken!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (option.equals("login")) {
                SwingUtilities.invokeLater(new LoginGUI(ois, oos));

                Object outcome = ois.readObject();

                if (outcome instanceof Profile) {
                    p = (Profile) outcome;
                    JOptionPane.showMessageDialog(null, "Successfully logged in!",
                            "Success", JOptionPane.INFORMATION_MESSAGE);

                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }


            }


        }

        JOptionPane.showMessageDialog(null, "Welcome to Dunstagram!");
        oos.flush();
        SwingUtilities.invokeLater(new SettingsGUI(ois, oos, p));






        oos.close();
        ois.close();
        clientSocket.close();
    }

    public void test(ObjectInputStream ois, ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        oos.writeObject("makeComment");
//            Profile chan = new Profile("Chan", "1123", 23, "Male");
        Profile profile = new Profile("Chris", "11233");
        Post post = new Post(profile, "Thank you");
        oos.writeObject(post);
        oos.writeObject("Thank you too");
        oos.flush();

        Object obj = ois.readObject();
        if (obj instanceof String) {
            System.out.println(obj);
        } else if (obj instanceof Profile) {
            System.out.println(((Profile) obj).getUsername());
        } else if (obj instanceof Comment) {
            System.out.println(((Comment) obj).getCommenter().getUsername());
            System.out.println(((Comment) obj).getCommentContents());
        }//moved testing code here (old phase 1 stuff)

    }
}
