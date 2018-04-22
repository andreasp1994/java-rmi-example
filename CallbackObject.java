import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CallbackObject extends Remote {

    void notifyClients(Book book) throws RemoteException;
}
