package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Dominik Żebracki
 */
class BookCollector implements Callable<Books>{

    private final List<BookstoreScrapper> scrappers;
    private final ExecutorService executor;

    BookCollector(List<BookstoreScrapper> scrappers, ExecutorService executor) {
        this.scrappers = scrappers;
        this.executor = executor;
    }

    @Override
    public Books call() {
        var collectedBooks = new Books(new ArrayList<>());
        try {
            var futures = executor.invokeAll(scrappers);
            for(Future<Books> future : futures) {
                    collectedBooks.concat(future.get(5, TimeUnit.MINUTES));
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new CollectingBookException("Error occurred during collecting books", e);
        }
        return collectedBooks;
    }
}
