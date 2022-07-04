package robot;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author Mariusz Bal
 */
@Service
class ScrappedBookService {

    private final ScrappedBookRepository repository;

    ScrappedBookService(ScrappedBookRepository repository) {
        this.repository = repository;
    }


    void save(String bookstore, Books books) {
        repository.saveAll(books
                .books()
                .stream()
                .map(b -> new ScrappedBook(0, bookstore, b.title(), b.author(),
                        b.oldPrice(), b.price(), b.link()))
                .toList());
    }

    Books filter(String bookstore, String title, String author, String minPrice, String maxPrice) {
        var list = new ArrayList<Book>();
        repository.find(
                        bookstore,
                        String.format("%%%s%%", title == null ? "" : title),
                        String.format("%%%s%%", author == null ? "" : author),
                        convert(maxPrice, 10_000.0),
                        convert(minPrice, 0.0))
                .forEach(b -> list.add(new Book(b.getTitle(), b.getAuthor(),
                        b.getOldPrice(), b.getPrice(), b.getLink())));
        return new Books(list);
    }

    private BigDecimal convert(String value, double def) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value));
        } catch (NumberFormatException | NullPointerException e) {
            return BigDecimal.valueOf(def);
        }
    }
}
