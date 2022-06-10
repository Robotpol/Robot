package robot;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * @author Dominik Żebracki
 */
@FunctionalInterface
interface BookstoreScrapper extends Callable<Books> {
    Books call();

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

    private String trimZl(String price) {
        return price.replaceAll("[^\\d.,]", "");
    }

    private String replaceCommaInPrice(String price) {
        return price.replace(",", ".");
    }
}
