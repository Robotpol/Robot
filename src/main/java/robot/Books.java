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

    Books() {
        books = new ArrayList<>();
    }

    static Books fromList(List<Books> books) {
        return new Books(books.stream()
                .flatMap(b -> b.books.stream())
                .toList());
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
