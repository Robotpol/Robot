package robot;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public enum Bookstore {
    GANDALF(new GandalfScrapper(new ChromeDriver(new ChromeOptions().addArguments(List.of("--headless", "--disable-gpu"))))),
    BONITO(new BonitoScrapper(new ChromeDriver(new ChromeOptions().addArguments(List.of("--headless", "--disable-gpu")))));

    private final BookstoreScrapper scrapper;

    Bookstore(BookstoreScrapper scrapper) {
        this.scrapper = scrapper;
    }

    public BookstoreScrapper getScrapper() {
        return scrapper;
    }
}
