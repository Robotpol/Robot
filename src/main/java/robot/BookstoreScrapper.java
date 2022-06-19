package robot;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Dominik Żebracki
 */
interface BookstoreScrapper extends Callable<Books> {
    Books call();

    void loopPages(Document document, int pages, List<Book> books) throws IOException;

    Document nextPage(int i) throws IOException;

    Book tryBookScrap(Element book);

    int findPageCount(Document document);

    Book readBookInfo(Element book);

    default void waitForPageLoad(WebDriver driver) {
        while (true) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String result = js.executeScript("return document.readyState").toString();
            if (result.equals("complete")) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    default BigDecimal transformPrice(String price) {
        return new BigDecimal(replaceCommaInPrice(trimZl(price)));
    }

    default void printInfo(Bookstore bookstore, String text) {
        System.out.printf("[%s]: %s%n", bookstore.name(), text);
    }

    private String trimZl(String price) {
        return price.replaceAll("[^\\d.,]", "");
    }

    private String replaceCommaInPrice(String price) {
        return price.replace(",", ".");
    }
}
