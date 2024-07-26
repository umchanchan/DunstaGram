import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        }
        return false;

    }
}
