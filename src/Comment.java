public class Comment extends Post implements IComment {
    private Profile commenter;
    private String contents;
    private int upvote;
    private int downvote;
    private String username;


    public Comment(Profile commenter, String contents) {
        super();
        this.commenter = commenter;
        this.username = commenter.getUsername();
        this.contents = contents;
        this.upvote = 0;
        this.downvote = 0;
    }

    public void addUpvote() {
        upvote++;
    }
    public void subtractUpvote() {
        upvote--;
    }
    public void addDownvote() {
        downvote++;
    }
    public void subtractDownvote() {
        downvote--;
    }
    public String getUsername() {
        return username;
    }
    public String getCommentContents() {
        return contents;
    }
    public int getUpvote() {
        return upvote;
    }
    public int getDownvote() {
        return downvote;
    }
}
