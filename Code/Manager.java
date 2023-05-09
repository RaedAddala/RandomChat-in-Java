import java.rmi.RemoteException;

public class Manager extends java.rmi.server.UnicastRemoteObject  implements ITexter {
    public ConversationManager _conversationManager;
    private QueueManager _queueManager;
    public Manager() throws RemoteException {
        super();
        _conversationManager = new ConversationManager();
        _queueManager = QueueManager.GetInstance();
    }
    public String GetId(){
        return _conversationManager.GetId();
    }
    @Override
    public void SendMsg(String message) throws Exception {
        if(_conversationManager.IsInRoom()){
            try {
                _conversationManager.SendMessage(message);
            }catch (Exception e){
                System.out.println(GetId()+":"+e.getMessage());
                ExitChatRoom();
            }
        }else{
            throw new Exception("User is not in a room");
        }
    }
    public void PushListener(ITextReceiver listener){
        _conversationManager.SetListener(listener);
    }
    @Override
    public void QueueForChat() throws Exception{
        try{
        _queueManager.AddToQueue(_conversationManager);
        }catch (Exception e){
            throw new Exception( this.GetId()+" couldn't pair");
        }
    }

    @Override
    public void ExitChatRoom() throws Exception{
        _queueManager.ExitRoom(_conversationManager);
    }

    @Override
    public void QuitApp() throws Exception {
        _queueManager.ExitRoom(_conversationManager);
        System.out.println("Id  "+_conversationManager.GetId()+" left.");
    }
}
