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
        String str = "";
        str += poster.getUsername() + ": ";
        str += message + " | Upvotes: ";
        str += upvote + " | Downvotes: ";
        str += downvote;

        if(!comments.isEmpty()) {
            str += "\n";
            str += "- Comments -";
        }
        for(Comment i: comments) {
            str += "\n";
            str += i.toString();

        }
        return str;
    }

    public String toStringFileFormat() {
        String str = "";
        str += poster.getUsername() + "_";
        str += message + "_";
        str += upvote + "_" + downvote;

        for(int i = 0; i < comments.size(); i++) {
            str += "_";
            Comment c = comments.get(i);
            str += c.getUsername() + "_" + c.getCommentContents() + "_";
            str += c.getUpvote() + "_" + c.getDownvote();


        }
        return str;
    }
    public Post makePost(String postInfo) throws UserNotFoundException {
        String[] parts = postInfo.split("_");
        Base b = new Base();

        Profile writer = b.searchUser(parts[0]);
        String msg = parts[1];
        int upvotes = Integer.parseInt(parts[2]);
        int downvotes = Integer.parseInt(parts[3]);
        ArrayList<Comment> commentList = new ArrayList<Comment>();

        for (int i = 4; i < parts.length; i += 4) {
            Profile commenter = b.searchUser(parts[i]);
            String message = parts[i+1];
            int commentUpvotes = Integer.parseInt(parts[i+2]);
            int commentDownvotes = Integer.parseInt(parts[i+3]);

            Comment c = new Comment(commenter, message, commentUpvotes, commentDownvotes);
            commentList.add(c);
        }
        return new Post(writer, msg, upvotes, downvotes, commentList);
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
        commenter.addMyPost(comment);
    }

    public void deleteComment(Comment comment) {
        comments.remove(comment);
        comment.getCommenter().removeMyPost(comment);
        this.numComments--;
    }

}
