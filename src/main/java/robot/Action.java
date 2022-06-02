package robot;

import org.quartz.JobDetail;
import org.quartz.Trigger;

record Action(JobDetail job, Trigger trigger) {
}
