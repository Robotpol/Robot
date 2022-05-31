package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

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
                try {
                    Books results = future.get();
                    System.out.println(results);
                    collectedBooks.concat(results);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("end of try");
        } catch (InterruptedException e) {
            throw new CollectingBookException("Error occurred during collecting books", e);
        }
        return collectedBooks;
    }
}
