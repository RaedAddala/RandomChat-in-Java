public class ConversationManager {
    public static int count = 0;
    public ITextReceiver _listener;
    private ConversationManager _Conversant;
    private String id;
    private boolean inRoom;
    public boolean IsInRoom(){
        return inRoom;
    }
    public void setConversant(ConversationManager manager){
        inRoom = !(manager==null);
        if(!inRoom){
            try {
                _listener.ReceiveQuitNotice();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        _Conversant = manager;
    }
    public String GetConversantid(){
        return _Conversant.GetId();
    }
    public void AcceptQueue() throws Exception {
        try {
            _listener.ConversationAcceptedNoticed();
        } catch (Exception e) {
            throw new Exception(this.GetId() + " couldn't connect to room.");
        }
    }
    public void SetListener(ITextReceiver listener){
        _listener = listener;
    }
    public ConversationManager(){
        id = Integer.toString(count, 16);
        count++;
        inRoom = false;
        System.out.println("New user joined id: "+id);
    }
    public void SendMessage(String message) throws Exception {
        try {
            _Conversant._listener.ReceiveText(message);
        } catch (Exception e) {
            inRoom = false;
            throw new Exception(_Conversant.GetId() + " disconnected.");
        }
    }
    public String GetId(){
        return id;
    }
}
