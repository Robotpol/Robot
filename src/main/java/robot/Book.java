package robot;

import java.math.BigDecimal;

/**
 * @author Dominik Żebracki
 */
public record Book(
        String title,
        String author,
        BigDecimal oldPrice,
        BigDecimal price,
        String link) {
}
