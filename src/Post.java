public class Post {

    String message;
    int upvotes;
    int downvotes;
    Profile poster;
    int views;
    boolean viewed;
    int comments;
    String commentContent;

    public Post(String message, int upvotes, int downvotes, Profile poster, int views, boolean viewed, int comments,
                String commentContent) {
        this.message = message;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
        this.poster = poster;
        this.views = views;
        this.viewed = viewed;
        this.comments = comments;
        this.commentContent = commentContent;
    }

    public Post() {
        this.message = "Input your ideas here...";
        this.upvotes = 0;
        this.downvotes = 0;
        this.views = 0;
        this.viewed = false;
        this.comments = 0;
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
    }//Whether the post is viewed by the user.

    public int getComments() {
        return comments;
    }// get the number of the comments of the posts.

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public void deleteComments() {
        this.comments = comments - 1;
    }


}
