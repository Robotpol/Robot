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
class BonitoScrapperJsoup implements BookstoreScrapper {

    private final String url = "https://bonito.pl/kategoria/ksiazki/?results=L3YxL3NlYXJjaC9wcm9kdWN0cy8/Y2F0ZWdvcnk9a3NpYXpraQ==&page=";

    @Override
    public Books call() {
        try {
            Document document = Jsoup.parse(new URL(url + 1), 10000);
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
            printInfo(Bookstore.BONITO, "---- Page #" + (i + 1));
            var booksElements = document.getElementsByClass("product_box");
            booksElements.stream().map(this::tryBookScrap).filter(Objects::nonNull).forEach(books::add);
            document = nextPage(i);
        }
        printInfo(Bookstore.BONITO, "---- Done");
    }

    @Override
    public Document nextPage(int i) throws IOException {
        return Jsoup.parse(new URL(url + (i + 2)), 10000);
    }

    @Override
    public Book tryBookScrap(Element book) {
        try {
            return readBookInfo(book);
        } catch (NumberFormatException e) { //NFE when the book is unavailable - hence no new price
            System.err.printf("Unable to scrap %s element. Skipping.%n", book.text());
            return null;
        }
    }

    @Override
    public int findPageCount(Document document) {
        return Integer.parseInt(document.select("div.H4L.color-light.text-nowrap")
                .text().split(" ")[2]);
    }

    @Override
    public Book readBookInfo(Element book) {
        var title = book.getElementsByClass("mb-2").get(0).text();
        var authorPublisherSection = book.selectXpath(".//div[contains(@class, 'T2L') " +
                "and contains(@class, 'color-dark')]");
        var author = authorPublisherSection.get(0).text();
        var oldPrice = book.selectXpath(".//span[contains(@class, 'T2L') " +
                "and contains(@class, 'text-line-through') and contains (@class, 'me-1')]").text();
        var newPrice = book.selectXpath(".//span[contains(@class, 'H3B') " +
                "and contains(@class, 'me-1')]").text();
        var link = book.getElementsByTag("a").get(0).absUrl("href");
        printInfo(Bookstore.BONITO, title);
        return new Book(title, author, transformPrice(oldPrice), transformPrice(newPrice), link);
    }
}
