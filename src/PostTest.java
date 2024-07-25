import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    private Profile poster;
    private Profile commenter;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        poster = new Profile("posterUser");
        commenter = new Profile("commenterUser");
        post = new Post(poster, "This is a test post");
        comment = new Comment(commenter, "This is a test comment", 0, 0);
    }

    @Test
    public void temp() {
        Profile chris = new Profile("Chris", "11233", 23, "Male");
        Post post = new Post(chris, "Hey");


    }

    @Test
    public void testCreatePostWithMinimalConstructor() {
        System.out.println(post.getPoster().getUsername());
        assertEquals("posterUser", post.getPoster().getUsername());
        assertEquals("This is a test post", post.getMessage());
        assertEquals(0, post.getUpvotes());
        assertEquals(0, post.getDownvotes());
        assertEquals(0, post.getComments().size());
    }

    @Test
    public void testCreatePostWithFullConstructor() {
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Post fullPost = new Post(poster, "Full constructor post", 10, 2, comments);

        assertEquals("posterUser", fullPost.getPoster().getUsername());
        assertEquals("Full constructor post", fullPost.getMessage());
        assertEquals(10, fullPost.getUpvotes());
        assertEquals(2, fullPost.getDownvotes());
        assertEquals(1, fullPost.getComments().size());
    }


    @Test
    public void testAddComment() {
        post.addComment(commenter, "This is another test comment");
        assertEquals(1, post.getComments().size());
    }

    @Test
    public void testDeleteComment() {
        post.addComment(commenter, "Comment to delete");
        Comment commentToDelete = post.getComments().get(0); // Assuming you have a method to get the comments list
        post.deleteComment(commentToDelete);
        assertEquals(0, post.getComments().size());
    }

    @Test
    public void testToString() {
        String expectedString = "posterUser_This is a test post_0_0_";
        assertEquals(expectedString, post.toString());
    }

    @Test
    public void testToStringFileFormat() {
        post.addComment(commenter, "File format comment");
        String expectedString = "posterUser_This is a test post_0_0_commenterUser_File format comment_0_0";
        assertEquals(expectedString, post.toString());
    }
}
