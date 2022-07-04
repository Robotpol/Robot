package robot.bonito;

import robot.Book;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
interface BonitoBookMapper {

    static List<Book> toBook(Collection<BonitoBook> books) {
        return books.stream()
                .map(b -> new Book(b.getTitle(),
                        b.getAuthor(),
                        b.getOldPrice(),
                        b.getPrice(),
                        b.getLink()))
                .toList();
    }

    static List<BonitoBook> toBonitoBook(Collection<Book> books) {
        var currentTime = LocalDateTime.now();
        return books.stream()
                .map(b -> BonitoBook.builder()
                        .title(b.title())
                        .author(b.author())
                        .oldPrice(b.oldPrice())
                        .price(b.price())
                        .link(b.link())
                        .createdAt(currentTime)
                        .build())
                .toList();
    }
}
