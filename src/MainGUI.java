import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;


public class MainGUI extends JComponent implements Runnable {

    private Profile user;
    private Socket clientSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private JFrame mainFrame;
    private JFrame postFrame;
    private JPanel centerPanel;
    private JPanel newsFeedPanel;
    private JPanel bottomPanel;
    private JPanel postPanel;
    private JPanel rightPanel;
    private JPanel rightTopPanel;
    private JPanel allCommentPanel;
    private JPanel allPostPanel;
    private JButton searchButton;
    private JButton followingButton;
    private JButton settingButton;
    private JButton makePostButton;
    private JButton managePostButton;
    private JButton hidePostButton;
    private JButton viewCommentButton;
    private JButton upvoteButton;
    private JLabel upvoteCount;
    private JButton downvoteButton;
    private JLabel downvoteCount;
    private JButton refreshButton;
    private JButton postButton;
    private ArrayList<Post> posts = new ArrayList<>();
    private JFrame commentFrame;
    private JTextField textPart;
    private ArrayList<Comment> comments = new ArrayList<>();
    private MainGUI main;

    public MainGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) {
        this.user = user;
        this.ois = ois;
        this.oos = oos;
        this.main = this;
    }


    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == settingButton) {

                mainFrame.dispose();
                SwingUtilities.invokeLater(new SettingsGUI(ois, oos, user, main));

            } else if (e.getSource() == searchButton) {

                SwingUtilities.invokeLater(new NewSearchGUI (ois, oos, user));

            } else if (e.getSource() == followingButton) {
//                SwingUtilities.invokeLater(new FollowingGUI (ois, oos));

            } else if (e.getSource() == makePostButton) {
                displayPostGUI();

            } else if (e.getSource() == managePostButton) {


            } else if (e.getSource() == postButton) {
                String message = textPart.getText();
                try {
                    oos.writeObject("makePost");
                    oos.writeObject(message);
                    oos.flush();

                    Object response = ois.readObject();

                    if (response instanceof String) {
                        if (response.equals("Success")) {
                            postFrame.dispose();
                            refresh();
                        } else {
                            JOptionPane.showMessageDialog(postFrame, "You have already made the same post!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(postFrame, "Error occurred while communicating with server",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == refreshButton) {
                refresh();
            }
        }
    };

    @Override
    public void run() {

        mainFrame = new JFrame("DunstaGram");
        mainFrame.setSize(1200, 800);
        mainFrame.setLayout(new BorderLayout());


        receivePostList();


        allPostPanel = new JPanel();
        allPostPanel.setLayout(new BoxLayout(allPostPanel, BoxLayout.Y_AXIS));
        generatePostPanel();

        centerPanel = new JPanel(new GridLayout(1, 2));


        newsFeedPanel = new JPanel(new BorderLayout());
        newsFeedPanel.add(new JScrollPane(allPostPanel), BorderLayout.CENTER);

        centerPanel.add(new JScrollPane(newsFeedPanel));


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
        rightTopPanel = new JPanel(new GridLayout(0, 2));
        makePostButton = new JButton("Post");
        managePostButton = new JButton("Manage Post");
        refreshButton = new JButton("Refresh");
        makePostButton.addActionListener(actionListener);
        managePostButton.addActionListener(actionListener);
        refreshButton.addActionListener(actionListener);
        rightTopPanel.add(makePostButton);
        rightTopPanel.add(refreshButton);

        JLabel picLabel = showDunstaLogo();

        rightPanel.add(rightTopPanel, BorderLayout.NORTH);
        rightPanel.add(picLabel, BorderLayout.CENTER);
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

    private void displayCommentGUI(Post post) {
        commentFrame = new JFrame("Comment");
        commentFrame.setSize(600, 400);
        commentFrame.setLayout(new BorderLayout());

        allCommentPanel = new JPanel();
        allCommentPanel.setLayout(new BoxLayout(allCommentPanel, BoxLayout.Y_AXIS));
        refreshComments(post);

        JPanel commentInput = new JPanel();
        commentInput.setLayout(new BoxLayout(commentInput, BoxLayout.X_AXIS));
        JTextField commentField = getjTextField();
        commentField.setMaximumSize(new Dimension(550, 50));
        JButton makeComment = new JButton("Comment");

        makeComment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String commentText = commentField.getText();
                try {
                    oos.writeObject("makeComment");
                    oos.writeObject(post);
                    oos.writeObject(commentText);
                    oos.flush();

                    String response = (String) ois.readObject();
                    if (response.equals("Success")) {
                        JOptionPane.showMessageDialog(commentFrame, "You made the comment successfully! Returning to the main menu",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        commentFrame.dispose();
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(commentFrame, "You have already made the same comment!",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }


                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(commentFrame, "Error occurred while communicating with server",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });

        commentInput.add(commentField);
        commentInput.add(makeComment);

        commentFrame.add(new JScrollPane(allCommentPanel), BorderLayout.CENTER);
        commentFrame.add(commentInput, BorderLayout.SOUTH);
        commentFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        commentFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                commentFrame.dispose();
            }
        });
        commentFrame.setVisible(true);
        commentFrame.setLocationRelativeTo(mainFrame);

    }

    private void displayPostGUI() {
        postFrame = new JFrame("Create Post");
        postFrame.setSize(600, 400);
        postFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel username = new JLabel(user.getUsername());
        textPart = getjTextField();

        postButton = new JButton("Post");
        postButton.addActionListener(actionListener);

        topPanel.add(postButton, BorderLayout.EAST);
        topPanel.add(username, BorderLayout.WEST);
        postFrame.add(topPanel, BorderLayout.NORTH);
        postFrame.add(textPart, BorderLayout.CENTER);

        postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postFrame.addWindowListener(new WindowAdapter() {
            /**
             * Window listener that closes the window
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                postFrame.dispose();
            }
        });
        postFrame.setVisible(true);
        postFrame.setLocationRelativeTo(mainFrame);
    }

    private static JTextField getjTextField() {
        JTextField textPart = new JTextField("What's on your mind?");
        textPart.setForeground(Color.GRAY);
        textPart.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textPart.getText().equals("What's on your mind?")) {
                    textPart.setText("");
                    textPart.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textPart.getText().isEmpty()) {
                    textPart.setForeground(Color.GRAY);
                    textPart.setText("What's on your mind?");
                }
            }
        });
        return textPart;
    }

    public void generatePostPanel() {
        for (Post upPost : posts) {
            postPanel = new JPanel(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());
            JLabel nameLabel = new JLabel(upPost.getPoster().getUsername());
            hidePostButton = new JButton("Hide");
            topPanel.add(nameLabel, BorderLayout.WEST);
            topPanel.add(hidePostButton, BorderLayout.EAST);

            JTextArea contentArea = new JTextArea(upPost.getMessage());
            contentArea.setEditable(false);
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);

            JPanel downPanel = new JPanel(new GridLayout(1, 5));
            upvoteButton = new JButton("Upvote");
            upvoteCount = new JLabel(String.valueOf(upPost.getUpvotes()), SwingConstants.CENTER);
            downvoteButton = new JButton("Downvote");
            downvoteCount = new JLabel(String.valueOf(upPost.getDownvotes()), SwingConstants.CENTER);
            viewCommentButton = new JButton(upPost.getComments().size() + "    Comments");
            downPanel.add(upvoteButton);
            downPanel.add(upvoteCount);
            downPanel.add(downvoteButton);
            downPanel.add(downvoteCount);
            downPanel.add(viewCommentButton);
            hidePostButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int ans = JOptionPane.showConfirmDialog(mainFrame, "Are you sure you want to hide this post?",
                            "Hide Post", JOptionPane.YES_NO_OPTION);
                    if (ans == JOptionPane.YES_OPTION) {
                        try {
                            oos.writeObject("hidePost");
                            oos.writeObject(upPost);
                            oos.flush();

                            refresh();
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "Error occurred while communicating with server",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            upvoteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        oos.writeObject("upvotePost");
                        oos.writeObject(upPost);
                        oos.flush();

                        refresh();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error occurred while communicating with server",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }


                }
            });
            downvoteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        oos.writeObject("downvotePost");
                        oos.writeObject(upPost);
                        oos.flush();

                        refresh();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(mainFrame, "Error occurred while communicating with server",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            viewCommentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    comments = upPost.getComments();
                    displayCommentGUI(upPost);
                }
            });

            postPanel.add(topPanel, BorderLayout.NORTH);
            postPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);
            postPanel.add(downPanel, BorderLayout.SOUTH);

            postPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            allPostPanel.add(postPanel);
        }
    }

    public JLabel showDunstaLogo() {
        ImageIcon profilePicIcon = new ImageIcon("dunsta.png");
        Image image = profilePicIcon.getImage();
        Image scaledImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel profilePic = new JLabel(scaledIcon);
        profilePic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return profilePic;
    }

    public void refresh() {
        mainFrame.dispose();
        SwingUtilities.invokeLater(new MainGUI(user, ois, oos));
    }

    public void closeAll() {
        mainFrame.dispose();
        if (postFrame != null) {
            postFrame.dispose();
        }

        if (commentFrame != null) {
            commentFrame.dispose();
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

    private void refreshComments(Post post) {
        for (Comment comment : comments) {
            JPanel commentPanel = new JPanel(new BorderLayout());
            JPanel topPanel = new JPanel(new BorderLayout());

            JLabel commenterPanel = new JLabel(comment.getCommenter().getUsername());
            JButton deleteCommentButton = new JButton("Delete Comment");
            topPanel.add(commenterPanel, BorderLayout.WEST);
            topPanel.add(deleteCommentButton, BorderLayout.EAST);

            JTextArea commentText = new JTextArea(comment.getCommentContents());
            commentText.setEditable(false);
            commentText.setLineWrap(true);
            commentText.setWrapStyleWord(true);

            JPanel downPanel = new JPanel(new GridLayout(1, 4));

            JButton upvoteCommentButton = new JButton("Upvote");
            JLabel upvoteCountLabel = new JLabel(String.valueOf(comment.getUpvote()), SwingConstants.CENTER);
            JButton downvoteCommentButton = new JButton("Downvote");
            JLabel downvoteCountLabel = new JLabel(String.valueOf(comment.getDownvote()), SwingConstants.CENTER);

            downPanel.add(upvoteCommentButton);
            downPanel.add(upvoteCountLabel);
            downPanel.add(downvoteCommentButton);
            downPanel.add(downvoteCountLabel);

            upvoteCommentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        oos.writeObject("upvoteComment");
                        oos.writeObject(post);
                        oos.writeObject(comment);
                        oos.flush();

                        JOptionPane.showMessageDialog(commentFrame, "Upvote Successfully! Returning to main menu!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        commentFrame.dispose();
                        refresh();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(commentFrame, "Error occurred while communicating with server",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            downvoteCommentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        oos.writeObject("downvoteComment");
                        oos.writeObject(post);
                        oos.writeObject(comment);
                        oos.flush();

                        JOptionPane.showMessageDialog(commentFrame, "Downvote Successfully! Returning to main menu!",
                                "Success", JOptionPane.INFORMATION_MESSAGE);
                        commentFrame.dispose();
                        refresh();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(commentFrame, "Error occurred while communicating with server",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });


            if (comment.getCommenter().getUsername().equals(user.getUsername())) {
                deleteCommentButton.setVisible(true);
                deleteCommentButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            oos.writeObject("deleteComment");
                            oos.writeObject(post);
                            oos.writeObject(comment);
                            oos.flush();

                            String response = (String) ois.readObject();
                            if (response.equals("Success")) {
                                JOptionPane.showMessageDialog(commentFrame, "Removed Successfully! Returning to main menu!",
                                        "Success", JOptionPane.INFORMATION_MESSAGE);
                                refresh();
                            } else {
                                JOptionPane.showMessageDialog(commentFrame, "Failed to delete comment",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (IOException | ClassNotFoundException ex) {
                            JOptionPane.showMessageDialog(commentFrame, "Error occurred while communicating with server",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
            } else {
                deleteCommentButton.setVisible(false);
            }

            commentPanel.add(topPanel, BorderLayout.NORTH);
            commentPanel.add(commentText, BorderLayout.CENTER);
            commentPanel.add(downPanel, BorderLayout.SOUTH);

            allCommentPanel.add(commentPanel);
        }
    }
}
