package robot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Dominik Żebracki
 */
class BookstoresCollector {

    private static final int DEFAULT_TIMEOUT_IN_MINUTES = 8;
    //TODO add a logger to log exceptions

    static List<CollectingResult> collectFrom(List<BookProvider> bookProviders) throws CollectingException {
        var executor = Executors.newFixedThreadPool(bookProviders.size());
        var tasks = createTasks(bookProviders, executor);
        try {
            return CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]))
                    .thenApply(f -> tasks.stream()
                            .map(CompletableFuture::join)
                            .toList())
                    .get(DEFAULT_TIMEOUT_IN_MINUTES, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CollectingException("Error occurred during collecting books", e);
        } catch (ExecutionException | TimeoutException e) {
            System.err.println("Error occurred during collecting books");
            throw new CollectingException("Error occurred during collecting books", e);
        } finally {
            executor.shutdown();
        }
    }

    private static List<CompletableFuture<CollectingResult>> createTasks(List<BookProvider> bookProviders,
                                                                         ExecutorService executor) {
        return bookProviders.stream()
                .map(s -> CompletableFuture.supplyAsync(
                        () -> new CollectingResult(s.toString(), LocalDateTime.now(), s.updateAndProvideBooks()), executor)
                        .exceptionally(BookstoresCollector::handleException))
                .toList();
    }

    private static CollectingResult handleException(Throwable ex) {
        System.err.println("Error occurred in a scrapper " + ex.getMessage());
        return new CollectingResult("", LocalDateTime.now(), new Books(new ArrayList<>()));
    }

    record CollectingResult(String providerName, LocalDateTime collectedAt, Books books){}
}
