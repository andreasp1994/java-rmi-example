import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BookList extends Remote {
    Book getBook(String bookName, CallbackObject callbackObject) throws RemoteException;
    void addBookReview(String bookName, String reviewText) throws RemoteException;
}
