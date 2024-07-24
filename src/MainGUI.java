import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainGUI implements Runnable {

    private Profile user;
    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public MainGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) {
        this.user = user;
        this.ois = ois;
        this.oos = oos;
    }


    @Override
    public void run() {

    }
}
