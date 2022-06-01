package robot;

import java.time.LocalDateTime;

/**
 * @author Dominik Żebracki
 */
class TriggerTime {

    private final int hour;
    private final int minute;
    private boolean alreadyActivated;

    TriggerTime(int hour, int minute ) {
        this.hour = hour;
        this.minute = minute;
        this.alreadyActivated = false;
    }

    TriggerTime(int hour, int minute, boolean alreadyActivated) {
        this.hour = hour;
        this.minute = minute;
        this.alreadyActivated = alreadyActivated;
    }

    boolean isTimeToActivate(LocalDateTime time) {
        if(!alreadyActivated && activationTimeMatches(time)) {
            alreadyActivated = true;
            return true;
        } else if (alreadyActivated && activationTimeMatches(time)){
            return false;
        } else if (alreadyActivated && !activationTimeMatches(time)){
            alreadyActivated = false;
            return false;
        } else {
            return false;
        }
    }

    private boolean activationTimeMatches(LocalDateTime time) {
        return hour == time.getHour() && minute == time.getMinute();
    }
}
