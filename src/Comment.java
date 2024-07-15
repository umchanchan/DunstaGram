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

    public Comment(Profile commenter, String contents, int upvotes, int downvotes) {
        this.commenter = commenter;
        this.contents = contents;
        this.upvote = upvotes;
        this.downvote = downvotes;
    }

    public String toString() {
        String str = commenter.getUsername() + "_" + contents + "_";
        str += "_" + upvote + "_" + downvote;
        return str;
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
    public Profile getCommenter() {
        return commenter;
    }
}
