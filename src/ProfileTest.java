import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileTest {
    Profile profile;
    Profile profile1;
    Profile profile2;

    Base base = new Base();

    @BeforeEach
    public void set() throws IOException, UserNotFoundException {
        base.signUp("Bob123", "1234", 55, "Male");
        Profile profile = base.searchUser("Bob123");
        Profile profile1 = new Profile("Jane3", "1234", 30, "Female");
        Profile profile2 = new Profile("Dave111", "444", 29, "Male");
        this.profile = profile;
        this.profile1 = profile1;
        this.profile2 = profile2;

    }
    @Test
    public void testtt() {
        Post post = new Post(profile, "Hey");
        profile.addMyPost(post);

        profile1.follow(profile);

        ArrayList<Post> friendPost = profile1.getFollowingPosts();
        for (Post post2 : friendPost) {
            System.out.println(post2.getPoster());
            System.out.println(post2.getMessage());
        }

    }

    @Test
    public void testToString() throws IOException {
        String s = profile.toString();
        String s1 = profile1.toString();
        String s2 = profile2.toString();

        assertEquals("Bob123_1234_55_Male_", s);
        assertEquals("Jane3_1234_30_Female_", s1);
        assertEquals("Dave111_444_29_Male_", s2);
    }

    @Test
    public void testMethods() {
        var blockList = new ArrayList<Profile>();
        blockList.add(profile1);

        Profile p = profile.makeProfile("Dave9_2222_30_Male_Bob123====Jane3");
        assertEquals("Dave9", p.getUsername());
        assertEquals("2222", p.getPassword());
        assertEquals(30, p.getAge());
        assertEquals("Male", p.getGender());
        assertEquals(blockList.get(0).getUsername(), p.getBlockedList().get(0));
        assertFalse(p.equals(profile1));
        assertTrue(p.equals(p));

        profile1.follow(profile2);
        profile.follow(profile2);

        assertEquals(1, profile1.getFollowing().size());

        profile.unfollow(profile2);

        assertEquals(0, profile.getFollowing().size());


    }

    @Test
    public void testPostMethods() {
        Post p = new Post(profile, "NOOOO!");
        ArrayList<Post> all = new ArrayList<>();
        all.add(p);

        NewsFeed n = new NewsFeed(profile1);

        n.comment(p, "YES");

        assertEquals(1, p.getComments().size());
        assertEquals(1, profile.getMyPosts().size());
    }


}
