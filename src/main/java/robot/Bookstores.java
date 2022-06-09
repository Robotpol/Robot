package robot;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
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

    void update(String bookstoreName) {
        bookstores.put(bookstoreName, bookProviders.get(bookstoreName).updateAndProvideBooks());
    }

    void updateAll() {
        bookstores.keySet().forEach(this::update);
    }

    Books getBooks(String bookstoreName) {
        return bookstores.get(bookstoreName);
    }

    @Override
    public String toString() {
        return bookstores.toString();
    }
}
