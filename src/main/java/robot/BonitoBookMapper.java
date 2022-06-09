package robot;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
@Component
final class BonitoBookMapper {

    static List<Book> toBook(List<BonitoBook> books) {
        return books.stream()
                .map(b -> new Book(b.getTitle(),
                        b.getAuthor(),
                        b.getOldPrice(),
                        b.getPrice(),
                        b.getLink()))
                .toList();
    }

    static List<BonitoBook> toBonitoBook(List<Book> books) {
        return books.stream()
                .map(b -> BonitoBook.builder()
                        .title(b.title())
                        .author(b.author())
                        .oldPrice(b.oldPrice())
                        .price(b.price())
                        .link(b.link())
                        .build())
                .toList();
    }

    private BonitoBookMapper() {
    }
}
