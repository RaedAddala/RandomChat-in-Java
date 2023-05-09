import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServer {
    public static void main (String [ ] argv) {
        try {

            //System.setSecurityManager(new RMISecurityManager());
            RoomTable table = new RoomTable();
            QueueManager.GetInstance().roomTable = table;
            System.setProperty("java.rmi.server.hostname", "0.0.0.0");
            LocateRegistry.createRegistry(7894);
            Naming.rebind ("rmi://0.0.0.0:7894/ChatServer", new Logger());
            System.out.println ("Serveur prêt.") ;
        } catch (Exception e) {
            System.out.println ("Erreur serveur : " + e) ;
            e.printStackTrace();
        }
    }
}
