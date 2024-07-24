import java.net.*;
import java.io.*;
import javax.swing.*;

/**
 * This class serves a client GUI
 */
public class Client implements IClient {
    public static void main(String[] args) {
        Socket clientSocket;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            clientSocket = new Socket("localhost", 3588);
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(clientSocket.getInputStream());

            JOptionPane.showMessageDialog(null, "Welcome to DunstaGram!", "DunstaGram",
                    JOptionPane.INFORMATION_MESSAGE);
            SwingUtilities.invokeLater(new LoginGUI(ois, oos));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
