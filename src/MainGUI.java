import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;


public class MainGUI extends JComponent implements Runnable {

    private Profile user;
    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame mainFrame;
    private JPanel centerPanel;
    private JPanel newsFeedPanel;
    private JPanel bottomPanel;
    private JPanel postPanel;
    private JPanel rightPanel;
    private JButton searchButton;
    private JButton followingButton;
    private JButton settingButton;
    private JButton makePostButton;
    private JButton managePostButton;
    private JButton hidePostButton;
    private JButton viewCommentButton;
    private JButton upvoteButton;
    private JButton downvoteButton;
    private ArrayList<Post> posts = new ArrayList<>();
    private DefaultListModel<Post> postModel;
    private JList<Post> postList;


    public MainGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) {
        this.user = user;
        this.ois = ois;
        this.oos = oos;
    }

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == settingButton) {
                SwingUtilities.invokeLater(new SettingsGUI(ois, oos, user));
            }


        }
    };

    @Override
    public void run() {
//        temp();
//        temp2();


        mainFrame = new JFrame("DunstaGram");
        mainFrame.setSize(800, 450);
        mainFrame.setLayout(new BorderLayout());

        postModel = new DefaultListModel<>();
        receivePostList();

        postList = new JList<>(postModel);
        postList.setCellRenderer(new ListCellRenderer<Post>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Post> list, Post value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                postPanel = new JPanel(new BorderLayout());
                JPanel topPanel = new JPanel(new BorderLayout());
                JLabel nameLabel = new JLabel(value.getPoster().getUsername());
                hidePostButton = new JButton("Hide");
                topPanel.add(nameLabel, BorderLayout.WEST);
                topPanel.add(hidePostButton, BorderLayout.EAST);

                JTextArea contentArea = new JTextArea(value.getMessage());
                contentArea.setEditable(false);
                contentArea.setLineWrap(true);
                contentArea.setWrapStyleWord(true);

                JPanel downPanel = new JPanel(new GridLayout(1, 3));
                upvoteButton = new JButton("Upvote");
                downvoteButton = new JButton("Downvote");
                viewCommentButton = new JButton("Comments");
                downPanel.add(upvoteButton);
                downPanel.add(downvoteButton);
                downPanel.add(viewCommentButton);
                hidePostButton.addActionListener(actionListener);
//                hidePostButton.setEnabled(false);
                upvoteButton.addActionListener(actionListener);
                downvoteButton.addActionListener(actionListener);
                viewCommentButton.addActionListener(actionListener);

                postPanel.add(topPanel, BorderLayout.NORTH);
                postPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
                postPanel.add(downPanel, BorderLayout.SOUTH);

                postPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                return postPanel;
            }
        });

        centerPanel = new JPanel(new GridLayout(1, 2));


        newsFeedPanel = new JPanel(new BorderLayout());
        newsFeedPanel.add(new JScrollPane(postList), BorderLayout.CENTER);

        centerPanel.add(newsFeedPanel);



        bottomPanel = new JPanel(new GridLayout(1, 3));
        searchButton = new JButton("Search");
        followingButton = new JButton("Following");
        settingButton = new JButton("Settings");
        settingButton.addActionListener(actionListener);
        followingButton.addActionListener(actionListener);
        searchButton.addActionListener(actionListener);
        bottomPanel.add(searchButton);
        bottomPanel.add(followingButton);
        bottomPanel.add(settingButton);

        rightPanel = new JPanel(new BorderLayout());
        makePostButton = new JButton("Post");
        managePostButton = new JButton("Manage Post");
        makePostButton.addActionListener(actionListener);
        managePostButton.addActionListener(actionListener);
        rightPanel.add(makePostButton, BorderLayout.NORTH);
        rightPanel.add(managePostButton, BorderLayout.SOUTH);

        centerPanel.add(rightPanel);



        mainFrame.add(bottomPanel, BorderLayout.SOUTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            /**
             * Window listener that closes the window
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    oos.writeObject("Exit");
                    oos.flush();
                    oos.close();
                    ois.close();
                    mainFrame.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
    }

    public void listToModel(ArrayList<Post> postList) {
        for (Post post : postList) {
            postModel.addElement(post);
        }
    }

    public void temp() {
        try {
            Profile chris = new Profile("Chan", "1123", 23, "Male");
            oos.writeObject("follow");
            oos.writeObject(chris);
            oos.flush();

            String result = (String) ois.readObject();
            System.out.println(result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void temp2() {
        try {
            Profile chris = new Profile("Chris", "11233", 23, "Male");
            Post post = new Post(chris, "Hey");
            oos.writeObject("makePost");
            oos.writeObject("Hey");
            oos.flush();


            Object obj = ois.readObject();
            if (obj instanceof Post) {
                System.out.println(((Post) obj).getPoster());
                System.out.println(((Post) obj).getMessage());
            } else {
                System.out.println(obj);
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void receivePostList() {
        try {
            oos.writeObject("viewPosts");
            oos.flush();

            Object response = ois.readObject();

            if (response instanceof ArrayList<?>) {
                ArrayList<?> list = (ArrayList<?>) response;

                if (!list.isEmpty() && list.get(0) instanceof Post) {
                    posts = (ArrayList<Post>) list;
                    listToModel(posts);
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Received data is not a list.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(mainFrame, "Error communicating with the server.",
                    "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
