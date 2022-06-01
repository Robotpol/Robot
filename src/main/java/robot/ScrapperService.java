package robot;

import java.util.concurrent.ExecutorService;

/**
 * @author Dominik Żebracki
 */
class ScrapperService {

    private final ExecutorService executorService;
    private final ActionTriggerer actionTriggerer;

    ScrapperService(ExecutorService executorService, ActionTriggerer actionTriggerer) {
        this.executorService = executorService;
        this.actionTriggerer = actionTriggerer;
        executeActions();
    }

    private void executeActions() {
        executorService.execute(actionTriggerer);
    }
}
