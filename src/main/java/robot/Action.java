package robot;

import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * @author Dominik Żebracki
 */
record Action(JobDetail job, Trigger trigger) {
}
