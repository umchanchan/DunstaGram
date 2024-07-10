public interface IComment {
    public void addUpvote();
    public void subtractUpvote();
    public void addDownvote();
    public void subtractDownvote();
    public String getUsername();
    public String getCommentContents();
    public int getUpvote();
    public int getDownvote();
}
