import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class FollowingGUI extends JFrame implements Runnable {
    private Profile currentUser;
    private Server server;
    private MainGUI mainGUI;
    private JList<String> followingList;
    private DefaultListModel<String> followingListModel;

    public FollowingGUI(Profile currentUser, Server server, MainGUI mainGUI) {
        this.currentUser = currentUser;
        this.server = server;
        this.mainGUI = mainGUI;
    }

    @Override
    public void run() {
        initializeGUI();
        setVisible(true);
    }

    private void initializeGUI() {
        setTitle("Following List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.setVisible(true);
                dispose();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // Following list
        followingListModel = new DefaultListModel<>();
        loadFollowingList();
        followingList = new JList<>(followingListModel);
        followingList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(followingList), BorderLayout.CENTER);

        // Unfollow and Block buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton unfollowButton = new JButton("Unfollow");
        JButton blockButton = new JButton("Block");

        unfollowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = followingList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String username = followingListModel.getElementAt(selectedIndex);
                    try {
                        Profile userToUnfollow = getUserByUsername(username);
                        if (userToUnfollow != null && server.unFollow(currentUser, userToUnfollow)) {
                            followingListModel.remove(selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(FollowingGUI.this, "Failed to unfollow user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(FollowingGUI.this, "An error occurred while unfollowing the user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        blockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = followingList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String username = followingListModel.getElementAt(selectedIndex);
                    try {
                        Profile userToBlock = getUserByUsername(username);
                        if (userToBlock != null && server.block(currentUser, userToBlock)) {
                            followingListModel.remove(selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(FollowingGUI.this, "Failed to block user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(FollowingGUI.this, "An error occurred while blocking the user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        bottomPanel.add(unfollowButton);
        bottomPanel.add(blockButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadFollowingList() {
        List<String> followingUsers = currentUser.getFollowing();
        for (String username : followingUsers) {
            followingListModel.addElement(username);
        }
    }

    private Profile getUserByUsername(String username) {
        for (Profile user : server.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
