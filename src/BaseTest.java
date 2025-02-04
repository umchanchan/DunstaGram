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
    void temp2() throws IOException {
        base.readAllListFile();

    }

    @Test
    void temp() throws IOException, UserNotFoundException {
        Profile chris = new Profile("Chris", "11233", 23, "Male");

        base.readAllListFile();

        Profile user = base.login("Chris", "11233");
        ArrayList<Post> userPost = user.getMyPosts();
        System.out.println(userPost.size());
        Post post2 = new Post(user, "Nah");
        Post post3 = base.makeNewPost(user, "Nh");
        for (Post userP : userPost) {
            System.out.println(userP.getMessage());
        }

        base.readAllListFile();

//        ArrayList<Profile> following = user.getFollowing();
//        System.out.println(following.size());

        ArrayList<Post> userPost2 = user.getMyPosts();
        System.out.println(userPost2.size());


        assertEquals(chris.getUsername(), user.getUsername());

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
            base.readAllListFile();
            ArrayList<Profile> users = base.getUsers();
            for (Profile user : users) {
                System.out.println(user);
            }
            assertEquals(2, users.size());
        });
    }
}
