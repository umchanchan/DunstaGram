import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SignUpGUI implements Runnable {
    private Socket clientSocket;

    public SignUpGUI(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public void run() {
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
            pw.println("SignUp");
            pw.println("Chan");
            pw.println("1122");
            pw.println("mm");
            pw.println("Male");
            pw.flush();

            String line = bfr.readLine();
            System.out.println(line);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
