import java.util.ArrayList;


public class Post implements IPost {
    private String message;
    private int upvote;
    private int downvote;
    private Profile poster;
    private ArrayList<Comment> comments = new ArrayList<>();
    private Comment comment;
    private int numComments;
    private String commentContent;


    public Post(String message, int upvote, int downvote, Profile poster, int numComments,
                String commentContent) {
        this.message = message;
        this.upvote = upvote;
        this.downvote = downvote;
        this.poster = poster;
        this.numComments = numComments;
        this.commentContent = commentContent;
    }

    public Post(String message, Profile poster) {
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

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

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
