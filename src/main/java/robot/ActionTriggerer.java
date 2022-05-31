package robot;

import java.time.LocalDateTime;

/**
 * @author Dominik Żebracki
 */
class ActionTriggerer implements Runnable{

    private final UserNotificationService userNotificationService;
    private final TriggerTime triggerTime;
    private final BookCollector bookCollector;

    ActionTriggerer(UserNotificationService userNotificationService,
                    TriggerTime triggerTime,
                    BookCollector bookCollector) {
        this.userNotificationService = userNotificationService;
        this.triggerTime = triggerTime;
        this.bookCollector = bookCollector;
    }

    @Override
    public void run() {
        while (true) {
            if(triggerTime.isTimeToActivate(LocalDateTime.now())) {
                bookCollector.getBooks();
            }
        }
    }
}
