package robot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
class Books {

    private final List<Book> books;

    Books(List<Book> books) {
        this.books = books;
    }

    void concat(Books other) {
       books.addAll(other.books);
    }

    @Override
    public String toString() {
        return "Books{" +
                "books=" + books +
                '}';
    }
}
