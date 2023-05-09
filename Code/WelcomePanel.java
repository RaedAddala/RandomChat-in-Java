import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;

public class WelcomePanel extends JFrame implements ActionListener, KeyListener {
    private JButton matchButton;
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 720;
    private static final int FONT_SIZE = 30;

    private ILogger logger;
    
    public WelcomePanel() {
        super("Welcome to ChatBox");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.addKeyListener(this);

        JLabel welcomeLabel = new JLabel("Welcome to ChatBox!");
        matchButton = new JButton("Match");

        matchButton.addActionListener(this);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(matchButton, BorderLayout.SOUTH);

        add(panel);

        pack();
        setVisible(true);
        toFront();
        requestFocus();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == matchButton) {
            try{
                ConversationListener listener = new ConversationListener();
                ILogger logger = (ILogger) Naming.lookup  ("rmi://0.0.0.0:7894/ChatServer");
                String url = logger.RequestLogin(listener);
                System.out.println("-System-: Your id is "+url);
                ITexter texter = (ITexter) Naming.lookup("rmi://0.0.0.0:7894/"+url);
                listener.PushTexter(texter);
                listener.PushChatBox(new ChatBox(texter));
                dispose();
            }catch(Exception ex){
                dispose();
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}