package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
final class BonitoBookMapper {

    static List<Book> toBook(List<BonitoBook> books) {
        return books.stream()
                .map(b -> new Book(b.getTitle(),
                        b.getAuthor(),
                        b.getOldPrice(),
                        b.getPrice(),
                        b.getLink()))
                .collect(Collectors.toList());
    }

    static List<BonitoBook> toBonitoBook(List<Book> books) {
        var idCounter = 1;
        var bonitoBooks = new ArrayList<BonitoBook>();
        for (Book book : books) {
            bonitoBooks.add(new BonitoBook(
                    String.valueOf(idCounter++),
                    book.title(),
                    book.author(),
                    book.oldPrice(),
                    book.price(),
                    book.link()));
        }
        return bonitoBooks;
    }
}
