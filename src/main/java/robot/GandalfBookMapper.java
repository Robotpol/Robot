package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
final class GandalfBookMapper {

    static List<Book> toBook(List<GandalfBook> books) {
        return books.stream()
                .map(b -> new Book(b.getTitle(),
                        b.getAuthor(),
                        b.getOldPrice(),
                        b.getPrice(),
                        b.getLink()))
                .collect(Collectors.toList());
    }

    static List<GandalfBook> toGandalfBook(List<Book> books) {
        var idCounter = 1;
        var gandalfBooks = new ArrayList<GandalfBook>();
        for (Book book : books) {
            gandalfBooks.add(new GandalfBook(
                    String.valueOf(idCounter++),
                    book.title(),
                    book.author(),
                    book.oldPrice(),
                    book.price(),
                    book.link()));
        }
        return gandalfBooks;
    }
}
