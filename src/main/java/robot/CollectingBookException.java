package robot;

/**
 * @author Dominik Żebracki
 */
class CollectingBookException extends RuntimeException{

    CollectingBookException(String message, Throwable cause) {
        super(message, cause);
    }
}
