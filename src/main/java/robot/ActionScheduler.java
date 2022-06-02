package robot;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

class ActionScheduler {

    private final Scheduler scheduler;

    public static ActionScheduler create() {
        Scheduler scheduler;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
           System.err.println("Error occurred while instantiating scheduler.");
           throw new IllegalStateException("Error occurred while instantiating scheduler.", e);
        }
        return new ActionScheduler(scheduler);
    }

    public void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            System.err.println("Error occurred while starting scheduler.");
            throw new IllegalStateException("Error occurred while starting scheduler.", e);
        }
    }

    public ActionScheduler addAction(Action action) {
        try {
            scheduler.scheduleJob(action.job(), action.trigger());
        } catch (SchedulerException e) {
            System.err.println("Error occurred while adding a job. The job was not added");
        }
        return this;
    }

    private ActionScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
