import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class NewSearchGUI implements Runnable{
    private JTextField searchBar;
    private JButton searchButton;
    private JPanel resultsPanel;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame frame;
    private ArrayList<ArrayList<String>> allUserInfo;
    private Profile viewer;

    public NewSearchGUI(ObjectInputStream ois, ObjectOutputStream oos, Profile viewer) {
        this.ois = ois;
        this.oos = oos;
        this.viewer = viewer;
    }
    public void run() {
        frame = new JFrame("User Profile Search");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new BorderLayout());

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
        frame.add(topPanel, BorderLayout.NORTH);

        frame.setVisible(true);
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        resultsPanel.setVisible(true);

        frame.add(new JScrollPane(resultsPanel), BorderLayout.CENTER);


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    oos.writeObject("search");
                    oos.flush();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                boolean b = false;

                try {
                    b = searchProfile();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(b);

                if(b) {
                    for(ArrayList<String> userInfo: allUserInfo) {
                        System.out.println(userInfo);
                        JButton user = new JButton(userInfo.get(0));
                        user.setAlignmentX(Component.CENTER_ALIGNMENT);
                        user.setSize(new Dimension(200, 30));
                        user.setPreferredSize(new Dimension(200, 30));

                        user.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                showProfileDetails(userInfo.get(0), userInfo.get(1), userInfo.get(2), userInfo.get(3),
                                        userInfo.get(4));
                            }
                        });
                        user.setVisible(true);
                        resultsPanel.add(user);

                    }
                }


            }


        });


    }

    private boolean searchProfile() throws IOException, ClassNotFoundException {
        if (resultsPanel != null) {
            resultsPanel.removeAll();
        }
        String query = searchBar.getText().trim().toLowerCase();
        oos.writeObject(query);
        oos.flush();
        Object obj = ois.readObject();
        if (obj instanceof String) {
            JOptionPane.showMessageDialog(null, "No such user found!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        allUserInfo = (ArrayList<ArrayList<String>>) obj;
        resultsPanel.revalidate();
        resultsPanel.repaint();
        return true;
    }



    private void showProfileDetails(String username, String age, String gender, String following, String posts) {
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

        profilePanel.add(new JLabel("Username: " + username));
        profilePanel.add(new JLabel("Age: " + age));
        profilePanel.add(new JLabel("Gender: " + gender));
        profilePanel.add(new JLabel("Following: " + following));
        profilePanel.add(new JLabel("Posts: " + posts));


        if (!(viewer.getUsername().equals(username))) {
            JButton followButton = new JButton("Follow");
            followButton.setSize(100, 50);
            profilePanel.add(followButton);
            followButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        oos.writeObject("follow");
                        oos.writeObject(username);
                        oos.writeObject(viewer.getUsername());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        String outcome = (String) ois.readObject();
                        if (outcome.equals("Success")) {
                            JOptionPane.showMessageDialog(null,
                                    "Successfully followed " + username + "!");
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Error: You are already following " + username + "!");
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }


                }
            });

        } else {
            JLabel label = new JLabel("This is you!");
            profilePanel.add(label);
        }

        JOptionPane.showMessageDialog(null, profilePanel, "Profile View",
                JOptionPane.INFORMATION_MESSAGE);

    }



}

