package robot;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.Callable;

/**
 * @author Dominik Żebracki
 */
@SuppressFBWarnings(value = "THROWS_METHOD_THROWS_CLAUSE_BASIC_EXCEPTION")
interface BookstoreScrapper extends Callable<Books> {
    Books call();
}
