package robot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
class BookstoresCollector {

    private static final int DEFAULT_TIMEOUT_IN_MINUTES = 8;

    static ArrayList<CollectingResult> collectFrom(List<BookProvider> bookProviders) {
        try {
            var tasks = createTasks(bookProviders);
            return CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
                    .thenApply(f -> tasks.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toCollection(ArrayList::new)))
                    .get(DEFAULT_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Thread.currentThread().interrupt();
            // TODO add proper exception handling
            throw new RuntimeException("Error occurred during collecting books", e);
        }
    }

    private static List<CompletableFuture<CollectingResult>> createTasks(List<BookProvider> bookProviders) {
        return bookProviders.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> new CollectingResult(s.toString(), LocalDateTime.now(), s.updateAndProvideBooks()),
                                Executors.newCachedThreadPool())
                        .exceptionally(BookstoresCollector::handleException))
                .toList();
    }
// TODO add proper exception handling
    private static CollectingResult handleException(Throwable ex) {
        System.err.println("Error occurred in a scrapper " + ex.getMessage());
        return new CollectingResult("", LocalDateTime.now(), new Books(new ArrayList<>()));
    }

    record CollectingResult(String providerName, LocalDateTime collectedAt, Books books){}
}
