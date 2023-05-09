import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class Logger extends UnicastRemoteObject implements ILogger {
    public Logger() throws Exception{
        super();
    }
    
    @Override
    public String RequestLogin(ITextReceiver listener) throws Exception{
        try{
             Manager manager = new Manager();
             manager.PushListener(listener);
             Naming.rebind ("rmi://localhost:7894/"+manager.GetId(), manager);
             return manager.GetId();
        }catch (Exception e){
            System.out.println(e.toString());
            throw e;
        }
    }
}
