package robot.gandalf;

import robot.Book;

import java.time.LocalDateTime;
import java.util.List;

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
                .toList();
    }

    static List<GandalfBook> toGandalfBook(List<Book> books) {
        var currentTime = LocalDateTime.now();
        return books.stream()
                .map(b -> GandalfBook.builder()
                        .title(b.title())
                        .author(b.author())
                        .oldPrice(b.oldPrice())
                        .price(b.price())
                        .link(b.link())
                        .createdAt(currentTime)
                        .build())
                .toList();
    }

    private GandalfBookMapper(){}
}
