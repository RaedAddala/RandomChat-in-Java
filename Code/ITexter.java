import java.rmi.Remote;

public interface ITexter extends Remote {
    public void SendMsg(String message) throws Exception;
    public void QueueForChat() throws Exception;
    public void ExitChatRoom() throws Exception;
    public void QuitApp() throws Exception;
}
