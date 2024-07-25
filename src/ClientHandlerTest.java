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
    private Base base;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Profile user;
    private ArrayList<Post> posts;
    @BeforeEach
    void setUp() throws IOException, ClassNotFoundException {
        base = new Base();
        Socket clientSocket = new Socket("localhost", 3588);
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

    @Test
    void temp() throws IOException, ClassNotFoundException {
        oos.writeObject("viewHidePost");
        oos.flush();

        Object response = ois.readObject();

        if (response instanceof ArrayList<?>) {
            ArrayList<?> list = (ArrayList<?>) response;

            if (!list.isEmpty() && list.get(0) instanceof Post) {
                posts = (ArrayList<Post>) list;
            }
        } else {
            System.out.println("WTF");
        }

        System.out.println(posts.size());
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
            assertEquals(2, users.size());
        });
    }
}
