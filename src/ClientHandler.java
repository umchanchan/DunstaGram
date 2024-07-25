import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Each client has a server that deals with each client's request
 */
public class ClientHandler implements IClientHandler {
    private Socket clientSocket;
    private Base base;
    private Profile profile = new Profile();

    public ClientHandler(Socket clientSocket, Base base) {
        this.clientSocket = clientSocket;
        this.base = base;
    }


    public void run() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(clientSocket.getInputStream());


            while (true) {
                String clientInput = (String) ois.readObject();
                System.out.println(clientInput);
                //To stop this thread when the user closes the software.
                if (clientInput.equals("Exit")) {
                    ois.close();
                    oos.close();
                    clientSocket.close();
                    return;
                }
                switch (clientInput) {
                    case "signUp" -> {
                        String username = (String) ois.readObject();
                        String password = (String) ois.readObject();
                        int age = 0;
                        String gender = "";

                        try {
                            String temp = (String) ois.readObject();
                            gender = (String) ois.readObject();

                            if (username.contains("_")) {
                                oos.writeObject("_");
                                oos.flush();
                                break;
                            }

                            if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
                                    temp == null || temp.isEmpty() || gender == null || gender.isEmpty()) {
                                oos.writeObject("Empty");
                                oos.flush();
                                break;
                            }
                            age = Integer.parseInt(temp);
                            System.out.println(age);
                            if (age < 0) {
                                oos.writeObject("Invalid");
                                oos.flush();
                            } else if (base.signUp(username, password, age, gender)) {
                                oos.writeObject("Success");
                                oos.flush();
                            } else {
                                oos.writeObject("Fail");
                                oos.flush();
                            }

                        } catch (NumberFormatException e) {
                            oos.writeObject("Invalid Int");
                            oos.flush();
                        }

                    }
                    case "login" -> {
                        String username = (String) ois.readObject();
                        String password = (String) ois.readObject();
                        String result;
                        try {
                            if ((profile = base.login(username, password)) != null) {
                                oos.writeObject(profile); //in client side, check what the object is by instanceOf
                                oos.flush();
                            } else {
                                result = "Fail";
                                oos.writeObject(result);
                                oos.flush();
                            }
                        } catch (UserNotFoundException e) {
                            result = "Fail";
                            oos.writeObject(result);
                            oos.flush();
                        }


                    }

                    case "follow" -> {
                        String toFollow = (String) ois.readObject();
                        String follower = (String) ois.readObject();
                        Profile p = base.searchUser(toFollow);
                        Profile p1 = base.searchUser(follower);

                        if (p1.isFollowing(p)) {
                            oos.writeObject("Fail");
                            oos.flush();
                            break;
                        }
                        if (base.follow(p1, p)) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            oos.writeObject("Fail");
                            oos.flush();
                        }
                    }

