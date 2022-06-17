package robot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
            Document document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")  
                .timeout(10000).execute().parse();
            int pages = findPageCount(document);
            List<Book> books = new ArrayList<>();
            loopPages(document, pages, books);

            return new Books(books);
        } catch (IOException e) {
            return new Books(Collections.emptyList());
        }
    }

    @Override
    public void loopPages(Document document, int pages, List<Book> books) throws IOException {
        for (int i = 0; i < pages; i++) {
            printInfo(Bookstore.GANDALF, "---- Page #" + (i + 1));
            var booksSection = document.getElementById("list-of-filter-products");
            var booksElements = Objects.requireNonNull(booksSection).getElementsByClass("info-box");
            booksElements.stream().map(this::tryBookScrap).filter(Objects::nonNull).forEach(books::add);
            document = nextPage(i);
        }
        printInfo(Bookstore.GANDALF, "---- Done");
    }

    @Override
    public Document nextPage(int i) throws IOException {
        return Jsoup.connect(url + (i + 1))
            .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
            .timeout(10000).execute().parse();
    }

    @Override
    public Book tryBookScrap(Element book) {
        try {
            return readBookInfo(book);
        } catch (NumberFormatException e) {
            System.err.printf("Unable to scrap %s element. Skipping.%n", book.text());
            return null;
        }
    }

    @Override
    public int findPageCount(Document document) {
        return Integer.parseInt(document.getElementsByClass("max-pages").get(0).text());
    }

    @Override
    public Book readBookInfo(Element book) {
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
