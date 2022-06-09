package robot;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Dominik Żebracki
 */
@Component
class OnStartupBookProvider implements ApplicationListener<ContextRefreshedEvent> {

    private final Bookstores bookstores;

    OnStartupBookProvider(Bookstores bookstores) {
        this.bookstores = bookstores;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        bookstores.updateAll();
    }
}
