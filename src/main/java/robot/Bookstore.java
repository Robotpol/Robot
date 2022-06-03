package robot;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public enum Bookstore {
    GANDALF(new GandalfScrapper()),
    BONITO(new BonitoScrapper());

    private final BookstoreScrapper scrapper;

    Bookstore(BookstoreScrapper scrapper) {
        this.scrapper = scrapper;
    }

    @SuppressFBWarnings("EI_EXPOSE_REP")
    public BookstoreScrapper getScrapper() {
        return scrapper;
    }
}
