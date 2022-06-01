package robot;

import java.util.concurrent.Callable;

/**
 * @author Dominik Żebracki
 */
interface BookstoreScrapper extends Callable<Books> {
    Books call();
}
