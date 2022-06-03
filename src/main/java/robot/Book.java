package robot;

import java.math.BigDecimal;

/**
 * @author Mariusz Bal
 */
record Book(String title, String author, BigDecimal oldPrice, BigDecimal newPrice) {}
