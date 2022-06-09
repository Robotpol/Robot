package robot;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
@Service
class Bookstores {

    private final Map<String, BookProvider> bookProviders;
    private final Map<String, Books> bookstores;

    Bookstores(Set<BookProvider> bookProvidersRegisteredInContext) {
        bookProviders = bookProvidersRegisteredInContext
                .stream()
                .collect(Collectors.toMap(BookProvider::toString, Function.identity()));
        bookstores = bookProviders.keySet()
                .stream()
                .map(k -> Map.entry(k, new Books(new ArrayList<>())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    void update(String ... bookstoreName) {
        var bookstoresToUpdate = Arrays.asList(bookstoreName);
        var result = BookstoresCollector.collectFrom(bookstoresToUpdate.stream()
                .map(bookProviders::get)
                .toList());
        processCollectingResults(result);
        System.out.println(result);
        System.out.println("\n\n\n" + bookstores.get(bookstoreName[0]));
    }

    void updateAll() {
        processCollectingResults(BookstoresCollector.collectFrom(bookProviders.values().stream().toList()));
        System.out.println(bookstores);
    }

    Books getBooks(String bookstoreName) {
        return bookstores.get(bookstoreName.toUpperCase(Locale.ROOT));
    }

    private void processCollectingResults(List<BookstoresCollector.CollectingResult> results) {
       for(BookstoresCollector.CollectingResult result : results) {
           if(!result.providerName().equals("")) {
               System.out.println("putting" + result.providerName());
               bookstores.remove(result.providerName());
               bookstores.put(result.providerName(), result.books());
           }
        }
    }

    @Override
    public String toString() {
        return bookstores.toString();
    }
}
