public class Post {

    String message;
    int upvotes;
    int downvotes;
    Profile poster;
    int views;
    boolean viewed;
    int comments;

    public Post(String message, int upvotes, int downvotes, Profile poster, int views, boolean viewed, int comments) {
        this.message = message;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.poster = poster;
        this.views = views;
        this.viewed = viewed;
        this.comments = comments;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public Profile getPoster() {
        return poster;
    }


    public boolean isViewed() {
        return this.viewed;
    }

    public int getComments() {
        return comments;
    }

    public void deleteComments() {
        this.comments = comments - 1;
    }


}
