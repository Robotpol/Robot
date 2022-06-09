package robot;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Dominik Żebracki
 */
@Component
class RegisteredBookProviders {

    private Map<String, BookProvider> bookProviders;

    RegisteredBookProviders(Set<BookProvider> bookProvidersRegisteredInContext) {
        bookProviders = bookProvidersRegisteredInContext.stream()
                .collect(Collectors.toMap(BookProvider::toString, Function.identity()));
    }
}
