package robot.pwn;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import robot.Book;
import robot.Books;
import robot.Bookstore;
import robot.BookstoreScrapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Dominik Żebracki
 */
class PwnScrapperJsoup implements BookstoreScrapper {

    public PwnScrapperJsoup(String link) {

    }

    @Override
    public Books call() {
        System.out.println(Bookstore.PWN);
        return new Books(List.of(new Book("test-book",
                "test-author",
                BigDecimal.ONE,
                BigDecimal.TEN,
                "link")));
    }

    @Override
    public void loopPages(Document document, int pages, List<Book> books) throws IOException {

    }

    @Override
    public Document nextPage(int i) throws IOException {
        return null;
    }

    @Override
    public Book tryBookScrap(Element book) {
        return null;
    }

    @Override
    public int findPageCount(Document document) {
        return 0;
    }

    @Override
    public Book readBookInfo(Element book) {
        return null;
    }
}
