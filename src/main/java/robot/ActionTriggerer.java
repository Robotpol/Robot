package robot;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Dominik Żebracki
 */
class ActionTriggerer implements Runnable{

    private static final int DEFAULT_REFRESH_RATE_IN_SECONDS = 1;

    private final UserNotificationService userNotificationService;
    private final TriggerTime triggerTime;
    private final BookCollector bookCollector;
    private final ExecutorService executorService;

    ActionTriggerer(UserNotificationService userNotificationService,
                    TriggerTime triggerTime,
                    BookCollector bookCollector,
                    ExecutorService executor) {
        this.userNotificationService = userNotificationService;
        this.triggerTime = triggerTime;
        this.bookCollector = bookCollector;
        this.executorService = executor;
    }

    @Override
    public void run() {
        listenForTrigger();
    }

    @SuppressWarnings("all")
    private void listenForTrigger() {
        try {
            while (true) {
                if(triggerTime.isTimeToActivate(LocalDateTime.now())) {
                }
                TimeUnit.SECONDS.sleep(DEFAULT_REFRESH_RATE_IN_SECONDS);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAction() {
        try {
            notifyUser(collectBooks());
        } catch (ExecutionException | InterruptedException | CollectingBookException e) {
            System.err.println("Error ocurred during collecting books.%nCaused by ".formatted());
        }
    }
    private void notifyUser(Books books) {
            userNotificationService.notifyAboutNewUpdate(books);
    }

    private Books collectBooks() throws ExecutionException, InterruptedException {
        return executorService.submit(bookCollector).get();
    }
}
