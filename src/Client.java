import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * This class serves a client GUI
 */
import java.util.Scanner;

public class Client implements IClient {
    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in); //to be deleted
        Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", 3588);

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            oos.flush();
            oos.writeObject("follow");
            Profile chan = new Profile("Chan", "1123", 23, "Male");
            oos.writeObject(chan);
            oos.flush();

            Object obj = ois.readObject();
            if (obj instanceof String) {
                System.out.println(obj);
            } else if (obj instanceof Profile) {
                System.out.println("Success");
            }


//        SwingUtilities.invokeLater(new SignUpGUI(clientSocket));//any new frame GUI
            oos.close();
            ois.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
