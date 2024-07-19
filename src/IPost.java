import java.util.ArrayList;

public interface IPost {
    String toString();

    Post makePost(String postInfo) throws UserNotFoundException;

    Comment addComment(Profile commenter, String content);

    void deleteComment(Comment comment);

    void addUpvote();

    void addDownvote();

    String getMessage();

    void setMessage(String message);

    int getUpvotes();

    void setUpvotes(int upvotes);

    int getDownvotes();

    ArrayList<Comment> getComments();

    int getNumComments();

    void setDownvotes(int downvotes);

    Profile getPoster();


}
