package robot;

import java.util.Locale;
import java.util.concurrent.ScheduledFuture;

public record ScrapperJob(Bookstore bookstore, ScheduledFuture<?> scheduledFuture, int hour, int min) {

    ScrapperJobDto toDto() {
        return new ScrapperJobDto(bookstore.name().toLowerCase(Locale.ROOT), hour, min);
    }

}
