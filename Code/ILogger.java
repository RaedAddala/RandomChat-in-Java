import java.rmi.Remote;

public interface ILogger extends Remote {
    public String RequestLogin(ITextReceiver listener) throws Exception;
}
