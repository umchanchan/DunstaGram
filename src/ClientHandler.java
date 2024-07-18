import java.net.*;
import java.io.*;
import java.util.*;

/**
 *  Each client has a server that deals with each client's request
 */
public class ClientHandler implements IClientHandler {
    private Socket clientSocket;
    private Base base;


    public ClientHandler(Socket clientSocket, Base base) {
        this.clientSocket = clientSocket;
        this.base = base;
    }


    public void run() {
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                String clientInput = bfr.readLine();
                //To stop this thread when the user close the software.
                if (clientInput == null) {
                    pw.close();
                    bfr.close();
                    clientSocket.close();
                    return;
                }
                switch (clientInput) {
                    case "signUp" -> {
                        String username = bfr.readLine();
                        String password = bfr.readLine();
                        int age = 0;
                        try {
                            age = Integer.parseInt(bfr.readLine());
                        } catch (NumberFormatException e) {
                            pw.println("Invalid Integer");
                            pw.flush();
                        }
                        if (age < 0) {
                            pw.println("Invalid Integer");
                            pw.flush();
                        }
                        String gender = bfr.readLine();
                        if (base.signUp(username, password, age, gender)) {
                            pw.println("success");
                            pw.flush();
                        } else {
                            pw.println("fail");
                            pw.flush();
                        }
                    }
                    case "login" -> {

                    }
                    case "follow" -> {

                    }



                }


            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
