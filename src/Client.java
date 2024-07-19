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
            clientSocket = new Socket("localhost", 3555);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

        String action = sc.nextLine(); //whatever input is

        oos.writeObject(action);
        oos.flush();

        String result = (String) ois.readObject();

        if(result.equals("Sucess")) {
            System.out.println("Sucessfully done " + result);
        } else if (result.equals("Fail")){
            System.out.println(result + "failed");
        }
//        SwingUtilities.invokeLater(new SignUpGUI(clientSocket));//any new frame GUI
    }
}
