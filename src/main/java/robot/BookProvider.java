package robot;

/**
 * @author Dominik Żebracki
 */
public interface BookProvider {

    Books provideBooks();
    boolean updateBooks();
    Books updateAndProvideBooks();
}
