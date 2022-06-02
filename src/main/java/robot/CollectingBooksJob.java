package robot;

import org.quartz.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author Dominik Żebracki
 */
public class CollectingBooksJob implements Job {

    private final UserNotificationService userNotificationService;

    static Action toDefaultAction() {
        return toAction(LocalDateTime.now().plusSeconds(5), 1);
    }

    static Action toAction(LocalDateTime actionsStart, int intervalHours) {
        var jobDetail =  JobBuilder.newJob(CollectingBooksJob.class)
                .withIdentity("Collecting books job")
                .build();
        var trigger = createTrigger(actionsStart, intervalHours);
        return new Action(jobDetail, trigger);
    }

    public CollectingBooksJob() {
        this.userNotificationService = new UserNotificationService();
    }

    private static Trigger createTrigger(LocalDateTime start, int intervalHours) {
        return TriggerBuilder.newTrigger()
                .startAt(Date.from(start.atZone(ZoneId.systemDefault()).toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(intervalHours)
                        .repeatForever())
                .build();
    }

    @Override
    public void execute(JobExecutionContext context) {
        userNotificationService.notifyAboutNewUpdate(
                new BookCollector()
                        .collectFrom(Scrappers.createDefaultCollection().getAll()));
    }
}
