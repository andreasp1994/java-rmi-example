import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class BookListImpl extends UnicastRemoteObject implements BookList {

    private List<Book> bookList = Collections.synchronizedList(new ArrayList<Book>());
    private Map<String, HashSet<CallbackObject>> registeredClients =
            Collections.synchronizedMap(new HashMap<String, HashSet<CallbackObject>>());


    protected BookListImpl() throws RemoteException {
        super();
    }

    @Override
    public Book getBook(String bookName, CallbackObject callbackObject) {
        if (registeredClients.containsKey(bookName)) {
            registeredClients.get(bookName).add(callbackObject);
        } else {
            HashSet<CallbackObject> clients = new HashSet<>();
            clients.add(callbackObject);
            registeredClients.put(bookName, clients);
        }
        for (Book book : bookList) {
            if (book.name.equals(bookName)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public void addBookReview(String bookName, String reviewText) throws RemoteException {
        Book bookOfInterest = null;
        for (Book book : bookList) {
            if (book.name.equals(bookName)) {
                book.addReview(reviewText);
                bookOfInterest = book;
                break;
            }
        }
        if (bookOfInterest == null) {
            bookOfInterest = new Book(bookName);
            bookOfInterest.addReview(reviewText);
            bookList.add(bookOfInterest);
        }
        if (registeredClients.containsKey(bookName)) {
            for(CallbackObject callbackObjects : registeredClients.get(bookName)){
                callbackObjects.notifyClients(bookOfInterest);
            }
        }

    }

    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(1099);
            BookList bookList = new BookListImpl();
            Naming.rebind("rmi://localhost:1099/BookList", bookList);
            System.out.println("Server Started");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
