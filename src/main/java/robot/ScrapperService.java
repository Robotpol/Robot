package robot;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author Dominik Żebracki
 */
class ScrapperService {

    private final List<BookstoreScrapper> scrappers;
    private TriggerTime triggerTime;
    private Executor executor;

    ScrapperService(List<BookstoreScrapper> scrappers, TriggerTime triggerTime, Executor executor) {
        this.scrappers = scrappers;
        this.triggerTime = triggerTime;
        this.executor = executor;
    }
}
