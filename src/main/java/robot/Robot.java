package robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@SpringBootApplication
class Robot {

    public static void main(String[] args) {
        List<BookstoreScrapper> scrappers = List.of(() -> createBooks1(), () -> createBooks2());
        var bookCollector = new BookCollector(scrappers, Executors.newCachedThreadPool());
        var actionTriggerer = new ActionTriggerer(
                new UserNotificationService(),
                new TriggerTime(4, 4),
                bookCollector,
                Executors.newCachedThreadPool());
        var thread = new Thread(actionTriggerer);
        thread.start();
        SpringApplication.run(Robot.class, args);
    }

    private static Books createBooks1() {
        var books = new ArrayList<Book>();
        books.add(new Book("title1", "Author1", BigDecimal.valueOf(13.5)));
        books.add(new Book("title2", "Author2", BigDecimal.valueOf(14.5)));
        books.add(new Book("title3", "Author3", BigDecimal.valueOf(15.5)));
        books.add(new Book("title4", "Author4", BigDecimal.valueOf(16.5)));
        return new Books(books);
    }

    private static Books createBooks2() {
        var books = new ArrayList<Book>();
        books.add(new Book("title5", "Author5", BigDecimal.valueOf(13.5)));
        books.add(new Book("title6", "Author6", BigDecimal.valueOf(14.5)));
        books.add(new Book("title7", "Author7", BigDecimal.valueOf(15.5)));
        books.add(new Book("title8", "Author8", BigDecimal.valueOf(16.5)));
        return new Books(books);
    }
}
