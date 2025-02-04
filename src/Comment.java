import java.io.Serializable;

/**
 * Team Project - Comment
 * <p>
 * Comment class that has attributes like commenter, contents, upvote, downvote
 * </p>
 */

public class Comment extends Post implements IComment, Serializable {
    private Profile commenter;
    private String contents;
    private int upvote;
    private int downvote;

    public Comment(Profile commenter, String contents, int upvotes, int downvotes) {
        this.commenter = commenter;
        this.contents = contents;
        this.upvote = upvotes;
        this.downvote = downvotes;
    }

    public String toString() {
        String str = commenter.getUsername() + "_" + contents + "_";
        str += upvote + "_" + downvote + "_";
        return str;
    }



    public boolean equals(Comment comment) {
        return comment.commenter.equals(commenter) && comment.contents.equals(contents);
    }

    public void addUpvote() {
        upvote++;
    }

    public void addDownvote() {
        downvote++;
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

    public void setCommentContents(String contents) {
        this.contents = contents;
    }


}
