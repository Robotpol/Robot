package robot;

import java.math.BigDecimal;

/**
 * @author Dominik Żebracki
 */
record Book(
        String title,
        String author,
        BigDecimal price) {
}
