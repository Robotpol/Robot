package robot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
class BookCollector {

    private static final int DEFAULT_TIMEOUT_IN_MINUTES = 8;

    Books collectFrom(List<BookstoreScrapper> scrappers) {
        try {
            var tasks = createTasks(scrappers);
            return Books.fromList(CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
                    .thenApply(f -> tasks.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toCollection(ArrayList::new)))
                    .get(DEFAULT_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Thread.currentThread().interrupt();
            throw new CollectingBookException("Error occurred during collecting books", e);
        }
    }

    private List<CompletableFuture<Books>> createTasks(List<BookstoreScrapper> scrappers) {
        return scrappers.stream()
                .map(s -> CompletableFuture.supplyAsync(s::call, Executors.newCachedThreadPool())
                        .exceptionally(this::handleException))
                .toList();
    }

    private Books handleException(Throwable ex) {
        System.err.println("Error occurred in a scrapper " + ex.getMessage());
        return new Books();
    }
}
