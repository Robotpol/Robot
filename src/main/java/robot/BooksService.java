package robot;

import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@Service
public class BooksService {

    private final Map<Bookstore, Books> bookstores;

    public BooksService() {
        this.bookstores = new EnumMap<>(Bookstore.class);
    }

    void cacheBooks(Bookstore bookstore, Books books) {
        this.bookstores.put(bookstore, books);
    }

    Books getBooks(String bookstoreName) {
        Bookstore bookstore = Bookstore.valueOf(bookstoreName.toUpperCase(Locale.ROOT));
        return bookstores.get(bookstore);
    }

}
