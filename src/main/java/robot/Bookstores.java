package robot;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
@Service
class Bookstores {

    private final Map<String, BookProvider> bookProviders;
    // TODO replace with RDBMS
    private final Map<String, Books> bookstores;

    private final ScrappedBookService scrappedBookService;

    Bookstores(Collection<BookProvider> bookProvidersRegisteredInContext, ScrappedBookService scrappedBookService) {
        bookProviders = bookProvidersRegisteredInContext
                .stream()
                .collect(Collectors.toMap(BookProvider::toString, Function.identity()));
        this.scrappedBookService = scrappedBookService;
        bookstores = bookProviders.keySet()
                .stream()
                .map(k -> Map.entry(k, new Books(new ArrayList<>())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void update(String... bookstoreName) {
        try {
            processCollectingResults(BookstoresCollector.collectFrom(Arrays.stream(bookstoreName)
                    .map(bookProviders::get)
                    .toList()));
        } catch (CollectingException e) {
            //TODO add a proper logging message. User also should be probably notified so something should be returned.
        }
    }

    void updateAll() {
        try {
            processCollectingResults(BookstoresCollector.collectFrom(bookProviders.values().stream().toList()));
        } catch (CollectingException e) {
            //TODO add a proper logging message. User also should be probably notified so something should be returned.
        }
    }

    Books getBooks(String bookstoreName, Map<String, String> filters) {
        return scrappedBookService.filter(bookstoreName, filters.get("title"),
                filters.get("author"), filters.get("min"), filters.get("max"));
    }

    private void processCollectingResults(Iterable<CollectingResult> results) {
        results.forEach(r -> scrappedBookService.save(r.providerName(), r.books()));
    }

    @Override
    public String toString() {
        return bookstores.toString();
    }
}
