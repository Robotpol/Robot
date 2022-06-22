package robot;

import java.time.LocalDateTime;

/**
 * @author Dominik Żebracki
 */
record CollectingResult(String providerName, LocalDateTime collectedAt, Books books) {
}
