package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Dominik Żebracki
 */
class BookCollector {

    private final List<BookstoreScrapper> scrappers;
    private final ExecutorService executor;

    BookCollector(List<BookstoreScrapper> scrappers, ExecutorService executor) {
        this.scrappers = scrappers;
        this.executor = executor;
    }

    public Books getBooks() {
        var collectedBooks = new Books(new ArrayList<>());
        try {
            executor.invokeAll(scrappers)
                    .stream()
                    .map(r -> {
                        try {
                            return r.get(5, TimeUnit.MINUTES);
                        } catch (InterruptedException | ExecutionException | TimeoutException e) {
                            throw new CollectingBookException("Error occurred during collecting books", e);
                        }
                    })
                    .forEach(collectedBooks::concat);
        } catch (InterruptedException e) {
            throw new CollectingBookException("Error occurred during collecting books", e);
        }
        return collectedBooks;
    }
}