                    case "unfollow" -> {
                        Profile toUnfollow = (Profile) ois.readObject();
                        if (base.unFollow(profile, toUnfollow)) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            oos.writeObject("Fail");
                            oos.flush();
                        }

                    }

                    case "block" -> {
                        Profile toBlock = (Profile) ois.readObject();
                        if (base.block(profile, toBlock)) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            oos.writeObject("Fail");
                            oos.flush();
                        }
                    }

                    case "unblock" -> {
                        Profile unBlock = (Profile) ois.readObject();
                        if (base.unBlock(profile, unBlock)) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            oos.writeObject("Fail");
                            oos.flush();
                        }
                    }

                    case "viewPosts" -> {
                        base.readAllListFile();
                        ArrayList<Post> postList = profile.getFollowingPosts(base);
                        oos.writeObject(postList);
                        oos.flush();
                    }

                    case "listMyPosts" -> {
                        ArrayList<Post> postList = profile.getMyPosts();
                        oos.writeObject(postList);
                        oos.flush();
                    }

                    case "makePost" -> {
                        String message = (String) ois.readObject();
                        Post post;
                        if ((post = base.makeNewPost(profile, message)) != null) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            oos.writeObject("Fail");
                            oos.flush();
                        }
                    }

                    case "removePost" -> {
                        Post post = (Post) ois.readObject();
                        if (base.removePost(profile, post)) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            //inform user doesn't have authority
                            oos.writeObject("Fail");
                            oos.flush();
                        }
                    }

                    case "hidePost" -> {
                        Post post = (Post) ois.readObject();
                        base.hidePost(profile, post);
                    }

                    case "viewHidePost" -> {
                        base.readAllListFile();
                        ArrayList<Post> postList = profile.getHidePosts();
                        oos.writeObject(postList);
                        oos.flush();
                    }

                    case "makeComment" -> {
                        Post post = (Post) ois.readObject();
                        String message = (String) ois.readObject();
                        Comment comment;
                        if ((comment = base.makeComment(post, profile, message)) != null) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            //this is unlikely to happen because user will choose pick one poster in GUI
                            oos.writeObject("Fail");
                            oos.flush();
                        }

                    }

                    case "deleteComment" -> {
                        Post post = (Post) ois.readObject();
                        Comment comment = (Comment) ois.readObject();
                        if (base.deleteComment(post, profile, comment)) {
                            oos.writeObject("Success");
                            oos.flush();
                        } else {
                            //inform user doesn't have authority
                            oos.writeObject("Fail");
                            oos.flush();
                        }
                    }

                    case "upvotePost" -> {
                        Post post = (Post) ois.readObject();
                        base.addUpvote(post);
                    }

                    case "downvotePost" -> {
                        Post post = (Post) ois.readObject();
                        base.addDownvote(post);
                    }

                    case "upvoteComment" -> {
                        Post post = (Post) ois.readObject();
                        Comment comment = (Comment) ois.readObject();
                        base.addUpvote(post, comment);
                    }

                    case "downvoteComment" -> {
                        Post post = (Post) ois.readObject();
                        Comment comment = (Comment) ois.readObject();
                        base.addDownvote(post, comment);
                    }

                    case "showNumComments" -> {
                        Post post = (Post) ois.readObject();
                        int numComments = post.getNumComments();
                        String numStr = String.valueOf(numComments);
                        oos.writeObject(numStr);
                        oos.flush();
                    }

                    case "showNumFollowing" -> {
                        Profile viewUser = (Profile) ois.readObject();
                        int numFollowing = viewUser.getFollowing().size();
                        String numStr = String.valueOf(numFollowing);
                        oos.writeObject(numStr);
                        oos.flush();
                    }

                    case "searchUser" -> {
                        try {
                            String username = (String) ois.readObject();
                            Profile searchedUser = base.searchUser(username);
                            oos.writeObject(searchedUser);
                            oos.flush();
                        } catch (UserNotFoundException e) {
                            oos.writeObject("Fail");
                            oos.flush();
                        }

                    }

                    case "viewProfile" -> {
                        Base b = new Base();
                        Profile p = (Profile) ois.readObject();
                        b.readUserListFile();
                        b.readPostListFile();
                        Profile newProfile = b.searchUser(p.getUsername());
                        ArrayList<String> list = b.getUserInfo(newProfile);
                        oos.writeObject(list);
                    }

                    case "editProfile" -> {
                        Base b = new Base();
                        String user = (String) ois.readObject();
                        Profile p = b.searchUser(user);
                        String temp = (String) ois.readObject();
                        String gender = (String) ois.readObject();
                        String password = (String) ois.readObject();
                        int age;
                        if (temp.isEmpty() || temp == null) {
                            age = p.getAge();
                            oos.flush();
                        } else {

                            try {
                                age = Integer.parseInt(temp);
                            } catch (NumberFormatException e) {
                                oos.writeObject("Invalid");
                                oos.flush();
                                break;
                            }
                            System.out.println(age);
                            if (age < 0) {
                                oos.writeObject("Invalid");
                                oos.flush();
                                break;
                            }
                        }

                        if (gender.isEmpty() || gender == null) {
                            gender = p.getGender();
                            oos.flush();
                        }

                        b.editUserInfo(p, age, gender, password);
                        b.writeUserListFile();
                        oos.writeObject("Success");
                    }

                    case "search" -> {
                        Base b = new Base();
                        String search = (String) ois.readObject();

                        Profile profileSearch = b.searchUser(search);
                        oos.writeObject(profileSearch);
                        oos.flush();

                    }

                    case "getUserInfo" -> {
                        Base b = new Base();
                        String search = (String) ois.readObject();
                        if (search == null || search.isEmpty()) {
                            oos.writeObject("No");
                            oos.flush();
                            break;
                        }

                        Profile profileSearch = b.searchUser(search);
                        ArrayList<ArrayList<String>> userInfo = new ArrayList<>();
                        userInfo.add(b.getUserInfo(profileSearch));

                        oos.writeObject(userInfo);
                        oos.flush();
                    }


                    default -> {
                        System.out.println("Invalid message...why are you here");
                        break;
                    }
                }


            }


        } catch (EOFException e) {
            System.err.print("Caught");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
