package robot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        return new BookCollector(List.of(this::sampleBooks1, this::sampleBooks2), executorService);
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
                new TriggerTime(16, 7),
                bookCollector,
                executorService);
    }

    @Bean
    ScrapperService scrapperService(ExecutorService executorService, ActionTriggerer actionTriggerer){
        return new ScrapperService(executorService, actionTriggerer);
    }

    private Books sampleBooks1() {
        var books = new ArrayList<Book>();
        books.add(new Book("title1", "Author1", BigDecimal.TEN, BigDecimal.valueOf(13.5)));
        books.add(new Book("title2", "Author2", BigDecimal.TEN, BigDecimal.valueOf(14.5)));
        books.add(new Book("title3", "Author3", BigDecimal.TEN, BigDecimal.valueOf(15.5)));
        books.add(new Book("title4", "Author4", BigDecimal.TEN, BigDecimal.valueOf(16.5)));
        return new Books(books);
    }

    private Books sampleBooks2() {
        var books = new ArrayList<Book>();
        books.add(new Book("title5", "Author5", BigDecimal.TEN, BigDecimal.valueOf(13.5)));
        books.add(new Book("title6", "Author6", BigDecimal.TEN, BigDecimal.valueOf(14.5)));
        books.add(new Book("title7", "Author7", BigDecimal.TEN, BigDecimal.valueOf(15.5)));
        books.add(new Book("title8", "Author8", BigDecimal.TEN, BigDecimal.valueOf(16.5)));
        return new Books(books);
    }
}
