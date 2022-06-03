package robot;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * @author Dominik Żebracki
 */
@FunctionalInterface
@SuppressFBWarnings(value = "THROWS_METHOD_THROWS_CLAUSE_BASIC_EXCEPTION")
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
