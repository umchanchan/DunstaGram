import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class mainly opens the server and prepares the server until the client connects
 */
public class Server implements IServer {

    private boolean running = false;
    private ServerSocket serverSocket;
    private final int port;
    private Base base;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public Server(int port) {
        this.base = null;
        this.port = port;
    }

    public static void main(String[] args) {
        Server server = new Server(3588); //port number not set
        new Thread(server).start();
    }


    public void run() {
        startServer();
    }

    //start the server method and when the user
    public void startServer() {

        try {
            serverSocket = new ServerSocket(port);
            running = true;
            base = new Base();
            base.readUserListFile();
            base.readPostListFile();
            base.readHidePostListFile();

            while (running) {
                Socket clientSocket = serverSocket.accept();
                //every time a user connects, each user is going to be
                //assigned to one clientHandler
                pool.execute(new ClientHandler(clientSocket, base));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stopServer();
        }

    }

    //stop the server method
    public void stopServer() {
        running = false;
        pool.shutdown();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
