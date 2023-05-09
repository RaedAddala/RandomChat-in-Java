import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITextReceiver extends Remote {
    public void ReceiveText(String message) throws Exception;
    public void ReceiveQuitNotice() throws RemoteException;
    public void EnterQueueNotice() throws RemoteException;
    public void ConversationAcceptedNoticed() throws Exception;
}
