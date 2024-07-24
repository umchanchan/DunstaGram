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
    void temp() throws IOException {
        Profile chris = new Profile("Chris", "11233", 23, "Male");
        Post post = new Post(chris, "Hey");

        base.readPostListFile();



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
            Profile newprofile = new Profile("Chan", "1123", 23, "Male");
            Profile profile = new Profile("Chris", "11233");
            Post post = new Post(profile, "I don't like you");
            Comment comment = new Comment(newprofile, "I dont like you too");

            if (base.deleteComment(post, newprofile, comment)) {
                System.out.println("Noice");
            } else {
                System.out.println("Not good");
            }


//            if ((comment = base.makeComment(post, newprofile, "**** you too"))!= null) {
//                System.out.println(comment.getCommentContents());
//            } else {
//                System.out.println("****** up");
//            }
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
