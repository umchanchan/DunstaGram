import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * This class serves a client GUI
 */

public class Client {
    public static void main(String[] args) {
        Socket clientSocket;
        try {
            clientSocket = new Socket("localhost", 3555);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        SwingUtilities.invokeLater();//any new frame GUI
    }
}
