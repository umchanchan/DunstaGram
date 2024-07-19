import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {
    private Base base;

    @BeforeEach
    void setUp() throws IOException {
        base = new Base();
        base.signUp("Jeff", "2332", 22, "Male");
    }

    @Test
    @DisplayName("Test if searchUser and signUp method work")
    void testFileWriting() {
        assertDoesNotThrow(() -> {
            Profile user = base.searchUser("Jeff");
            assertEquals("Jeff", user.getUsername());
        });
    }

    @Test
    @DisplayName("Login")
    void writeFile() {
        assertDoesNotThrow(() -> {
            base.readUserListFile();


            ArrayList<Profile> temp = base.getUsers();
            Profile profile = base.login("Chan", "1123");
            Profile to = new Profile("Chris", "11233", 23, "Male");
            if (base.follow(profile, to)) {
                System.out.println("Success");
            } else {
                System.out.println("fuck");
            }
//            assertEquals("Chan", profile.getUsername());
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
            base.readUserListFile();
            ArrayList<Profile> users = base.getUsers();
            for (Profile user : users) {
                System.out.println(user);
            }
            assertEquals(2, users.size());
        });
    }
}
