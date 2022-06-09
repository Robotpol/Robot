package robot;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mariusz Bal
 */
class BonitoScrapper implements BookstoreScrapper {

    @Override
    public Books call() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments(List.of("--headless", "--disable-gpu")));
        driver.get("https://bonito.pl/kategoria/ksiazki/?sale=1");

        int pages = findPageCount(driver);

        List<Book> books = new ArrayList<>();
        loopPages(driver, pages, books);

        driver.quit();
        return new Books(books);
    }

    private void loopPages(WebDriver driver, int pages, List<Book> books) {
        for (int i = 0; i < 10; i++) {
            var booksElements = driver.findElements(By.className("product_box"));
            booksElements.stream().map(this::tryBookScrap).filter(Objects::nonNull).forEach(books::add);
            clickNextPage(driver);
        }
    }

    private Book tryBookScrap(WebElement book) {
        try {
            return readBookInfo(book);
        } catch (NoSuchElementException e) {
            System.err.printf("Unable to scrap %s element. Skipping.%n", book.getText());
            return null;
        }
    }

    private void clickNextPage(WebDriver driver) {
        driver.findElements(By.xpath("//img[contains(@src,'arrow-right-grey.svg')]")).get(0).click();
    }

    private int findPageCount(WebDriver driver) {
        return Integer.parseInt(
                driver.findElements(By.xpath("//div[contains(@class, 'H4L') " +
                                "and contains(@class, 'color-light') and contains(@class, 'text-nowrap')]"))
                        .get(0).getText().split(" ")[2]);
    }

    private Book readBookInfo(WebElement book) {
        var title = book.findElement(By.className("mb-2")).getText();
        var authorPublisherSection = book.findElements(
                By.xpath(".//div[contains(@class, 'T2L') and contains(@class, 'color-dark')]"));
        var author = authorPublisherSection.get(0).getText();
        var oldPrice = book.findElement(By.xpath(".//span[contains(@class, 'T2L') " +
                "and contains(@class, 'text-line-through') and contains (@class, 'me-1')]")).getText();
        var newPrice = book.findElement(By.xpath(".//span[contains(@class, 'H3B') " +
                "and contains(@class, 'me-1')]")).getText();
        var link = book.findElement(By.tagName("a")).getAttribute("href");

        return new Book(title, author, transformPrice(oldPrice), transformPrice(newPrice), link);
    }
}
