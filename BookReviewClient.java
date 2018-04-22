import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BookReviewClient {

    protected BookReviewClient() throws RemoteException {
    }

    public static void main(String args[]) {
        try {
            BookList bookList = (BookList) Naming.lookup("rmi://localhost:1099/BookList");
            bookList.addBookReview("Andreas Book", "en kalo...");
            Book aBook = bookList.getBook("Andreas Book", new CallbackObjectImpl());
            System.out.println(aBook.getName());
            bookList.getBook("Marios Book", new CallbackObjectImpl());
            bookList.addBookReview("Marios Book", "ee ntaksi");
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("MalformedUrl Exception");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("NotBound Exception");
            e.printStackTrace();
        }
    }

}

class CallbackObjectImpl extends UnicastRemoteObject implements CallbackObject {

    protected CallbackObjectImpl() throws RemoteException {
    }

    @Override
    public void notifyClients(Book book) throws RemoteException {
        System.out.println("Got Book " + book.name);
    }
}
