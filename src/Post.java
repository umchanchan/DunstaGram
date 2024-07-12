import java.util.ArrayList;


public class Post implements IPost {
    private String message;
    private int upvote;
    private int downvote;
    private Profile poster;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int numComments;


    public Post(Profile poster, String message, int upvote, int downvote, ArrayList<Comment> comments) {
        this.message = message;
        this.upvote = upvote;
        this.downvote = downvote;
        this.poster = poster;
        this.numComments = comments.size();
        this.comments = comments;
    }

    public Post(Profile poster, String message) {
        this.poster = poster;
        this.message = message;
        this.upvote = 0;
        this.downvote = 0;
        this.numComments = 0;
    }

    public Post() {
        this.poster = null;
        this.message = null;
        this.upvote = 0;
        this.downvote = 0;
        this.numComments = 0;
    }

    public String toString() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUpvotes() {
        return upvote;
    }

    public void setUpvotes(int upvote) {
        this.upvote = upvote;
    }

    public int getDownvotes() {
        return downvote;
    }

    public void setDownvotes(int downvote) {
        this.downvote = downvote;
    }

    public Profile getPoster() {
        return poster;
    }

    public int getComments() {
        return numComments;
    }// get the number of the comments of the posts.

    public void addComment(Profile commenter, String content) {
        Comment comment = new Comment(commenter, content);
        comments.add(comment);
        numComments++;
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
        this.numComments--;
    }

}
