package robot;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mariusz Bal
 */
class GandalfScrapper implements BookstoreScrapper {

    @Override
    public Books call() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments(List.of(
                "headless",
                "window-size=1920,1080"
        )));
        driver.get("https://www.gandalf.com.pl/promocje/bcb");

        int pages = findPageCount(driver);

        List<Book> books = new ArrayList<>();
        loopPages(driver, pages, books);

        driver.quit();
        return new Books(books);
    }

    private void loopPages(WebDriver driver, int pages, List<Book> books) {
        for (int i = 0; i < pages; i++) {
            waitForBooksToLoad(driver);
            var booksElements = driver.findElements(By.className("info-box"));
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

    private void clickNextPage(WebDriver driver) { //todo extract to the interface with findPageCount and readBookInfo?
        driver.findElement(By.className("next")).click();
    }

    private void waitForBooksToLoad(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.domPropertyToBe(driver.findElement(By.id("list-of-filter-products")),
                        "className", ""));
    }

    private int findPageCount(WebDriver driver) {
        return Integer.parseInt(driver.findElements(By.className("max-pages")).get(0).getText());
    }

    private Book readBookInfo(WebElement book) {
        var title = book.findElement(By.className("title")).getText();
        var author = book.findElement(By.className("author")).getText();
        var oldPrice = book.findElement(By.className("old-price")).getText();
        var newPrice = book.findElement(By.className("current-price")).getText();
        return new Book(title, author, transformPrice(oldPrice), transformPrice(newPrice));
    }
}
