package robot.pwn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import robot.Book;
import robot.Books;
import robot.Bookstore;
import robot.BookstoreScrapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Dominik Żebracki
 */
class PwnScrapperJsoup implements BookstoreScrapper {
    private final String url;

    PwnScrapperJsoup(String url) {
        this.url = url;
    }

    @Override
    public Books call() {
        try {
            Document document = Jsoup.parse(new URL(url), 10000);
            int pages = findPageCount(document);
            System.out.println(pages);
            List<Book> books = new ArrayList<>();
            loopPages(document, 10, books);
            return new Books(books);
        } catch (IOException e) {
            return new Books(Collections.emptyList());
        }
    }

    @Override
    public void loopPages(Document document, int pages, List<Book> books) throws IOException {
        for (int i = 0; i < pages; i++) {
            printInfo(Bookstore.PWN, "---- Page #" + (i + 1));
            System.out.println(document.location());
            var booksElements = document.selectXpath("//div[contains(@class, 'emp-product-tile') " +
                    "and not(contains(@class, 'hidden'))]");
            booksElements.stream().map(this::tryBookScrap).filter(Objects::nonNull).forEach(books::add);
            document = nextPage(i);
        }
        printInfo(Bookstore.PWN, "---- Done");
    }

    @Override
    public Document nextPage(int i) throws IOException {
        return Jsoup.parse(new URL(url + "&page=" + (i + 2)), 10000);
    }

    @Override
    public Book tryBookScrap(Element book) {
        try {
            return readBookInfo(book);
        } catch (NumberFormatException e) { //NFE when the book has no old price provided
            System.err.printf("Unable to scrap %s element. Skipping.%n", book.text());
            return null;
        }
    }

    @Override
    public int findPageCount(Document document) {
        return document.getElementsByClass("sitePagination")
                .stream().mapToInt(e -> {
                    try {
                        return Integer.parseInt(e.selectXpath(".//a").text());
                    } catch (NumberFormatException ex) {
                        return 0;
                    }
                }).max().orElse(50);
    }

    @Override
    public Book readBookInfo(Element book) {
        var title = book.getElementsByClass("emp-info-title").text();
        var author = book.getElementsByClass("emp-info-authors").text();
        var oldPrice = book.getElementsByClass("emp-base-price").text();
        var newPrice = book.getElementsByClass("emp-sale-price-value").text();
        var link = book.getElementsByTag("a").get(0).absUrl("href");
        printInfo(Bookstore.PWN, title);
        return new Book(title, author, transformPrice(oldPrice), transformPrice(newPrice), link);
    }
}
