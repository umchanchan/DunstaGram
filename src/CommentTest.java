import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommentTest {
    private Profile commenter;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        commenter = new Profile("commenterUser");
        comment = new Comment(commenter, "This is a test comment", 0, 0);
    }



    @Test
    public void testCreateCommentWithMinimalConstructor() {
        assertEquals("commenterUser", comment.getCommenter());
        assertEquals("This is a test comment", comment.getCommentContents());
        assertEquals(0, comment.getUpvote());
        assertEquals(0, comment.getDownvote());
    }

    @Test
    public void testCreateCommentWithFullConstructor() {
        Comment fullComment = new Comment(commenter, "Full constructor comment", 5, 1);
        assertEquals("commenterUser", fullComment.getCommenter());
        assertEquals("Full constructor comment", fullComment.getCommentContents());
        assertEquals(5, fullComment.getUpvote());
        assertEquals(1, fullComment.getDownvote());
    }

    @Test
    public void testAddUpvote() {
        comment.addUpvote();
        assertEquals(1, comment.getUpvote());
    }


    @Test
    public void testAddDownvote() {
        comment.addDownvote();
        assertEquals(1, comment.getDownvote());
    }



    @Test
    public void testToString() {
        String expectedString = "commenterUser_This is a test comment_0_0_";
        assertEquals(expectedString, comment.toString());
    }

    @Test
    public void testSetCommentContents() {
        comment.setCommentContents("Updated comment contents");
        assertEquals("Updated comment contents", comment.getCommentContents());
    }

    @Test
    public void testSetUpvote() {
        comment.addUpvote();
        assertEquals(1, comment.getUpvote());
    }

    @Test
    public void testSetDownvote() {
        comment.addDownvote();
        assertEquals(1, comment.getDownvote());
    }

    @Test
    public void testGetCommenter() {
        Profile returnedCommenter = comment.getCommenter();
        System.out.println("Returned commenter: " + returnedCommenter);
        System.out.println("Returned commenter class: " + returnedCommenter.getClass().getName());
        assertEquals(commenter, returnedCommenter);
        assertEquals("commenterUser", returnedCommenter.getUsername());
    }


    @Test
    public void testEquality() {
        Comment anotherComment = new Comment(commenter, "This is a test comment", 0, 0);
        assertEquals(comment, anotherComment);

        anotherComment.addUpvote();
        assertNotEquals(comment, anotherComment);
    }


}
