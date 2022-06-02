package robot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dominik Żebracki
 */
@Configuration
class Beans {

    @Bean
    ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    BookCollector bookCollector(ExecutorService executorService) {
        return new BookCollector(List.of(new BonitoScrapper(), new GandalfScrapper()), executorService);
    }

    @Bean
    UserNotificationService userNotificationService() {
        return new UserNotificationService();
    }

    @Bean
    ActionTriggerer actionTriggerer(UserNotificationService userNotificationService,
                                    BookCollector bookCollector,
                                    ExecutorService executorService) {
        return new ActionTriggerer(
                userNotificationService,
                new TriggerTime(17, 59),
                bookCollector,
                executorService);
    }

    @Bean
    ScrapperService scrapperService(ExecutorService executorService, ActionTriggerer actionTriggerer) {
        ActionScheduler.create().addAction(CollectingBooksJob.toDefaultAction()).start();
        return new ScrapperService(executorService, actionTriggerer);
    }
}
