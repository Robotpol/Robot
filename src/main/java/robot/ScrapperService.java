package robot;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Dominik Żebracki
 */
@Service
class ScrapperService {

    private final ScheduledExecutorService executorService;

    private final List<ScrapperJob> jobs;
    private final Bookstores bookstores;

    public ScrapperService(Bookstores bookstores) {
        this.bookstores = bookstores;
        this.executorService = Executors.newScheduledThreadPool(2);
        this.jobs = new LinkedList<>();
    }

    public ScrapperJobDto startExecutionAt(ScrapperPostDto scrapperPostDto, int daysOffset) {

        String bookstoreName = scrapperPostDto.bookstore();
        int targetHour = scrapperPostDto.hour();
        int targetMin = scrapperPostDto.min();

        Bookstore bookstore = Bookstore.valueOf(bookstoreName.toUpperCase(Locale.ROOT));
        Runnable taskWrapper = () -> {
            bookstores.update(bookstoreName.toUpperCase(Locale.ROOT));
            startExecutionAt(scrapperPostDto, 1);
        };
        long delay = computeNextDelay(daysOffset, targetHour, targetMin);
        ScheduledFuture<?> schedule = executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
        ScrapperJob job = new ScrapperJob(bookstore, schedule, targetHour, targetMin);
        jobs.add(job);

        return job.toDto();
    }

    private long computeNextDelay(int daysOffset, int targetHour, int targetMin) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.systemDefault();
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(0);
        zonedNextTarget = zonedNextTarget.plusDays(daysOffset);
        Duration duration = Duration.between(zonedNow, zonedNextTarget);
        return duration.getSeconds();
    }

    List<ScrapperJobDto> getScrapperJobs() {
        return jobs.stream()
                .map(ScrapperJob::toDto)
                .toList();
    }

}
