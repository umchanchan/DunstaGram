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

public class BlockListGUI extends JFrame implements IBlockListGUI, Runnable {

    private JList<String> blockList;
    private DefaultListModel<String> blockListModel;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public BlockListGUI(ObjectInputStream ois, ObjectOutputStream oos) {
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
        setTitle("Block List");
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

        // Back button (trying to connect to the settings
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
                        oos.writeObject("unblock");
                        oos.writeObject(unBlockProfile);
                        oos.flush();

                        String response = (String) ois.readObject();
                        if (unBlockProfile != null && response.equals("Success")) {

                            blockListModel.remove(selectedIndex);
                        } else {
                            JOptionPane.showMessageDialog(BlockListGUI.this,
                                    "Failed to unblock user.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
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
        try {
            oos.writeUnshared("getBlockList");
            oos.flush();

            List<String> blockedUsers = (ArrayList<String>) ois.readObject();

            for (String user : blockedUsers) {
                blockListModel.addElement(user);
            }
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(BlockListGUI.this, "An error occurred while reading the user.", "Error", JOptionPane.ERROR_MESSAGE);

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
            JOptionPane.showMessageDialog(BlockListGUI.this, "An error occurred while reading the user.", "Error", JOptionPane.ERROR_MESSAGE);

        }
        return null;
    }
}
