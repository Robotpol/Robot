package robot.gandalf;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import robot.Book;
import robot.Books;
import robot.BookstoreScrapper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Mariusz Bal
 */
class GandalfScrapper implements BookstoreScrapper {

    @Override
    public Books call() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments(List.of("--no-sandbox", "--headless", "--disable-gpu")));
        driver.get("https://www.gandalf.com.pl/promocje/bcb");
        waitForPageLoad(driver);

        int pages = findPageCount(driver);

        List<Book> books = new ArrayList<>();
        loopPages(driver, pages, books);

        driver.quit();
        return new Books(books);
    }


    private void loopPages(WebDriver driver, int pages, List<Book> books) {
        for (int i = 0; i < 10; i++) {
            waitForPageLoad(driver);
            var booksSection = driver.findElement(By.id("list-of-filter-products"));
            var booksElements = booksSection.findElements(By.className("info-box"));
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
        driver.findElement(By.className("next")).click();
    }

    private int findPageCount(WebDriver driver) {
        return Integer.parseInt(driver.findElements(By.className("max-pages")).get(0).getText());
    }

    private Book readBookInfo(WebElement book) {
        var titleElement = book.findElement(By.className("title"));
        var title = titleElement.getText();
        var author = book.findElement(By.className("author")).getText();
        var oldPrice = book.findElement(By.className("old-price")).getText();
        var newPrice = book.findElement(By.className("current-price")).getText();
        var link = titleElement.getAttribute("href");
        System.out.println("[GANDALF]: " + title);
        return new Book(title, author, transformPrice(oldPrice), transformPrice(newPrice), link);
    }
}
