import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ChatBox extends JFrame  implements ActionListener, KeyListener  {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 720;

    private static final int TEXTINPUT_FONT_SIZE = 22;
    private static final int CHAT_FONT_SIZE = 16;

    private static final int TEXT_AREA_HEIGHT = 60;
    private static final int SEND_BUTTON_WIDTH = 150;

    private final JTextField textField;
    private final JTextArea chatArea;
    private final JButton sendButton;
    private final JButton exitButton;
    private final ITexter _texter;
    private boolean inRoom;

    public ChatBox(ITexter texter) {
        super("Random ChatRoom");
        _texter = texter;
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textField = new JTextField();
        chatArea = new JTextArea();

        sendButton = new JButton("Send");
        exitButton = new JButton("!Exit the Chatroom!");

        sendButton.addActionListener(this);
        exitButton.addActionListener(this);
        chatArea.setEditable(false);
        exitButton.setEnabled(false);

        textField.setFont(new Font("Arial", Font.PLAIN, TEXTINPUT_FONT_SIZE));
        chatArea.setFont(new Font("Arial", Font.PLAIN, CHAT_FONT_SIZE));

        textField.setPreferredSize(new Dimension(0, TEXT_AREA_HEIGHT));

        textField.addKeyListener(this);
        this.addKeyListener(this);

        sendButton.setPreferredSize(new Dimension(SEND_BUTTON_WIDTH, 0));
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());

        inputPanel.add(textField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        inputPanel.add(exitButton, BorderLayout.WEST);
        add(inputPanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);

        ImageIcon img = new ImageIcon("./logo.png");
        setIconImage(img.getImage());

        this.addWindowListener(new OnDisplayListener(_texter));
        inRoom = true;

        pack();
        setVisible(true);
        toFront();
        requestFocus();
    }
    public void DisplayText(String message){
        if (message.toUpperCase().equals(message)) {
            message += " [!!Yelling!!]";
        }
        chatArea.append(message+"\n");
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == sendButton) {
            String message = textField.getText().strip();
            if (message.length() != 0) {
                if (message.toUpperCase().equals(message)) {
                    message += " [!!Yelling!!]";
                }
                chatArea.append("You: " + message + "\n");
                textField.setText("");
                 try {
                _texter.SendMsg(message);
                 } catch (Exception ex) {
                 ex.printStackTrace();
                 }
            }
        } else if (event.getSource() == exitButton) {
            if(inRoom){
            DisplayText("-System-: You have left the chat."+"\n");
                inRoom = false;
                try {
                  _texter.ExitChatRoom();
              } catch (Exception e) {
                  throw new RuntimeException(e);
                }
                exitButton.setText("!Enter new Chatroom!");
                textField.setText("");
                textField.setEditable(false);
            }else {
                exitButton.setText("!Exit the Chatroom!");
                exitButton.setEnabled(false);
                textField.setEditable(true);
                try {
                    _texter.QueueForChat();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public void OnStrangerQuit(){
        if(inRoom){
            inRoom = false;
            chatArea.append("-System-: Stranger has left the chat."+"\n");
            exitButton.setText("!Enter new Chatroom!");

            textField.setText("");
            textField.setEditable(false);
            exitButton.setEnabled(true);
        }
    }
    public void EnterRoomNotice(){
        chatArea.setText("");
        chatArea.append("-System-: You are now talking with a stranger."+"\n");
        inRoom = true;
        exitButton.setText("!Exit the chatroom!");
        exitButton.setEnabled(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            dispose();
        } else if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (e.getSource() == textField)) {
            String message = textField.getText().strip();
            if (message.length() != 0) {
                if (message.toUpperCase().equals(message)) {
                    message += " [!!Yelling!!]";
                }
                chatArea.append("You: " + message + "\n");
                textField.setText("");
                try {
                    _texter.SendMsg(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
