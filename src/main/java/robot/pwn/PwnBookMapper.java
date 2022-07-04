package robot.pwn;

import robot.Book;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
interface PwnBookMapper {

    static List<PwnBook> toPwnBook(Collection<Book> books) {
        var currentTime = LocalDateTime.now();
        return books.stream()
                .map(b -> PwnBook.builder()
                        .title(b.title())
                        .author(b.author())
                        .oldPrice(b.oldPrice())
                        .price(b.price())
                        .link(b.link())
                        .createdAt(currentTime)
                        .build())
                .toList();
    }

    static List<Book> toBook(Collection<PwnBook> books) {
        return books.stream()
                .map(b -> new Book(b.getTitle(),
                        b.getAuthor(),
                        b.getOldPrice(),
                        b.getPrice(),
                        b.getLink()))
                .toList();
    }
}
