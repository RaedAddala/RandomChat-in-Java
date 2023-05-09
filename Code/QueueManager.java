import java.rmi.RemoteException;
import java.util.*;

public class QueueManager {
    private static QueueManager manager = new QueueManager();
    private Hashtable<String, ConversationManager> activeUsers;
    private final List<Set<ConversationManager>> rooms;
    private List<ConversationManager> queue;
    public RoomTable roomTable;

    private QueueThread queueThread;
    public void ExitRoom(ConversationManager manager){
        int count = 0;
        for(Set<ConversationManager> room: rooms) {
            if(room.contains(manager)){
                roomTable.DeleteRoom(manager.GetId());
                Iterator<ConversationManager> iterator = room.iterator();
                while(iterator.hasNext()) {
                    ConversationManager cv = iterator.next();
                    cv.setConversant(null);
                    activeUsers.remove(cv.GetId());
                }
                rooms.remove(count);
                break;
            }
            count++;
        }
    }
    public static QueueManager GetInstance(){
        return manager;
    }
    public void AddToQueue(ConversationManager manager) throws Exception{
        if(manager.IsInRoom()){
            System.out.println(manager.GetId() + " is already in a room.");
            throw new Exception("User is already in a room");
        }
        if(queue.contains(manager)){
            System.out.println(manager.GetId() + " is already in the queue.");
            throw new Exception("User is already in the queue");
        }
        try {
            manager._listener.EnterQueueNotice();
            queue.add(manager);
            queueThread.Wakeup();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    private QueueManager(){
        queue = new ArrayList<ConversationManager>();
        activeUsers = new Hashtable<String, ConversationManager>();
        rooms = new ArrayList<Set<ConversationManager>>();
        queueThread = new QueueThread();
        queueThread.start();
    }
    private class QueueThread extends Thread{
        public synchronized void run(){
            while(true){
                if(queue.size()>1){
                ConversationManager conversant = queue.get(0);
                ConversationManager manager = queue.get(1);
                try {
                    Set<ConversationManager> room = new HashSet<ConversationManager>();
                    try{
                        conversant.AcceptQueue();
                    }catch(Exception e){
                        queue.remove(conversant);
                        continue;
                    }
                    try{
                        manager.AcceptQueue();
                    }catch(Exception e){
                        queue.remove(manager);
                        continue;
                    }
                    queue.remove(conversant);
                    queue.remove(manager);
                    room.add(manager);
                    room.add(conversant);
                    rooms.add(room);
                    activeUsers.put(manager.GetId(), manager);
                    activeUsers.put(conversant.GetId(), conversant);
                    manager.setConversant(conversant);
                    conversant.setConversant(manager);
                    roomTable.PushRoom(conversant.GetId(), manager.GetId());
                    System.out.println("Room is ready for Id "+manager.GetId()+" and Id "+conversant.GetId());
                } catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                    ExitRoom(manager);
                }
                }else{
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        public synchronized void Wakeup(){
            this.notify();
        }
    }

}
