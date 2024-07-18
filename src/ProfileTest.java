import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfileTest {
    Profile profile;
    Profile profile1;
    Profile profile2;

    @BeforeEach
    public void set() {
        Profile profile = new Profile("Bob123", "1234", 55, "Male");
        Profile profile1 = new Profile("Jane3", "1234", 30, "Female");
        Profile profile2 = new Profile("Dave111", "444", 29, "Male");
        this.profile = profile;
    }

    @Test
    public void testToString() throws IOException {
        String s = profile.toString();
        String s1 = profile1.toString();
        String s2 = profile2.toString();

        assertEquals("Bob123_1234_55_Male", s);
        assertEquals("Jane3_1234_30_Female", s1);
        assertEquals("Male", s2);
    }

    @Test
    public void testMethods() {
        var blockList = new ArrayList<Profile>();
        blockList.add(profile);
        blockList.add(profile1);

        Profile p = profile.makeProfile("Dave9_2222_30_Male_==+==Bob123,Jane3");
        assertEquals("Dave9", p.getUsername());
        assertEquals("2222", p.getPassword());
        assertEquals(30, p.getAge());
        assertEquals("Male", p.getGender());
        assertEquals(blockList, p.getBlockedList());
        assertFalse(p.equals(profile1));
        assertTrue(p.equals(p));

        profile1.follow(profile2);
        profile.follow(profile2);

        assertEquals(2, profile2.getFollowers().size());

        profile.unfollow(profile2);

        assertEquals(1, profile2.getFollowers().size());


    }

    @Test
    public void testPostMethods() {
        Post p = new Post(profile, "NOOOO!");
        Comment c = new Comment(profile1, "YES!", p);
        assertEquals(1, c.getComments().size());
        assertEquals(1, profile.getMyPosts().size());
    }


}
