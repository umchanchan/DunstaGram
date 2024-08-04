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

public class ManagePostGUI implements IManagePostGUI, Runnable {
    private ArrayList<Post> myPosts;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    private JFrame frame;
    private JPanel allPosts;

    public ManagePostGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) throws IOException,
            ClassNotFoundException {
        this.ois = ois;
        this.oos = oos;

        oos.writeUnshared("listMyPosts");
        oos.writeUnshared(user);
        oos.flush();

        myPosts = (ArrayList<Post>) ois.readObject();


    }


    public void run() {
        frame = new JFrame();

        JPanel otherPanel = new JPanel();
        otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
        JPanel newPanel = new JPanel(new BorderLayout());
        newPanel.add(new JScrollPane(otherPanel), BorderLayout.CENTER);
        frame.add(newPanel);

        frame.setLayout(new FlowLayout());

        frame.setTitle("Manage Posts");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel yourPosts = new JLabel("Select a Post:");
        yourPosts.setFont(new Font("Arial", Font.BOLD, 36));
        frame.add(yourPosts);

        allPosts = new JPanel();
        allPosts.setLayout(new FlowLayout());
        allPosts.setSize(620, 700);

        for (Post post : myPosts) {
            JPanel postPanel = new JPanel();
            JButton postButton = new JButton();
            postButton.setSize(600, 100);
            postButton.setPreferredSize(new Dimension(600, 100));
            JLabel label = new JLabel(post.getMessage());
            label.setFont(new Font("Arial", Font.PLAIN, 24));
            postButton.add(label);

            postButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean removePost = false;
                    try {
                        frame.dispose();
                        removePost = showOption(post);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (removePost) {
                        frame.remove(postPanel);
                        frame.remove(postButton);
                    }



                }
            });
            postPanel.add(postButton);
            frame.add(postPanel);


        }
    }

    private boolean showOption(Post p) throws IOException, ClassNotFoundException {

        String[] options = new String[] {"Delete Post", "Review Comments"};
        String selection = (String) JOptionPane.showInputDialog(null, "Enter your action",
                "Prompt", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selection == null) {
            JOptionPane.showMessageDialog(null, "No changes made.");
            return false;
        }
        if (selection.equals("Delete Post")) {
            oos.flush();

            oos.writeObject("removePost");
            oos.writeUnshared(p);

            oos.flush();

            String outcome = (String) ois.readObject();
            if (outcome.equals("Success")) {
                JOptionPane.showMessageDialog(null, "Successfully deleted this post!");
                frame.revalidate();
                frame.repaint();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Unable to delete this post!");
                return false;
            }
        } else if (selection.equals("Review Comments")) {
            showComments(p);
            return true;
        }
        return false;

    }

    private void showComments(Post post) {
        JFrame commentPanel = new JFrame();
        commentPanel.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        commentPanel.addWindowListener(new WindowAdapter() {
            /**
             * Window listener that closes the window
             * @param e the event to be processed
             */
            @Override
            public void windowClosing(WindowEvent e) {
                commentPanel.dispose();
            }
        });
        commentPanel.setLayout(new FlowLayout());
        commentPanel.setSize(800, 800);
        ArrayList<Comment> commentList = post.getComments();
        for(Comment c: commentList) {
            JPanel panel = new JPanel();
            JButton commentButton = new JButton();
            JLabel label = new JLabel(c.getCommentContents());
            label.setFont(new Font("Arial", Font.PLAIN, 24));
            commentButton.add(label);
            commentButton.setSize(700, 50);
            commentButton.setPreferredSize(new Dimension(700, 50));

            commentButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    commentPanel.dispose();
                    inspectComment(post, c);

                }
            });
            panel.add(commentButton);
            commentPanel.add(panel);
        }

        commentPanel.setVisible(true);
        commentPanel.setLocationRelativeTo(null);

    }

    private void inspectComment(Post p, Comment c) {
        JFrame inspectArea = new JFrame();
        inspectArea.setLayout(new FlowLayout());
        inspectArea.setSize(600, 400);
        JPanel partOne = new JPanel();
        Color color = new Color(150, 180, 200);
        partOne.setBackground(color);
        partOne.setSize(600, 100);
        partOne.setPreferredSize(new Dimension(600, 60));
        JPanel partTwo = new JPanel();
        partTwo.setSize(600, 100);
        partTwo.setPreferredSize(new Dimension(600, 60));
        partTwo.setBackground(Color.WHITE);

        JPanel space = new JPanel();
        space.setSize(600, 100);
        space.setPreferredSize(new Dimension(600, 100));


        JLabel poster = new JLabel("Commenter: " + c.getCommenter().getUsername());
        poster.setFont(new Font("Arial", Font.BOLD, 48));
        JLabel message = new JLabel("Message: " + c.getCommentContents());
        message.setFont(new Font("Arial", Font.BOLD, 36));
        JButton deleteButton = new JButton("Delete Comment");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 48));
        Dimension d = new Dimension(550, 100);
        deleteButton.setSize(d);
        deleteButton.setPreferredSize(d);
        partOne.add(poster);
        partTwo.add(message);
        inspectArea.add(partOne);
        inspectArea.add(partTwo);
        inspectArea.add(space);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    oos.writeObject("deleteComment");
                    oos.writeObject(p);
                    oos.writeObject(c);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    String outcome = (String) ois.readObject();
                    if (outcome.equals("Success")) {
                        JOptionPane.showMessageDialog(null,
                                "Successfully deleted this comment.");
                        inspectArea.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Could not delete");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }




            }
        });
        inspectArea.add(deleteButton);
        inspectArea.setVisible(true);
        inspectArea.setLocationRelativeTo(null);

    }
}
