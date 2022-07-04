package robot;

/**
 * @author Dominik Żebracki
 */
public interface BookProvider {

    Books provideBooks();
    Books updateAndProvideBooks();
}
