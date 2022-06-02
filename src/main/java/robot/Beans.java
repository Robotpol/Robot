package robot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dominik Żebracki
 */
@Configuration
class Beans {

    @Bean
    UserNotificationService userNotificationService() {
        return new UserNotificationService();
    }

    @Bean
    ScrapperService scrapperService() {
        ActionScheduler.create().addAction(CollectingBooksJob.toDefaultAction()).start();
        return new ScrapperService();
    }
}
