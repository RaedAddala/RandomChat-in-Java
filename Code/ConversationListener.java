import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConversationListener extends UnicastRemoteObject implements ITextReceiver{
    private ChatBox _chatbox;
    private ITexter _texter;
    public ConversationListener() throws RemoteException {
        super();

    }
    public void PushChatBox(ChatBox box){
        _chatbox = box;
    }
    protected void finalize(){
        if(_texter != null){
            try {
                _texter.QuitApp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void PushTexter(ITexter texter){
        _texter = texter;
    }
    @Override
    public void ReceiveText(String message) throws Exception{
        try{
        _chatbox.DisplayText("Stranger: "+message);
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void ReceiveQuitNotice() throws RemoteException  {
        _chatbox.OnStrangerQuit();
    }

    @Override
    public void EnterQueueNotice() throws RemoteException{
        _chatbox.DisplayText("-System-: searching for a person to chat with...");
    }

    @Override
    public void ConversationAcceptedNoticed() throws Exception {
        _chatbox.EnterRoomNotice();
    }

}
