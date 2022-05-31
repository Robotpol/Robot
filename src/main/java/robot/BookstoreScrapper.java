package robot;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Dominik Żebracki
 */
interface BookstoreScrapper extends Callable<Books> {
    Books call();
}
