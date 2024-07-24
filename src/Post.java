import java.io.Serializable;
import java.util.ArrayList;

/**
 * Team Project - Post
 * <p>
 * Post class that has attributes like poster, message, upvote, downvote, and comments
 * It contains methods that update the post's attributes.
 * </p>
 */

public class Post implements IPost, Serializable {
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
        this.comments = new ArrayList<>();
        this.numComments = 0;
    }

    public Post() {
        this.poster = null;
        this.message = null;
        this.upvote = 0;
        this.downvote = 0;
        this.numComments = 0;
        this.comments = new ArrayList<>();
    }

    public String toString() {
        String str = "";
        str += poster.getUsername() + "_";
        str += message + "_";
        str += upvote + "_";
        str += downvote + "_";

        for (Comment i : comments) {
            str += i.toString();
        }
        return str;
    }

    public Post makePost(String postInfo, ArrayList<Profile> users) throws UserNotFoundException {
        String[] parts = postInfo.split("_");
        Profile writer = new Profile(parts[0]);
        String msg = parts[1];
        int upvotes = Integer.parseInt(parts[2]);
        int downvotes = Integer.parseInt(parts[3]);
        ArrayList<Comment> commentList = new ArrayList<Comment>();

        for (int i = 4; i < parts.length; i += 4) {
            Profile commenter = new Profile(parts[i]);
            String message = parts[i + 1];
            int commentUpvotes = Integer.parseInt(parts[i + 2]);
            int commentDownvotes = Integer.parseInt(parts[i + 3]);

            Comment c = new Comment(commenter, message, commentUpvotes, commentDownvotes);
            commentList.add(c);
        }
        Post post = null;
        for (Profile user : users) {
            if (parts[0].equals(user.getUsername())) {
                post = new Post(user, msg, upvotes, downvotes, commentList);
                user.addMyPost(post);
            }
        }
        return post;
    }

    public boolean equals(Post post) {
        return post.poster.getUsername().equals(poster.getUsername()) && post.getMessage().equals(message);
    }

    public Comment addComment(Profile commenter, String content) {
        Comment comment = new Comment(commenter, content);
        comments.add(comment);
        numComments++;
        commenter.addMyPost(comment);
        return comment;
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
        comment.getCommenter().removeMyPost(comment);
        this.numComments--;
    }

    public void addUpvote() {
        upvote++;
    }

    public void addDownvote() {
        downvote++;
    }

    /**
     * Getters and setters
     */
    public String getMessage() {
        return message;
    }

    public int getUpvotes() {
        return upvote;
    }

    public int getDownvotes() {
        return downvote;
    }

    public Profile getPoster() {
        return poster;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public int getNumComments() {
        return numComments;
    }// get the number of the comments of the posts.

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDownvotes(int downvote) {
        this.downvote = downvote;
    }

    public void setUpvotes(int upvote) {
        this.upvote = upvote;
    }


}
