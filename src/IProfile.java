import java.util.ArrayList;

public interface IProfile {
    String toString();

    Profile makeProfile(String userInfo);

    void addMyPost(Post post);

    void removeMyPost(Post post);

    void follow(Profile p);

    void unfollow(Profile p);

    boolean blockUser(Profile user);

    String getUsername();

    String getPassword();


    int getAge();

    String getGender();

    ArrayList<Profile> getFollowing();

    ArrayList<Post> getMyPosts();

    ArrayList<Profile> getBlockedList();

    void setUsername(String username);

    void setAge(int age);

    void setGender(String gender);

}
