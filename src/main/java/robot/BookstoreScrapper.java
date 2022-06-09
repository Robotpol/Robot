package robot;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * @author Dominik Żebracki
 */
@FunctionalInterface
interface BookstoreScrapper extends Callable<Books> {
    Books call();

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
