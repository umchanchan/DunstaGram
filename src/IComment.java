public interface IComment {

    String toString();

    boolean equals(Comment comment);

    void addUpvote();

    void addDownvote();

    String getCommentContents();

    int getUpvote();

    int getDownvote();

    Profile getCommenter();

}
