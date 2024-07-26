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

public class ManagePostGUI implements Runnable {
    private Profile user;
    private ArrayList<Post> myPosts;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean remove = false;
    private JFrame frame;
    private JPanel allPosts;

    public ManagePostGUI(Profile user, ObjectInputStream ois, ObjectOutputStream oos) throws IOException,
            ClassNotFoundException {
        this.user = user;
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
                        removePost = showOption(post);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    if (removePost) {
                        frame.remove(postPanel);
                    }



                }
            });
            postPanel.add(postButton);
            frame.add(postPanel);


        }







    }

    public boolean showOption(Post p) throws IOException, ClassNotFoundException {

        String[] options = new String[] {"Delete Post", "Review Comments"};
        String selection = (String) JOptionPane.showInputDialog(null, "Enter your action",
                "Prompt", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (selection == null) {
            JOptionPane.showMessageDialog(null, "No changes made.");
            return false;
        }
        if (selection.equals("Delete Post")) {
            System.out.println("Hello wordl");

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
        System.out.println(commentList);
        for(Comment c: commentList) {
            JPanel panel = new JPanel();
            JButton commentButton = new JButton();
            System.out.println(c.getCommentContents());
            JLabel label = new JLabel(c.getCommentContents());
            label.setFont(new Font("Arial", Font.PLAIN, 24));
            commentButton.add(label);
            commentButton.setSize(700, 50);
            commentButton.setPreferredSize(new Dimension(700, 50));

            commentButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    inspectComment(post, c);
                }
            });
            panel.add(commentButton);
            commentPanel.add(panel);
        }

        commentPanel.setVisible(true);

    }

    public void inspectComment(Post p, Comment c) {
        JFrame inspectArea = new JFrame();
        inspectArea.setLayout(new FlowLayout());
        inspectArea.setSize(600, 400);
        JLabel poster = new JLabel("Commenter: " + c.getCommenter().getUsername());
        poster.setFont(new Font("Arial", Font.PLAIN, 48));
        JLabel message = new JLabel("Message: " + c.getCommentContents());
        message.setFont(new Font("Arial", Font.PLAIN, 36));
        JButton deleteButton = new JButton("Delete Comment");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 48));
        Dimension d = new Dimension(400, 100);
        deleteButton.setSize(d);
        deleteButton.setPreferredSize(d);
        inspectArea.add(poster);
        inspectArea.add(message);
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

    }
}
