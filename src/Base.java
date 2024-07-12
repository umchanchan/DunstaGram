import java.util.*;
import java.io.*;

public class Base {
    private ArrayList<Profile> users;
    private ArrayList<Post> allPosts;

    public Profile searchUser(String username) {
        for (Profile profile : users) {
            if (profile.getUsername().equals(username)) {
                return profile;
            }
        }
        return new Profile();
    }

    public Profile signUp(String username, String password, int age, String gender) throws IOException {

        ArrayList<String> fileInfo = readUserListFile();

        String fileUsername = "";
        for (int i = 0; i < fileInfo.size(); i++) {
            String[] parts = fileInfo.get(i).split("_");
            fileUsername = parts[0];
            String filePassword = parts[1];
        }

        if (fileUsername.equals(username)) {
            System.out.println("username not available");
            return null;
        }

        Profile newProfile = new Profile(username, password, age, gender);
        users.add(newProfile);

        fileInfo.add(username + "_" + password + "_");
        writeUserListFile(fileInfo);
        return newProfile;
    }

    public boolean login(String username, String password) throws IOException, UserNotFoundException {
        ArrayList<String> fileInfo = readUserListFile();
        String fileUsername = "";
        String filePassword = "";
        for (int i = 0; i < fileInfo.size(); i++) {
            String[] parts = fileInfo.get(i).split("_");
            fileUsername = parts[0];
            filePassword = parts[1];
        }

        if (fileUsername.equals(username) && filePassword.equals(password)) {
            return true;
        } else {
            throw new UserNotFoundException("Invalid login!");
        }
    }

    public ArrayList<String> readUserListFile() throws IOException {
        ArrayList<String> fileInfo = new ArrayList<>();
        try {
            File f = new File("userList.txt");
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);

            String line;
            while ((line = bfr.readLine()) != null) {

                fileInfo.add(line);

                String[] parts = line.split("_");
                if (parts.length < 1) {
                    throw new IOException("Error occurred when reading or writing a file");
                }

                String username = parts[0];
                String password = parts[1];

                Profile newProfile = new Profile(username, password);
                ArrayList<Profile> friends = newProfile.getFriends();

                for (int i = 2; i < parts.length; i++) {
                    String friendUsername = parts[i];
                    friends.add(new Profile(friendUsername));
                }
            }
            bfr.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when reading a file");
        }
        return fileInfo;
    }

    public void writeUserListFile(ArrayList<String> userInfo) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("userListFile", true), true)) {
            for (int i = 0; i < userInfo.size(); i++) {
                pw.println(userInfo.get(i));
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            throw new IOException("Error occurred when writing a file");
        }

    }

}
