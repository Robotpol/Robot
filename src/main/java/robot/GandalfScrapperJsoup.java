package robot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.NoSuchElementException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Mariusz Bal
 */
class GandalfScrapperJsoup implements BookstoreScrapper {
    private final String url = "https://www.gandalf.com.pl/promocje/bcb";

    @Override
    public Books call() {
        try {
            Document document = Jsoup.parse(new URL(url), 10000);
            int pages = findPageCount(document);
            List<Book> books = new ArrayList<>();
            loopPages(document, pages, books);

            return new Books(books);
        } catch (IOException e) {
            return new Books(Collections.emptyList());
        }
    }

    private void loopPages(Document document, int pages, List<Book> books) throws IOException {
        for (int i = 0; i < 10; i++) {
            printInfo(Bookstore.GANDALF, "---- Page #" + (i + 1));
            var booksSection = document.getElementById("list-of-filter-products");
            var booksElements = Objects.requireNonNull(booksSection).getElementsByClass("info-box");
            booksElements.stream().map(this::tryBookScrap).filter(Objects::nonNull).forEach(books::add);
            document = Jsoup.parse(new URL(url + (i + 1)), 10000);
        }
        printInfo(Bookstore.GANDALF, "---- Done");
    }

    private Book tryBookScrap(Element book) {
        try {
            return readBookInfo(book);
        } catch (NoSuchElementException e) {
            System.err.printf("Unable to scrap %s element. Skipping.%n", book.text());
            return null;
        }
    }

    private int findPageCount(Document document) {
        return Integer.parseInt(document.getElementsByClass("max-pages").get(0).text());
    }

    private Book readBookInfo(Element book) {
        var titleElement = book.getElementsByClass("title").get(0);
        var title = titleElement.text();
        var author = book.getElementsByClass("author").text();
        var oldPrice = book.getElementsByClass("old-price").text();
        var newPrice = book.getElementsByClass("current-price").text();
        var link = titleElement.attr("href");
        printInfo(Bookstore.GANDALF, title);
        return new Book(title, author, transformPrice(oldPrice), transformPrice(newPrice), link);
    }
}
