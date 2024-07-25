import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SearchGUI extends JFrame {
    private JTextField searchBar;
    private JButton searchButton;
    private JPanel resultsPanel;
    private List<Profile> profilesList;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public SearchGUI() {
        setTitle("User Profile Search");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        profilesList = new ArrayList<>();
        loadProfiles("userList.txt");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel instructionLabel = new JLabel("Type a username below to search for a user");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(instructionLabel);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        searchBar = new JTextField();
        searchBar.setMaximumSize(new Dimension(200, 30));
        searchPanel.add(searchBar);

        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        topPanel.add(searchPanel);
        add(topPanel, BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(new JScrollPane(resultsPanel), BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchProfiles();
            }
        });
    }

    private void loadProfiles(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Profile profile = new Profile().makeProfile(line);
                profilesList.add(profile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void searchProfiles() {
        String query = searchBar.getText().trim().toLowerCase();
        resultsPanel.removeAll();

        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No such user found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean userFound = false;
        for (Profile profile : profilesList) {
            if (profile.getUsername().toLowerCase().contains(query)) {
                userFound = true;
                JButton userButton = new JButton(profile.getUsername());
                userButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                userButton.setMaximumSize(new Dimension(200, 30));
                userButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                userButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showProfileDetails(profile);
                    }
                });
                resultsPanel.add(userButton);
                resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        if (!userFound) {
            JOptionPane.showMessageDialog(this, "No such user found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private void showProfileDetails(Profile profile) {
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon profilePicIcon = new ImageIcon("default_profile_pic.png");
        Image image = profilePicIcon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel profilePic = new JLabel(scaledIcon);
        profilePic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        profilePanel.add(profilePic);
        profilePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        profilePanel.add(new JLabel("Username: " + profile.getUsername()));
        profilePanel.add(new JLabel("Age: " + profile.getAge()));
        profilePanel.add(new JLabel("Gender: " + profile.getGender()));
        profilePanel.add(new JLabel("Friends: " + getFriendsList(profile)));

        JOptionPane.showMessageDialog(this, profilePanel, "Profile View",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String getFriendsList(Profile profile) {
        List<String> friendsUsernames = new ArrayList<>();
        for (String friend : profile.getFollowing()) {
            friendsUsernames.add(friend);
        }
        return String.join(", ", friendsUsernames);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SearchGUI().setVisible(true);
            }
        });
    }
}