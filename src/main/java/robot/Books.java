package robot;

import java.util.List;

/**
 * @author Mariusz Bal
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
