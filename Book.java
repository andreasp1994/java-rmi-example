import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Book implements Serializable {
    String name;
    ArrayList<String> reviews;

    public Book(String name) {
        this.name = name;
        this.reviews = new ArrayList<>();
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void addReview(String review) {
        this.reviews.add(review);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

