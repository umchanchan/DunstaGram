import java.util.ArrayList;

public interface IProfile {
    //public boolean signUp(String username, String password) throws IOException;

    //public boolean login(String username, String password) throws IOException, UserNotFoundException;

    //public ArrayList<String> readUserListFile() throws IOException;

   // public void writeUserListFile(ArrayList<String> userInfo) throws IOException;

   // public void reWriteUserListFile(ArrayList<String> lines) throws IOException;

    public String getUsername();

    public String getPassword();

    public ArrayList<Profile> getFollowers();

  //  public ArrayList<Profile> getFriends();

    public int getAge();

    public String getGender();

   // public ArrayList<Profile> getFriendRequests();

    public ArrayList<Post> getMyPosts();

    public ArrayList<Profile> getBlockedList();

    public void setUsername(String username);

    public boolean removeFollowers(Profile p);

   // public void setFriends(ArrayList<Profile> friends);

    public void setAge(int age);

    public void setGender(String gender);

   // public void setFriendRequests(Profile profile);

  //  public void addFriend(Profile profile);

  //  public boolean isFriends(Profile profile);

  //  public boolean acceptRequest(Profile friend) throws IOException, UserNotFoundException;

  //  public boolean rejectRequest(Profile profile);

  //  public void removeFriend(Profile f);

    public boolean blockUser(Profile user);

}
