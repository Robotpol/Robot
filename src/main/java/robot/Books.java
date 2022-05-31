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

    Books concat(Books other) {
        var newBooks = new ArrayList<Book>();
        newBooks.addAll(this.books);
        newBooks.addAll(other.books);
        return new Books(newBooks);
    }

    @Override
    public String toString() {
        return "Books{" +
                "books=" + books +
                '}';
    }
}
