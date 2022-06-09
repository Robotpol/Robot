package robot;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Dominik Żebracki
 */
@Component
class OnStartupBookProvider implements ApplicationListener<ContextRefreshedEvent> {

    private final List<BookProvider> bookProviders;

    OnStartupBookProvider(List<BookProvider> bookProviders) {
        this.bookProviders = bookProviders;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.err.println("Invoked");
        bookProviders.forEach(BookProvider::updateBooks);
    }
}
