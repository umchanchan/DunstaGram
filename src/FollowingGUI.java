import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FollowingGUI extends JFrame implements Runnable {
    private Profile currentUser;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private JList<String> followingList;
    private DefaultListModel<String> followingListModel;

    public FollowingGUI(Profile currentUser, ObjectInputStream ois, ObjectOutputStream oos) {
        this.currentUser = currentUser;
        this.ois = ois;
        this.oos = oos;
    }

    @Override
    public void run() {
        initializeGUI();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void initializeGUI() {
        setTitle("Following List");
        setSize(400, 300);
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
        setLayout(new BorderLayout());

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                        oos.writeObject("unfollow");
                        oos.writeObject(userToUnfollow);
                        oos.flush();

                        String response = (String) ois.readObject();
                        if (userToUnfollow != null && response.equals("Success")) {

                            followingListModel.remove(selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(FollowingGUI.this, "Failed to unfollow user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
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
                        oos.writeObject("block");
                        oos.writeObject(userToBlock);
                        oos.flush();


                        String response = (String) ois.readObject();
                        if (userToBlock != null && response.equals("Success")) {

                            followingListModel.remove(selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(FollowingGUI.this, "Failed to block user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
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
        try {
            oos.writeObject("search");
            oos.writeObject(username);
            oos.flush();

            Profile userObj = (Profile) ois.readObject();

            return userObj;

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(FollowingGUI.this, "An error occurred while reading the user.", "Error", JOptionPane.ERROR_MESSAGE);

        }
        return null;
    }
}
