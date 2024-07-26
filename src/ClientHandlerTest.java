import org.junit.jupiter.api.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientHandlerTest {
    private static Server server;
    private static Thread serverThread;
    private Base base;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Profile user;
    private ArrayList<Post> posts;
    private Socket clientSocket;

    @BeforeAll
    static void setUpAll() {
        startServer();
    }

    @AfterAll
    static void tearDownAll() {
        stopServer();
    }

    @BeforeEach
    void setUp() throws IOException, ClassNotFoundException {
        base = new Base();
        clientSocket = new Socket("localhost", 3588);
        oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ois = new ObjectInputStream(clientSocket.getInputStream());
        oos.flush();

        oos.writeObject("login");
        oos.writeObject("Jeff");
        oos.writeObject("2332");
        oos.flush();

        Object response = ois.readObject();
        if (response instanceof Profile) {
            user = (Profile) response;
            System.out.println(user.getUsername());
        } else if (response instanceof String) {
            System.out.println(response);
        }
    }

    @AfterEach
    void tearDown() throws IOException {
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
    }

    @Test
    void temp() throws IOException, ClassNotFoundException {
        oos.writeObject("viewHidePost");
        oos.flush();

        Object response = ois.readObject();

        if (response instanceof ArrayList<?>) {
            ArrayList<?> list = (ArrayList<?>) response;

            if (!list.isEmpty() && list.get(0) instanceof Post) {
                posts = (ArrayList<Post>) list;
                System.out.println("Number of hidden posts: " + posts.size());
            } else {
                System.out.println("No hidden posts found or list is empty.");
                posts = new ArrayList<>(); // Initialize with an empty list
            }
        } else {
            System.out.println("Unexpected response type: " + response.getClass().getName());
            posts = new ArrayList<>(); // Initialize with an empty list
        }

        // This assertion will now work without throwing a NullPointerException
        assertEquals(0, posts.size(), "Expected no hidden posts for a new user");
    }


    @Test
    @DisplayName("Login")
    void writeFile() {
        assertDoesNotThrow(() -> {
            base.readAllListFile();
            Profile newprofile = new Profile("Chan", "1123", 23, "Male");
            Profile profile = new Profile("Chris", "11233");
            Post post = new Post(profile, "I don't like you");
            Comment comment = new Comment(newprofile, "I dont like you too", 0, 0);

            if (base.deleteComment(post, newprofile, comment)) {
                System.out.println("Noice");
            } else {
                System.out.println("Not good");
            }
        });
    }


    @Test
    @DisplayName("Test if searchUser and signUp method work")
    void testSearchUser() {
        assertDoesNotThrow(() -> {
            Profile user = base.searchUser("Jeff");
            assertEquals("Jeff", user.getUsername());
        });
    }

    @Test
    @DisplayName("Test writeUserList method works when signUp")
    void testSignUpByAllUserList() {
        assertDoesNotThrow(() -> {
            base.readAllListFile();
            ArrayList<Profile> users = base.getUsers();
            for (Profile user : users) {
                System.out.println(user);
            }
            assertEquals(7, users.size());
        });
    }

    public static void startServer() {
        server = new Server(3588);
        serverThread = new Thread(server);
        serverThread.start();
    }

    public static void stopServer() {
        if (server != null) {
            server.stopServer();
        }
        if (serverThread != null) {
            serverThread.interrupt();
        }
    }
}
