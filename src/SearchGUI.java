import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SearchGUI extends JFrame implements Runnable {
    private JTextField searchBar;
    private JButton searchButton;
    private Profile user;
    private JPanel resultsPanel;
    private List<Profile> profilesList;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;


    public SearchGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) {
        this.user = user;
        this.ois = ois;
        this.oos = oos;
    }


    public void run() {
        initializeGUI();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void initializeGUI() {
        setTitle("User Profile Search");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            /**
             * Window listener that closes the window
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });


        profilesList = new ArrayList<>();
        loadProfiles();

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

    private void loadProfiles() {
        try {
            oos.writeObject("getAllUser");
            oos.flush();

            Object response = ois.readObject();
            profilesList = (ArrayList<Profile>) response;

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(SearchGUI.this,
                    "An error occurred while reading the user.", "Error", JOptionPane.ERROR_MESSAGE);
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

        JButton followButton = new JButton("Follow");
        JButton blockButton = new JButton("Block");
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean followed = false;
                    ArrayList<String> followings = new ArrayList<>();
                    oos.writeObject("getFollowing");
                    oos.flush();

                    followings = (ArrayList<String>) ois.readObject();

                    for (String friend : followings) {
                        if (friend.equals(profile.getUsername())) {
                            followed = true;
                            break;
                        }
                    }
                    if (followed) {
                        JOptionPane.showMessageDialog(profilePanel, "You already follow this user",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {

                        oos.writeObject("follow");
                        oos.writeObject(profile);
                        oos.flush();


                        String response = (String) ois.readObject();
                        if (response.equals("Success")) {
                            JOptionPane.showMessageDialog(profilePanel, "Follow successful!",
                                    "Follow", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(profilePanel, "Failed to follow user.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }


                    }
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(profilePanel,
                            "An error occurred while following the user.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean blocked = false;
                    ArrayList<String> blocking = new ArrayList<>();
                    oos.writeObject("getBlockList");
                    oos.flush();

                    blocking = (ArrayList<String>) ois.readObject();

                    for (String blockedUser : blocking) {
                        if (blockedUser.equals(profile.getUsername())) {
                            blocked = true;
                            break;
                        }
                    }

                    if (blocked) {
                        JOptionPane.showMessageDialog(profilePanel, "This user is already blocked",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {

                        oos.writeUnshared("block");
                        oos.writeUnshared(profile);
                        oos.flush();


                        String response = (String) ois.readObject();
                        if (response.equals("Success")) {
                            JOptionPane.showMessageDialog(profilePanel, "Blocked successful!",
                                    "Follow", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(profilePanel, "Failed to block user.",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(profilePanel,
                            "An error occurred while blocking the user.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
        profilePanel.add(new JLabel("Following: " + getFriendsList(profile)));
        if (!profile.equals(user)) {
            profilePanel.add(followButton);
            profilePanel.add(blockButton);
        } else {
            Label label = new Label("This is you!");
            label.setAlignment(Label.CENTER);
            profilePanel.add(label);
        }

        JOptionPane.showMessageDialog(this, profilePanel, "Profile View",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private String getFriendsList(Profile profile) {
        try {
            oos.writeObject("getThisFollowing");
            oos.writeUnshared(profile);
            oos.flush();


            List<String> friendsUsernames = (ArrayList<String>) ois.readObject();
            for (String friend : profile.getFollowing()) {
                friendsUsernames.add(friend);
            }


            return String.join(", ", friendsUsernames);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}