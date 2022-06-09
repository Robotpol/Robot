package robot;

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
        return books.stream()
                .map(b -> GandalfBook.builder()
                        .title(b.title())
                        .author(b.title())
                        .oldPrice(b.oldPrice())
                        .price(b.price())
                        .link(b.link())
                        .build())
                .toList();
    }

    private GandalfBookMapper(){}
}
