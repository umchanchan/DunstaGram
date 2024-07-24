import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class BlockListGUI extends JFrame implements Runnable {
    private Profile currentUser;
    private Server server;
    private JFrame settingsFrame;
    private JList<String> blockList;
    private DefaultListModel<String> blockListModel;

    public BlockListGUI(Profile currentUser, Server server, JFrame settingsFrame) {
        this.currentUser = currentUser;
        this.server = server;
        this.settingsFrame = settingsFrame;
    }

    @Override
    public void run() {
        initializeGUI();
        setVisible(true);
    }

    private void initializeGUI() {
        setTitle("Block List");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Back button (trying to connect to the settings
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.setVisible(true);
                dispose();
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);

        // Block list
        blockListModel = new DefaultListModel<>();
        loadBlockList();
        blockList = new JList<>(blockListModel);
        blockList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(blockList), BorderLayout.CENTER);

        // Unblock button here...
        JButton unblockButton = new JButton("Unblock");
        unblockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = blockList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String username = blockListModel.getElementAt(selectedIndex);
                    try {
                        Profile unBlockProfile = getUserByUsername(username);
                        if (unBlockProfile != null && server.unBlock(currentUser, unBlockProfile)) {
                            blockListModel.remove(selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(BlockListGUI.this,
                                    "Failed to unblock user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(BlockListGUI.this,
                                "An error occurred while unblocking the user.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(unblockButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadBlockList() {
        List<Profile> blockedUsers = currentUser.getBlockedList();
        for (Profile user : blockedUsers) {
            blockListModel.addElement(user.getUsername());
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
