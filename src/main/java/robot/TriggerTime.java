package robot;

import java.time.LocalDateTime;

/**
 * @author Dominik Żebracki
 */
record TriggerTime (int hour, int minute){

    boolean isTimeToActivate(LocalDateTime time) {
        return hour == time.getHour() && minute == time.getMinute();
    }
}
